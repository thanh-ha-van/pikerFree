package ha.thanh.pikerfree.activities.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.forgetPassword.ForgetPassActivity;
import ha.thanh.pikerfree.activities.mainActivity.MainActivity;
import ha.thanh.pikerfree.activities.signup.SignUpActivity;
import ha.thanh.pikerfree.customviews.CustomAlertDialog;
import ha.thanh.pikerfree.customviews.WaitingDialog;

public class LoginActivity extends AppCompatActivity implements LoginInterface.RequiredViewOps {

    private FirebaseAuth auth;
    @BindView(R.id.et_email_login)
    EditText etUserName;
    @BindView(R.id.et_pass)
    EditText etPass;
    CustomAlertDialog alertDialog;
    WaitingDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();
        alertDialog = new CustomAlertDialog(this);
        waitingDialog = new WaitingDialog(this);
        checkLogIn();
    }

    public void checkLogIn() {
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @OnClick(R.id.btn_log_up)
    public void doSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
    }

    @OnClick(R.id.btn_forget)
    public void doForgetPassword() {
        startActivity(new Intent(LoginActivity.this, ForgetPassActivity.class));
        finish();
    }

    @OnClick(R.id.btn_log_in)
    public void dpLogIn() {

        String email = etUserName.getText().toString();
        final String password = etPass.getText().toString();

        if (TextUtils.isEmpty(email)) {
            alertDialog.showAlertDialog("Oop!", "Please enter your registered email address");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            alertDialog.showAlertDialog("Oop!", "Please enter your password");
            return;
        }
        showWaiting();
        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                etPass.setError(getString(R.string.minimum_password));
                                hideWaiting();
                            }
                        } else {
                            hideWaiting();
                            startMain();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        {
                            hideWaiting();
                            alertDialog.showAlertDialog("Oop!", e.getMessage());
                        }
                    }
                });
    }

    public void showWaiting() {
        waitingDialog.showDialog();
    }

    public void hideWaiting() {
        waitingDialog.hideDialog();
    }
    public void startMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
