package ha.thanh.pikerfree.activities.viewListPost;

import android.content.Context;
import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ha.thanh.pikerfree.models.Post;


class ViewListPostPresenter {

    private ViewListPostInterface.RequiredViewOps mView;
    private ViewListPostModel mModel;
    private FirebaseDatabase database;
    private List<Post> postList;
    private Handler handler;
    int currentCategory = 8;
    private int postCount = -1;

    List<Post> getPostList() {
        return postList;
    }

    ViewListPostPresenter(Context context, ViewListPostInterface.RequiredViewOps mView) {
        this.mView = mView;
        mModel = new ViewListPostModel(context);
        postList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        handler = new Handler();
        getCurrentPostCount();
    }

    private void getCurrentPostCount() {
        DatabaseReference postCountRef;
        postCountRef = database.getReference().child("postCount");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postCount = dataSnapshot.getValue(int.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        postCountRef.addListenerForSingleValueEvent(eventListener);
    }

    double getUserLat() {
        return mModel.getUserLat();
    }

    double getUserLng() {
        return mModel.getUserLng();
    }

    void getPostData() {

        handler.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < postCount; i++) {
                    DatabaseReference postRef;
                    postRef = database
                            .getReference("posts")
                            .child(i + "");
                    postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            postList.add(dataSnapshot.getValue(Post.class));
                            mView.onGetPostDone();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
}
