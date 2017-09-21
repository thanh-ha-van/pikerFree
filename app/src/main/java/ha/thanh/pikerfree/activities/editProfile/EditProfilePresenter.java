package ha.thanh.pikerfree.activities.editProfile;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ha.thanh.pikerfree.models.User;


class EditProfilePresenter implements EditProfileInterface.RequiredPresenterOps {

    private EditProfileInterface.RequiredViewOps mView;
    private EditProfileModel mModel;
    private Context context;

    private StorageReference mStorageRef;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    private User dataUser;

    private String userId;
    private String userName;
    private String userAddress;

    private boolean isTextChanged = false;
    private boolean isImagesChanged = false;
    private boolean isUploadDone = false;
    private boolean isUpdatedDatabase = false;
    private boolean isUpdatedAuth = false;

    private Handler handler;

    EditProfilePresenter(Context context, EditProfileInterface.RequiredViewOps mView) {
        this.mView = mView;
        this.context = context;
        mModel = new EditProfileModel(context, this);
        auth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users").child(firebaseUser.getUid());
        handler = new Handler();
        dataUser = new User();
        updateDataUser();
    }

    void setImagesChanged() {
        this.isImagesChanged = true;
    }


    public void getLocalData() {
        userName = mModel.getUserNameStringFromSharePf();
        userAddress = mModel.getUserAddressStringFromSharePf();
        mView.onLocalDataReady(userName, userAddress, mModel.getLocalImageStringFromSharePf());
        Log.e("editProfile", "got local infor:  Name" + userName  + " Address " + userAddress + " Path " + mModel.getLocalImageStringFromSharePf());
    }

    void addTextChangeListener(final EditText etUserName, final EditText etUserAddress) {
        userAddress = etUserAddress.getText().toString();
        userName = etUserName.getText().toString();
        etUserAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isTextChanged = true;
                userAddress = etUserAddress.getText().toString();
            }
        });
        etUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isTextChanged = true;
                userName = etUserName.getText().toString();
            }
        });
    }

    void saveDatabaseSetting() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateDataUser();
                databaseReference.setValue(dataUser);
                isUpdatedDatabase = true;
                Log.e("editProfile", "done save database to server");
                checkIfCanHideDialog();
            }
        });
    }

    private void updateDataUser() {
        dataUser.setId(firebaseUser.getUid());
        dataUser.setAvatarLink("userImages/" + userId + ".jpg");
        dataUser.setAddress(userAddress);
        dataUser.setName(userName);
    }

    void saveLocal(Bitmap bitmapImage) {

        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());

        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        File mypath = new File(directory, "profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mModel.saveLocal(userName, userAddress, mypath.getAbsolutePath());
        Log.e("editProfile", " Done save local");
    }

    void saveAuthSetting(final String username, final String linkImages) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isTextChanged) {
                    if (auth != null) {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .setPhotoUri(Uri.parse(linkImages))
                                .build();
                        firebaseUser.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            isUpdatedAuth = true;
                                            checkIfCanHideDialog();
                                            Log.e("editProfile", "done save auth");
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }

    void uploadFile(final Uri filePath) {

        if (!isImagesChanged) {
            isUploadDone = true;
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (filePath != null) {

                    StorageReference riversRef = mStorageRef.child("userImages/" + userId + ".jpg");
                    riversRef.putFile(filePath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    isUploadDone = true;
                                    checkIfCanHideDialog();
                                    Log.e("editProfile", "done upload file to server");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    isUploadDone = true;
                                    checkIfCanHideDialog();
                                    Log.e("editProfile", " upload file to server get error");
                                }
                            });
                }
            }
        });

    }

    private void checkIfCanHideDialog() {
        if (isUpdatedDatabase && isUpdatedAuth && isUploadDone)
            mView.hideDialog();
    }
}
