package ha.thanh.pikerfree.activities.viewPost;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.models.User;
import ha.thanh.pikerfree.utils.Utils;


class PostPresenter {

    private PostInterface.RequiredViewOps mView;
    private PostModel mModel;
    private List<String> imagePostList;
    private StorageReference mStorageRef;
    private FirebaseDatabase database;
    private User dataUser;
    private List<String> requestingUserIDs;
    private List<User> requestingUsers;
    private Post post;
    private Handler handler;
    private boolean isUserOwner = false;
    private Context con;
    private int postID;

    PostPresenter(Context context, PostInterface.RequiredViewOps mView) {
        this.mView = mView;
        this.con = context;
        mModel = new PostModel(context);
        initData();
    }

    List<String> getImagePostList() {
        return imagePostList;
    }

    List<User> getRequestingUsers() {
        return requestingUsers;
    }

    private void initData() {
        requestingUsers = new ArrayList<>();
        imagePostList = new ArrayList<>();
        mStorageRef = FirebaseStorage
                .getInstance()
                .getReference()
                .child("postImages");
        database = FirebaseDatabase.getInstance();
        dataUser = new User();
        post = new Post();
        handler = new Handler();
    }
    String getTextFromIntCategory(int intput) {
        switch (intput) {
            case Constants.CATE_ACCESSORY:
                return "ACCESSORIES";
            case Constants.CATE_BABY:
                return "BABY AND TOYS";
            case Constants.CATE_ELECTRONIC:
                return "ELECTRONIC";
            case Constants.CATE_FASHION:
                return "FASHION";
            case Constants.CATE_GROCERY:
                return "GROCERIES";
            case Constants.CATE_HOME:
                return "HOME AND STUFFS";
            case Constants.CATE_OTHER:
                return "OTHERS";
            case Constants.CATE_PET:
                return "PETS";
            default:
                return "OTHERS";
        }
    }

    void getPostData(final String postId) {

        postID = Integer.parseInt(postId);
        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference postRef;
                postRef = database
                        .getReference("posts")
                        .child(postId);
                postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        post = dataSnapshot.getValue(Post.class);
                        mView.getPostDone(post);
                        requestingUserIDs = post.getRequestingUser();
                        if (post.getOwnerId().equals(mModel.getUserIdFromSharePref())) {

                            // if user is owner then show requesting list and notify text to UI
                            mView.onUserIsOwner();
                            isUserOwner = true;
                            getRequestingUserList();
                        }
                        getOwnerData(post.getOwnerId());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        mView.getPostFail();
                    }
                });
            }
        });
    }

    void showConfirmDialog() {

        if (isUserOwner) {
            mView.showConfirmDialog(con.getResources().getString(R.string.confirm_delete));
        } else {
            mView.showConfirmDialog(con.getResources().getString(R.string.confirm_request));
        }
    }

    void handleRequestOrDelete() {
        if (isUserOwner) {
            deletePost();
        } else {
            updateRequestingUserList();
        }
    }

    private void deletePost() {
        // delete data base.

        DatabaseReference postPref;
        postPref = database.getReference("posts").child("" + post.getPostId());
        postPref.setValue(null);

        updateUserData();
    }

    private void updateUserData() {
        List<Integer> posts = dataUser.getPosts();
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i) == postID) {
                posts.remove(i);
            }
        }
        DatabaseReference userPost;
        userPost = database.getReference("users").child("" + dataUser.getId()).child("posts");
        userPost.setValue(posts);

        mView.onDeleteDone();

    }

    private void updateRequestingUserList() {

        boolean canRequest = true;
        if (requestingUserIDs == null) {
            // if it's null then must init then add element into it.
            requestingUserIDs = new ArrayList<>();

        } else {
            // if it's not null then must check if user is already in it then decide.
            for (int i = 0; i < requestingUserIDs.size(); i++) {
                if (requestingUserIDs.get(i).equals(mModel.getUserIdFromSharePref())) {
                    mView.onAlreadyRequested();
                    canRequest = false;
                }
            }
        }
        if (canRequest) {
            requestingUserIDs.add(mModel.getUserIdFromSharePref());
            post.setRequestingUser(requestingUserIDs);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    DatabaseReference postPref;
                    postPref = database.getReference("posts").child("" + post.getPostId()).child("requestingUser");
                    postPref.setValue(post.getRequestingUser());
                    mView.onRequestSent();
                }
            });
        }
    }

    private void editPost() {

    }

    private void initChat() {

    }

    //// TODO: 10/10/2017 must show requesting user for owner of the post.
    void handleChatOrClose() {
        if (isUserOwner) {

            // close post
            editPost();
            //// TODO: 10/10/2017  to close post is to clear the requesting user list
        } else {

            // chat
            initChat();
            //// TODO: 10/10/2017  to chat is to start an activity with 2 user ids
        }
    }

    String getDistance() {
        return Utils.getDistance(mModel.getUserLat(), mModel.getUserLng(), post.getLat(), post.getLng());
    }

    String getStatus() {
        if (post.getStatus() == 1)
            return "Opening";
        return "Closed";
    }

    private void getOwnerData(final String UserId) {
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
                        dataUser = dataSnapshot.getValue(User.class);
                        mView.getOwnerDone(dataUser);
                        getUserImageLink(dataUser.getAvatarLink());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void getRequestingUserList() {

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (requestingUserIDs != null) {
                    for (int i = 0; i < requestingUserIDs.size(); i++) {

                        DatabaseReference userPref;
                        userPref = database
                                .getReference("users")
                                .child(requestingUserIDs.get(i));

                        userPref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);
                                requestingUsers.add(user);
                                mView.onGetRequestingUserDone();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
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

    void getImageLinksFromId(final String postId) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 6; i++) {
                    mStorageRef = FirebaseStorage
                            .getInstance()
                            .getReference()
                            .child("postImages")
                            .child(postId)
                            .child("image_no_" + i + ".jpg");
                    mStorageRef.getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imagePostList.add(uri.toString());
                                    mView.getLinkDone();
                                }
                            });
                }
            }
        });

    }
}
