package ha.thanh.pikerfree.fragments.messages;

import android.content.Context;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.models.Conversation;
import ha.thanh.pikerfree.dataHelper.SQLiteMess;


class MessagePresenter {

    private MessageInterface.RequiredViewOps mView;
    private Handler handler;
    private FirebaseDatabase database;
    private List<Conversation> conversationList;
    private List<String> conversationIdList;
    private String userId;

    List<Conversation> getConversationList() {
        if (conversationList != null)
            return conversationList;
        else return conversationList = new ArrayList<>();
    }

    MessagePresenter(Context context, MessageInterface.RequiredViewOps mView) {
        this.mView = mView;
        handler = new Handler();
        conversationList = new ArrayList<>();
        conversationIdList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    void loadAllConversation() {
        if (conversationList != null)
            conversationList.clear();
        handler.post(new Runnable() {
            @Override
            public void run() {
                final DatabaseReference userPref;
                userPref = database
                        .getReference("users")
                        .child(userId)
                        .child(Constants.MESS_STRING);
                userPref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {
                        };
                        conversationIdList.clear();
                        if (dataSnapshot.getValue(t) != null)
                            conversationIdList.addAll(dataSnapshot.getValue(t));
                        getConversations();
                        userPref.removeEventListener(this);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void getConversations() {
        conversationList.clear();
        if (conversationIdList != null)
            for (int i = 0; i < conversationIdList.size(); i++) {
                final DatabaseReference conversationPref = database
                        .getReference(Constants.CONVERSATION).child(conversationIdList.get(i));
                conversationPref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Conversation conversation = dataSnapshot.getValue(Conversation.class);
                        conversationList.add(conversation);
                        //Collections.sort(conversationList);
                        mView.onGetConversationDone();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
    }

}
