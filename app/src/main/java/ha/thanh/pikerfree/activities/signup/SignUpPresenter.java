package ha.thanh.pikerfree.activities.signup;

import android.content.Context;
import android.net.Uri;

/**
 * Created by HaVan on 9/10/2017.
 */

public class SignUpPresenter implements SignUpInterface.RequiredPresenterOps {
    private SignUpInterface.RequiredViewOps mView;
    private SignUpModel mModel;
    private Uri file;


    SignUpPresenter(Context context, SignUpInterface.RequiredViewOps mView) {
        this.mView = mView;
        mModel = new SignUpModel(context, this);
    }

    protected void addUserToNode() {

//        file = Uri.fromFile(new File("path/to/mountains.jpg"));
//
//        metadata = new StorageMetadata.Builder()
//                .setContentType("image/jpeg")
//                .build();
//
//        uploadTask = storageReference.child("images/" + file.getLastPathSegment()).putFile(file, metadata);
//
//        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                System.out.println("Upload is " + progress + "% done");
//            }
//        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
//                System.out.println("Upload is paused");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle unsuccessful uploads
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                // Handle successful uploads on complete
//                Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
//            }
//        });
    }
}
