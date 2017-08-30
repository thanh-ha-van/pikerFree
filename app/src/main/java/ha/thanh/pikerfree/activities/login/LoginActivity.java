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
import com.google.firebase.auth.FirebaseAuthException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.forgetPassword.ForgetPassActivity;
import ha.thanh.pikerfree.activities.mainActivity.MainActivity;
import ha.thanh.pikerfree.activities.signup.SignUpActivity;
import ha.thanh.pikerfree.customviews.CustomAlertDialog;
import ha.thanh.pikerfree.customviews.WaitingDialog;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    @BindView(R.id.et_username)
    EditText etUserName;
    @BindView(R.id.et_pass)
    EditText etPass;
    CustomAlertDialog alertDialog = new CustomAlertDialog();
    WaitingDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();
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
    }

    @OnClick(R.id.btn_forget)
    public void doForgetPassword() {
        startActivity(new Intent(LoginActivity.this, ForgetPassActivity.class));
    }

    @OnClick(R.id.btn_log_in)
    public void dpLogIn() {


        String email = etUserName.getText().toString();
        final String password = etPass.getText().toString();

        if (TextUtils.isEmpty(email)) {
            alertDialog.showAlertDialog(this, "Error", "Enter email address");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            alertDialog.showAlertDialog(this, "Error", "Enter email address");
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
                            } else {
                                alertDialog.showAlertDialog(LoginActivity.this, "Error", "Enter email address");
                                hideWaiting();
                            }
                        } else {
                            hideWaiting();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof FirebaseAuthException) {
                            hideWaiting();
                            alertDialog.showAlertDialog(LoginActivity.this, "Error", e.getMessage());
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
}
