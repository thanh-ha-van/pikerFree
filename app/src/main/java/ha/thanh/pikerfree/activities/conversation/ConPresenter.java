package ha.thanh.pikerfree.activities.conversation;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.models.Conversation;
import ha.thanh.pikerfree.models.User;

/**
 * Created by HaVan on 10/16/2017.
 */

public class ConPresenter {

    ConInterface.RequiredViewOps mView;
    Context con;
    ConModel mModel;

    String id1;
    String id2;

    String userId;
    private boolean isUser1 = false;
    Conversation conversation;

    private Handler handler;
    private FirebaseDatabase database;
    private User OpUser;

    public ConPresenter(Context context, ConInterface.RequiredViewOps mView, String id1, String id2) {
        this.mView = mView;
        this.con = context;
        mModel = new ConModel(context);
        initData();
    }

    private void initData() {
        handler = new android.os.Handler();
        database = FirebaseDatabase.getInstance();
        OpUser = new User();
        userId = mModel.getUserIdFromSharePref();
        if (userId.equals(id1))
            isUser1 = true;
        checkIfAlreadyHave();
    }

    private void checkIfAlreadyHave() {

        // The conversation id is the id of user1 and user 2 stand together.
        // The conversation id will be stored in users
        // database and conversations database at the same time.
        // If Init a new conversations, the database at user1, user2 and conversations will be change.
        // after that. updating the conversation will be easy, delete a conversation will be logic.
        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference userPref;
                userPref = database
                        .getReference("users").child(userId).child("mess");
                userPref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(id1 + id2)) {
                            loadOldConversation(id1 + id2);
                        } else if (dataSnapshot.hasChild(id2 + id1)) {
                            loadOldConversation(id2 + id1);
                        } else {
                            startNewConversation();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void startNewConversation() {
        // To start  a new conversation is to change data at user1, user2 and conversations database itself.
        // For user1 and user2 will add the conversation id into mess preference.
        // For conversations preference is add the new conversation id. easy. logic. fuck it im genius.

        conversation = new Conversation(id1, id2, id1 + id2, 0);
        handler.post(new Runnable() {
            @Override
            public void run() {

                // add to conversations
                DatabaseReference conPref;
                conPref = database.getReference(Constants.CONVERSATION).child(id1 + id2);
                conPref.setValue(conversation);
                // add to user1
                DatabaseReference user1Pref;
                user1Pref = database.getReference(Constants.USERS_STRING)
                        .child(id1)
                        .child(Constants.MESS_STRING)
                        .child(id1 + id2);
                user1Pref.setValue(conversation);
                // add to user2
                DatabaseReference user2Pref;
                user2Pref = database.getReference(Constants.USERS_STRING)
                        .child(id2)
                        .child(Constants.MESS_STRING)
                        .child(id1 + id2);
                user2Pref.setValue(conversation);
            }
        });
    }

    private void loadOldConversation(final String id) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference conPref;
                conPref = database
                        .getReference(Constants.CONVERSATION)
                        .child(id);
                conPref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        conversation = dataSnapshot.getValue(Conversation.class);

                        // todo shit

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}
