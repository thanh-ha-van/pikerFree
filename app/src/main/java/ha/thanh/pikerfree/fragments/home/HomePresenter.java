package ha.thanh.pikerfree.fragments.home;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

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

import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.models.User;

/**
 * Created by HaVan on 8/27/2017.
 */

public class HomePresenter implements HomeInterface.RequiredPresenterOps {
    private HomeInterface.RequiredViewOps mView;
    private HomeModel mModel;
    private Handler handler;
    private FirebaseDatabase database;
    private StorageReference mStorageRef;
    private List<Post> postList;


    HomePresenter(Context context, HomeInterface.RequiredViewOps mView) {
        this.mView = mView;
        mModel = new HomeModel(context, this);
        handler = new Handler();
        postList = new ArrayList<>();
        mStorageRef = FirebaseStorage.getInstance().getReference().child("postImages");
        database = FirebaseDatabase.getInstance();
    }

    public List<Post> loadAllMyPost() {
        return mModel.loadAllMyPost();
    }

    public void getLocalData() {
        String userName;
        String userAddress;
        userName = mModel.getUserNameStringFromSharePf();
        userAddress = mModel.getUserAddressStringFromSharePf();
        mView.onLocalDataReady(userName, userAddress, mModel.getLocalImageStringFromSharePf());
    }

    private void getPostData() {

        handler.post(new Runnable() {
            @Override
            public void run() {
                DatabaseReference postPref;
                postPref = database.getReference("posts");
                postPref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<List<Post>> genericTypeIndicator =new GenericTypeIndicator<List<Post>>(){};
                        postList = dataSnapshot.getValue(genericTypeIndicator);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
