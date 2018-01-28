package ha.thanh.pikerfree.activities.forgetPassword;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.customviews.CustomAlertDialog;
import ha.thanh.pikerfree.customviews.WaitingDialog;

public class ForgetPassActivity extends AppCompatActivity {

    @BindView(R.id.et_email_reset)
    EditText emailReset;
    CustomAlertDialog alertDialog;
    WaitingDialog waitingDialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();
        alertDialog = new CustomAlertDialog(this);
        waitingDialog = new WaitingDialog(this);

    }

    @OnClick(R.id.btn_reset)
    public void resetPass() {
        {
            String email = emailReset.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                alertDialog.showAlertDialog(getResources().getString(R.string.error), getResources().getString(R.string.empty_email));
                return;
            }
            waitingDialog.showDialog();
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                alertDialog.showAlertDialog(getResources().getString(R.string.completed), getResources().getString(R.string.we_sent_email));
                            } else {
                                alertDialog.showAlertDialog(getResources().getString(R.string.error), getResources().getString(R.string.can_not_complete));
                            }

                            waitingDialog.hideDialog();
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
