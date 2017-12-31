package ha.thanh.pikerfree.activities.viewListPost;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.constants.Globals;
import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.dataHelper.PostDataHelper;


class ViewListPostPresenter {

    private ViewListPostInterface.RequiredViewOps mView;
    private ViewListPostModel mModel;
    private FirebaseDatabase database;
    private List<Post> postList;
    private Handler handler;
    private int currentCategory = 8;
    private boolean hasCategory = false;
    private boolean hasCount = false;
    private int postCount = -1;
    private Context context;

    void setCurrentCategory(int currentCategory) {
        this.currentCategory = currentCategory;
        hasCategory = true;
        checkIfCanGetData();
    }

    int getCurrentCategory() {
        return currentCategory;
    }

    private void checkIfCanGetData() {
        if (hasCategory && hasCount)
            getPostData();
    }

    List<Post> getPostList() {
        return postList;
    }

    ViewListPostPresenter(Context context, ViewListPostInterface.RequiredViewOps mView) {
        this.mView = mView;
        this.context = context;
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
                hasCount = true;
                checkIfCanGetData();
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

    private void getPostData() {
        if (currentCategory < 9) {
            searchCategory();
        } else if (currentCategory == Constants.CATE_RECENT) {
            // search recent posts
            searchRecent();
        } else if (currentCategory == Constants.CATE_LOCAL) {
            getLocal();
        }
    }

    private void getLocal() {

        PostDataHelper db = new PostDataHelper(context);
        List<Integer> list = db.getAllPosts();
        for (Integer id : list) {
            getPostByID(id);
        }
    }

    private void searchCategory() {
        setTimeOut();
        handler.post(new Runnable() {
            @Override
            public void run() {

                Query query = database.getReference()
                        .child("posts")
                        .orderByChild("category")
                        .equalTo(currentCategory);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                postList.add(issue.getValue(Post.class));
                                mView.onGetPostDone();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void searchRecent() {

        for (int i = postCount; i > 0 && i > postCount - 10; i--) {
            getPostByID(i);
        }
    }

    private void getPostByID(int i) {
        DatabaseReference postRef;
        postRef = database
                .getReference("posts")
                .child(i + "");
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    postList.add(dataSnapshot.getValue(Post.class));
                    mView.onGetPostDone();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setTimeOut() {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (postList.size() == 0)
                    mView.onNoResult();
            }
        }, 6000);

    }
}
