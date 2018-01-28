package ha.thanh.pikerfree.activities.changePassword;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

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
        if (etCurrentPass.getText().toString().isEmpty() ||
                etNewPass.getText().toString().isEmpty() ||
                etPassConfirm.getText().toString().isEmpty()) {
            alertDialog.showAlertDialog(getResources().getString(R.string.error),
                    getResources().getString(R.string.pls_fill_all));
            return;
        }
        if (!etNewPass.getText().toString().equals(etPassConfirm.getText().toString())) {
            alertDialog.showAlertDialog(getResources().getString(R.string.error),
                    getResources().getString(R.string.not_match));
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
                            if (task.isSuccessful()) {
                                waitingDialog.hideDialog();
                                alertDialog.showAlertDialog(getResources().getString(R.string.success), getResources().getString(R.string.change_success));
                            } else {
                                waitingDialog.hideDialog();
                                alertDialog.showAlertDialog(getResources().getString(R.string.error), getResources().getString(R.string.can_not_complete));
                            }
                        }
                    });
                } else {
                    waitingDialog.hideDialog();
                    alertDialog.showAlertDialog(getResources().getString(R.string.error), getResources().getString(R.string.fail_auth));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}

