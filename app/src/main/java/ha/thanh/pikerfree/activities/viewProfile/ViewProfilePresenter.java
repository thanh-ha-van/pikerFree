package ha.thanh.pikerfree.activities.viewProfile;

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
import ha.thanh.pikerfree.services.GPSTracker;

/**
 * Created by HaVan on 11/29/2017.
 */

public class ViewProfilePresenter {
    private ViewProfileInterface.RequiredViewOps mView;
    private Handler handler;
    private FirebaseDatabase database;
    private List<Post> postList;
    private String currentUserId;
    private GPSTracker gpsTracker;

    List<Post> getPostList() {
        return postList;
    }

    private User user;

    ViewProfilePresenter(Context context, ViewProfileInterface.RequiredViewOps mView, String currentUserId) {
        this.mView = mView;
        this.currentUserId = currentUserId;
        handler = new Handler();
        postList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        gpsTracker = new GPSTracker(context);
    }

    public double getUserLat(){
        return gpsTracker.getLatitude();
    }

    public double getUserLng(){
        return gpsTracker.getLongitude();
    }

    void loadAllMyPost() {
        postList.clear();
        handler.post(new Runnable() {
            @Override
            public void run() {
                final DatabaseReference userPref;
                userPref = database
                        .getReference("users")
                        .child(currentUserId);
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
                        postList.add(dataSnapshot.getValue(Post.class));
                        mView.onGetUserPostsDone();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }
}
