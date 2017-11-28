package ha.thanh.pikerfree.activities.conversation;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.models.Conversation;
import ha.thanh.pikerfree.models.Messages.Message;
import ha.thanh.pikerfree.models.User;
import ha.thanh.pikerfree.utils.Utils;


class ConPresenter {

    private ConInterface.RequiredViewOps mView;
    private ConModel mModel;

    private String id1;
    private String id2;
    private String conversationID;

    private String userId;
    private boolean isUploadedMess = false;
    private Conversation conversation;

    private Handler handler;
    private FirebaseDatabase database;
    private User OPUser;

    private int currentPull = 10;
    private int nextPull = 0;
    private int lastMessId;
    private List<Message> messageList = new ArrayList<>();


    private ArrayList<String> conversationList1;
    private ArrayList<String> conversationList2;

    List<Message> getMessageList() {
        return messageList;
    }

    ConPresenter(Context context, ConInterface.RequiredViewOps mView, String id1, String id2) {
        this.mView = mView;
        this.id1 = id1;
        this.id2 = id2;
        mModel = new ConModel(context);
        initData();
    }

    private void initData() {

        conversationList1 = new ArrayList<>();
        conversationList2 = new ArrayList<>();
        handler = new android.os.Handler();
        database = FirebaseDatabase.getInstance();
        OPUser = new User();
        userId = mModel.getUserIdFromSharePref();
        getCurrentConversation();
        if (userId.equals(id1)) {
            getOPData(id2);
        } else {
            getOPData(id1);
        }
        checkIfAlreadyHave();
    }

    private void getCurrentConversation() {

        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference userPref;
                userPref = database.getReference("users").child(id1).child(Constants.MESS_STRING);
                userPref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {
                        };

                        conversationList1 = dataSnapshot.getValue(t);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                DatabaseReference user2Pref;
                user2Pref = database.getReference("users").child(id2).child(Constants.MESS_STRING);
                user2Pref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {
                        };

                        conversationList2 = dataSnapshot.getValue(t);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    private void checkIfAlreadyHave() {

        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference userPref;
                userPref = database
                        .getReference(Constants.CONVERSATION);
                userPref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(id1 + id2)) {
                            createDataForConversation(id1 + id2);
                            getConversationData(id1 + id2);
                        } else if (dataSnapshot.hasChild(id2 + id1)) {
                            createDataForConversation(id2 + id1);
                            getConversationData(id2 + id1);
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

    private void createDataForConversation(final String id) {
        conversationID = id;
        final DatabaseReference userPref;
        userPref = database
                .getReference(Constants.CONVERSATION).child(id).child("lastMessId");
        userPref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lastMessId = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void startNewConversation() {

        conversation = new Conversation(id1, id2, id1 + id2, 0);
        if (conversationList1 == null)
            conversationList1 = new ArrayList<>();
        if (conversationList2 == null)
            conversationList2 = new ArrayList<>();

        conversationList1.add(id1 + id2);
        conversationList2.add(id1 + id2);

        handler.post(new Runnable() {
            @Override
            public void run() {

                DatabaseReference user1PostPref;
                user1PostPref = database.getReference("users").child(id1).child(Constants.MESS_STRING);
                user1PostPref.setValue(conversationList1);

                DatabaseReference user2PostPref;
                user2PostPref = database.getReference("users").child(id2).child(Constants.MESS_STRING);
                user2PostPref.setValue(conversationList2);

                DatabaseReference conPref;
                conPref = database.getReference(Constants.CONVERSATION).child(id1 + id2);
                conPref.setValue(conversation);
                getConversationData(id1 + id2);
            }
        });
    }


    private void getConversationData(final String id) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference conPref;
                conPref = database
                        .getReference(Constants.CONVERSATION)
                        .child(id).child("lastMessId");
                conPref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        showData();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    void onUserAddNewMess(String text) {
        lastMessId++;
        currentPull = lastMessId;
        nextPull = lastMessId - 10;
        Message mess = new Message(lastMessId, mModel.getUserIdFromSharePref(), text, Utils.getCurrentTimestamp());
        uploadMess(mess);
    }

    private void uploadMess(final Message message) {
        isUploadedMess = true;
        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference messPref;
                messPref = database.getReference(Constants.CONVERSATION)
                        .child(conversationID)
                        .child(Constants.MESS_STRING)
                        .child(lastMessId + "");
                messPref.setValue(message);
            }
        });

        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference messPref;
                messPref = database.getReference(Constants.CONVERSATION)
                        .child(conversationID)
                        .child("lastMessId");
                messPref.setValue(lastMessId);
            }
        });
    }

    private void showData() {

        if (isUploadedMess) {
            getMessData(lastMessId, true);
            return;
        }
        currentPull = lastMessId;
        nextPull = currentPull - 10;
        // show the last 10 messages to UI. If user pull show the next 10 mess;
        while (currentPull > 0) {
            getMessData(currentPull, false);
            currentPull--;
            if (currentPull == nextPull) {
                nextPull = currentPull - 10;
                return;
            }
        }

    }

    void onPull() {

    }

    private void getMessData(final int id, final boolean isUpload) {

        final DatabaseReference messPref;
        messPref = database
                .getReference(Constants.CONVERSATION)
                .child(conversationID)
                .child(Constants.MESS_STRING)
                .child(id + "");
        messPref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Message mess = dataSnapshot.getValue(Message.class);
                if (isUpload)
                    messageList.add(messageList.size(), mess);
                else
                    messageList.add(mess);
                mView.onGetMessDone(mess);
                messPref.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

        StorageReference mStorageRef;
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
