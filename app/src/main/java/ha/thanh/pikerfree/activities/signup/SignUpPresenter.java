package ha.thanh.pikerfree.activities.signup;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ha.thanh.pikerfree.models.User;

/**
 * Created by HaVan on 9/10/2017.
 */

public class SignUpPresenter implements SignUpInterface.RequiredPresenterOps {

    private SignUpInterface.RequiredViewOps mView;
    private SignUpModel mModel;
    private Activity activity;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private User dataUser;

    SignUpPresenter(Activity context, SignUpInterface.RequiredViewOps mView) {
        this.mView = mView;
        this.activity = context;
        mModel = new SignUpModel(context, this);
        auth = FirebaseAuth.getInstance();
    }

    void doSignUp(final String email, String password, final String userName) {

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            getCurrentUser(userName, email);
                            updateUserDataToServer(userName);
                            saveDataLocal(userName);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        {
                            mView.onHideWaiting();
                            mView.onShowInforDialog("Error", e.getMessage());
                        }
                    }
                });
    }

    public void saveDataLocal(String userName) {
        mModel.saveLocal(userName);
    }

    public void getCurrentUser(String userName, String email) {
        // get Firebase user and convert to dataUser to push to database
        firebaseUser = auth.getCurrentUser();
        dataUser = new User();
        dataUser.setName(userName);
        dataUser.setEmail(email);
        dataUser.setId(firebaseUser.getUid());
    }

    public void updateDataBase() {
        // update to database;
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        DatabaseReference usersRef = ref.child(firebaseUser.getUid());
        usersRef.setValue(dataUser);
        mView.onDoneProcess();
    }

    public void updateUserDataToServer(String userName) {

        // update Auth data
        if (auth != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(userName)
                    .setPhotoUri(Uri.parse(dataUser.getAvatarLink()))
                    .build();
            firebaseUser.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                updateDataBase();
                            }
                        }
                    });
        }
    }
}
