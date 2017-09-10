package ha.thanh.pikerfree.activities.signup;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.login.LoginActivity;
import ha.thanh.pikerfree.activities.mainActivity.MainActivity;
import ha.thanh.pikerfree.customviews.CustomAlertDialog;
import ha.thanh.pikerfree.customviews.WaitingDialog;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.et_pass_confirm)
    EditText etPassConfirm;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private WaitingDialog waitingDialog;
    private CustomAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();
        waitingDialog = new WaitingDialog(this);
        alertDialog = new CustomAlertDialog(this);
    }

    @OnClick(R.id.btn_sign_up)
    public void doSignUp() {

        String email = etEmail.getText().toString().trim();
        String username = etUserName.getText().toString().trim();
        String password = etPass.getText().toString().trim();
        String passwordConfirm = etPassConfirm.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            alertDialog.showAlertDialog("Whoop!", "Email must not be empty");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            alertDialog.showAlertDialog("Whoop!", "Password must not be empty");
            return;
        }

        if (password.length() < 6) {
            alertDialog.showAlertDialog("Whoop!", "Password must not be empty... and at least 6 characters");
            return;
        }
        if (!password.equals(passwordConfirm)) {
            alertDialog.showAlertDialog("Whoop!", "Password confirm does not match");
            return;
        }
        if (TextUtils.isEmpty(username)) {
            alertDialog.showAlertDialog("Whoop!", "Username must not be empty");
            return;
        }
        waitingDialog.showDialog();

        //create user
        auth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            updateUserData();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        {
                            waitingDialog.hideDialog();
                            alertDialog.showAlertDialog("Error", e.getMessage());
                        }
                    }
                });

    }

    public void updateUserData() {
        if (auth != null) {
            user = auth.getCurrentUser();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(etUserName.getText().toString())
                    .setPhotoUri(Uri.parse(""))
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                waitingDialog.hideDialog();
                                Log.d("Lololo", "User profile updated.");
                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    });
        }
    }

    @OnClick(R.id.btn_log_in)
    public void goToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
