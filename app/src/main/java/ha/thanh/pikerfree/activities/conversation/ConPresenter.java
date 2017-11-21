package ha.thanh.pikerfree.activities.conversation;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.models.Conversation;
import ha.thanh.pikerfree.models.User;


class ConPresenter {

    private ConInterface.RequiredViewOps mView;
    private ConModel mModel;

    private String id1;
    private String id2;
    private StorageReference mStorageRef;

    private String userId;
    private boolean isUser1 = false;
    private Conversation conversation;

    private Handler handler;
    private FirebaseDatabase database;
    private User OPUser;

    ConPresenter(Context context, ConInterface.RequiredViewOps mView, String id1, String id2) {
        this.mView = mView;
        this.id1 = id1;
        this.id2 = id2;
        mModel = new ConModel(context);
        initData();
    }

    private void initData() {
        handler = new android.os.Handler();
        database = FirebaseDatabase.getInstance();
        OPUser = new User();
        userId = mModel.getUserIdFromSharePref();
        if (userId.equals(id1)) {
            isUser1 = true;
            getOPData(id2);
        } else {
            isUser1 = false;
            getOPData(id1);
        }
        checkIfAlreadyHave();
    }

    private void checkIfAlreadyHave() {

        // The conversation id is the id of user1 and user 2 stand together.
        // The conversation id will be stored in users
        // database and conversations database at the same time.
        // If Init a new conversations, the database at user1, user2 and conversations will be change.
        // after that. updating the conversation will be easy, delete a conversation will be logic.
        // The content of conversation is inside the conversations preference.

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

                // at this time the conversation is available so just be an old one.
                loadOldConversation(id1 + id2);
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
                        // the last mess id is 0+
                        showData();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    private  void showData() {
        if (conversation.getLastMessId() == 0){
            // nothing in the content of conversation.
        }
        else {
            //
        }
    }

    private void getOPData(final String UserId) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference userPref;
                userPref = database
                        .getReference("users")
                        .child(UserId);
                userPref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        OPUser = dataSnapshot.getValue(User.class);
                        mView.getOPDone(OPUser);
                        getUserImageLink(OPUser.getAvatarLink());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void getUserImageLink(String link) {

        mStorageRef = FirebaseStorage.getInstance()
                .getReference().child(link);
        mStorageRef.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        mView.getOwnerImageDone(uri);
                    }
                });
    }

}
