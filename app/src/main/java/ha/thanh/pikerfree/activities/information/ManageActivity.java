package ha.thanh.pikerfree.activities.information;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.changePassword.ChangePassActivity;
import ha.thanh.pikerfree.activities.login.LoginActivity;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.customviews.CustomAlertDialog;
import ha.thanh.pikerfree.customviews.CustomYesNoDialog;

public class ManageActivity extends AppCompatActivity {

    CustomAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        ButterKnife.bind(this);
        alertDialog = new CustomAlertDialog(this);

    }


    @OnClick(R.id.view_log_out)
    public void LogOut() {

        CustomYesNoDialog customYesNoDialog = new CustomYesNoDialog(this, new CustomYesNoDialog.YesNoInterFace() {
            @Override
            public void onYesClicked() {
                doLogOut();
            }

            @Override
            public void onNoClicked() {

            }
        });
        customYesNoDialog.showAlertDialog("Confirm", "Are you sure you want to log out?");
    }


    @OnClick(R.id.view_change_pass)
    public void changePass() {
        Intent intent = new Intent(this, ChangePassActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.view_delete_account)
    public void deleteAcc() {
        CustomYesNoDialog customYesNoDialog = new CustomYesNoDialog(this, new CustomYesNoDialog.YesNoInterFace() {
            @Override
            public void onYesClicked() {

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            showSuccess();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showFailed(e.getMessage());
                    }
                });
            }
            @Override
            public void onNoClicked() {

            }
        });
        customYesNoDialog.showAlertDialog("Confirm", "Are you sure you want to delete your account?");

    }

    private void showFailed(String e) {
        alertDialog.showAlertDialog("Error", e);
    }

    private void showSuccess() {
        alertDialog.setListener(new CustomAlertDialog.AlertListener() {
            @Override
            public void onOkClicked() {
                doLogOut();
            }
        });
        alertDialog.showAlertDialog("Success", "Your account had been deleted from Piker.");
    }

    private void doLogOut() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Constants.USER_ID, null);
        edit.putString(Constants.USER_ADDRESS, null);
        edit.putString(Constants.USER_LAT, null);
        edit.putString(Constants.USER_LNG, null);
        edit.putString(Constants.USER_NAME, null);
        edit.putString(Constants.USER_PROFILE_PIC_PATH, null);

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
