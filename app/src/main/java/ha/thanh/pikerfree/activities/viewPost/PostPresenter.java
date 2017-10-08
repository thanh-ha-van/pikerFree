package ha.thanh.pikerfree.activities.viewPost;

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

import java.util.ArrayList;
import java.util.List;

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
    private Post post;
    private Handler handler;
    PostPresenter(Context context, PostInterface.RequiredViewOps mView) {
        this.mView = mView;
        mModel = new PostModel(context);
        initData();
    }

    List<String> getImagePostList() {
        return imagePostList;
    }

    private void initData() {
        imagePostList = new ArrayList<>();
        mStorageRef = FirebaseStorage.getInstance().getReference().child("postImages");
        database = FirebaseDatabase.getInstance();
        dataUser = new User();
        post = new Post();
        handler = new Handler();
    }

    void getPostData(final String postId) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference postRef;
                postRef = database.getReference("posts").child(postId);
                postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        post = dataSnapshot.getValue(Post.class);
                        mView.getPostDone(post);
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

    String getDistance() {
        return Utils.getDistance(mModel.getUserLat(), mModel.getUserLng(), post.getLat(), post.getLng());
    }
    String getStatus() {
        if (post.getStatus() == 1)
            return "Opening";
        return  "Closed";
    }

    private void getOwnerData(final String UserId) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference userPref;
                userPref = database.getReference("users").child(UserId);
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

    private void getUserImageLink(String link) {

        mStorageRef = FirebaseStorage.getInstance().getReference().child(link);
        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                mView.getOwnerImageDone(uri);
            }
        });
    }

    void getImageLinksFromId(String postId) {
        for (int i = 1; i <= 6; i++) {
            mStorageRef = FirebaseStorage.getInstance().getReference().child("postImages").child(postId).child("image_no_" + i + ".jpg");
            mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    imagePostList.add(uri.toString());
                    mView.getLinkDone();
                }
            });
        }
    }
}
