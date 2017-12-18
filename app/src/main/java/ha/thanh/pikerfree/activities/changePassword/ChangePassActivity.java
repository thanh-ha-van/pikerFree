package ha.thanh.pikerfree.activities.changePassword;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.customviews.CustomAlertDialog;
import ha.thanh.pikerfree.customviews.CustomEditText;
import ha.thanh.pikerfree.customviews.WaitingDialog;

public class ChangePassActivity extends AppCompatActivity {

    @BindView(R.id.et_current_pass)
    CustomEditText etCurrentPass;
    @BindView(R.id.et_new_pass)
    CustomEditText etNewPass;
    @BindView(R.id.et_confirm_pass)
    CustomEditText etPassConfirm;

    WaitingDialog waitingDialog;
    CustomAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        ButterKnife.bind(this);
        waitingDialog = new WaitingDialog(this);
        alertDialog = new CustomAlertDialog(this);
    }

    @OnClick(R.id.ic_back)
    public void getBack() {
        onBackPressed();
    }

    @OnClick(R.id.btn_save)

    public void checkInfor() {
        if (etCurrentPass.getText().toString().isEmpty() || etNewPass.getText().toString().isEmpty() || etPassConfirm.getText().toString().isEmpty()) {
            alertDialog.showAlertDialog("Error", "Please fill all the inputs.");
            return;
        }
        if (!etNewPass.getText().toString().equals(etPassConfirm.getText().toString())) {
            alertDialog.showAlertDialog("Error", "New password and it\'s confirm does not match.");
            return;
        } else {
            waitingDialog.showDialog();
            changePass(etCurrentPass.getText().toString(), etNewPass.getText().toString());
        }
    }

    private void changePass(String old, final String newPass) {

        final FirebaseUser user;
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email, old);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                waitingDialog.hideDialog();
                                alertDialog.showAlertDialog("Successful", "Your password had been changed.");
                            } else {
                                waitingDialog.hideDialog();
                                alertDialog.showAlertDialog("Error", "Sorry, we can not complete this action.");
                            }
                        }
                    });
                } else {
                    waitingDialog.hideDialog();
                    alertDialog.showAlertDialog("Error", "Authentication failed, please check again your old password.");
                }
            }
        });
    }
}

