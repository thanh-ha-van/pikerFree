package ha.thanh.pikerfree.activities.newPost;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import ha.thanh.pikerfree.models.ImagePost;
import ha.thanh.pikerfree.models.User;

/**
 * Created by HaVan on 8/23/2017.
 */

public class NewPostPresenter implements NewPostInterface.RequiredPresenterOps {

    private NewPostInterface.RequiredViewOps mView;
    private NewPostModel mModel;

    private List<ImagePost> imagePostList;
    private Context context;
    private StorageReference mStorageRef;
    private FirebaseDatabase database;
    private FirebaseUser firebaseUser;
    private DatabaseReference userPref;
    private DatabaseReference postPref;
    private ValueEventListener eventListener;
    private int postCount = 0;
    private User dataUser;
    private Handler handler;

    NewPostPresenter(Context context, NewPostInterface.RequiredViewOps mView) {

        this.mView = mView;
        this.context = context;

        this.imagePostList = new ArrayList<>();
        imagePostList.add(new ImagePost("", true, "image_no_0"));

        mModel = new NewPostModel(context, this);
        handler = new Handler();

        /// for storage
        mStorageRef = FirebaseStorage.getInstance().getReference().child("postImages");
        // for setName;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        // for database
        userPref = database.getReference("users").child(firebaseUser.getUid());
        userPref = database.getReference("posts");
        getCurrentCount();
    }

    public void addItemToList(ImagePost imagePost) {
        imagePostList.add(0, imagePost);
    }
    public List<ImagePost> getItemList() {
        return  imagePostList;
    }

    public ImagePost getFirstItem() {
        return imagePostList.get(0);
    }
    @Override
    public void onSaveLocalDone() {

    }

    @Override
    public void onSaveLocalFail(String error) {

    }


    public void upLoadSingleImage(final ImagePost imagePost) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (imagePost.getPathLocal() != null) {
                    Uri filePath = Uri.parse(imagePost.getPathLocal());
                    StorageReference riversRef = mStorageRef.child(postCount + "/" + imagePost.getName());
                    riversRef.putFile(filePath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Log.d("thanh", "Upload image done");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Log.d("thanh", "Upload image fail");
                                }
                            });
                }
            }
        });
    }

    public void getCurrentCount() {
        database = FirebaseDatabase.getInstance();
        userPref = database.getReference().child("postCount");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postCount = dataSnapshot.getValue(int.class);
                Log.e("thanh", " get count = " + postCount);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }
}
