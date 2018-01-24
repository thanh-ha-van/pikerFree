package ha.thanh.pikerfree.fragments.home;

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


class HomePresenter {

    private HomeInterface.RequiredViewOps mView;
    private HomeModel mModel;
    private Handler handler;
    private FirebaseDatabase database;
    private List<Post> postList;

    List<Post> getPostList() {
        return postList;
    }

    private User user;

    HomePresenter(Context context, HomeInterface.RequiredViewOps mView) {
        this.mView = mView;
        mModel = new HomeModel(context);
        handler = new Handler();
        postList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        if (!mModel.canGetLocation())
            mView.onNoGPS();
    }

    double getUserLat() {
        return mModel.getUserLat();
    }

    double getUserLng() {
        return mModel.getUserLng();
    }


    void loadAllMyPost() {
        postList.clear();
        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference userPref;
                userPref = database
                        .getReference("users")
                        .child(mModel.getUserIdFromSharePf());
                userPref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user = dataSnapshot.getValue(User.class);
                        mView.onGetUserDataDone(user);
                        getUserImageLink(user.getAvatarLink());
                        getPostData(user.getPosts());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    String getUserId() {
        return user.getId();
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

    private void getPostData(final ArrayList<Integer> posts) {
        if (posts != null) {
            for (int i = 0; i < posts.size(); i++) {
                DatabaseReference postRef;
                postRef = database
                        .getReference("posts")
                        .child(posts.get(i).toString());
                postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            postList.add(dataSnapshot.getValue(Post.class));
                            mView.onGetUserPostsDone();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }
}
