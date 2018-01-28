package ha.thanh.pikerfree.activities.information;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

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


    @OnClick(R.id.ic_back)
    public void getBack() {
        onBackPressed();
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
        customYesNoDialog.showAlertDialog(getResources().getString(R.string.confirm), getResources().getString(R.string.confirm_log_out));
    }


    @OnClick(R.id.view_change_pass)
    public void changePass() {
        Intent intent = new Intent(this, ChangePassActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
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
                            FirebaseDatabase.getInstance().getReference()
                                    .child("users")
                                    .child(user.getUid())
                                    .removeValue();
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
        customYesNoDialog.showAlertDialog(getResources().getString(R.string.confirm), getResources().getString(R.string.confirm_delete_acc));

    }

    private void showFailed(String e) {
        alertDialog.showAlertDialog(getResources().getString(R.string.error), e);
    }

    private void showSuccess() {
        alertDialog.setListener(new CustomAlertDialog.AlertListener() {
            @Override
            public void onOkClicked() {
                doLogOut();
            }
        });
        alertDialog.showAlertDialog(getResources().getString(R.string.completed), getResources().getString(R.string.complete_delete_acc));
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
        edit.apply();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
