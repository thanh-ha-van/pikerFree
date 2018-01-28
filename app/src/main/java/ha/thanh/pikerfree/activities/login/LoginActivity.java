package ha.thanh.pikerfree.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.forgetPassword.ForgetPassActivity;
import ha.thanh.pikerfree.activities.main.MainActivity;
import ha.thanh.pikerfree.activities.signup.SignUpActivity;
import ha.thanh.pikerfree.customviews.CustomAlertDialog;
import ha.thanh.pikerfree.customviews.WaitingDialog;

public class LoginActivity extends AppCompatActivity implements LoginInterface.RequiredViewOps {

    @BindView(R.id.et_email_login)
    EditText etUserName;
    @BindView(R.id.et_pass)
    EditText etPass;
    CustomAlertDialog alertDialog;
    WaitingDialog waitingDialog;
    LoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this, this);
        alertDialog = new CustomAlertDialog(this);
        waitingDialog = new WaitingDialog(this);
        presenter.checkLogIn();
    }

    @OnClick(R.id.btn_sign_up)
    public void doSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    @OnClick(R.id.tv_help)
    public void showHelp() {
        alertDialog.showAlertDialog(getResources().getString(R.string.help_log_in), getResources().getString(R.string.help_mess));
    }

    @OnClick(R.id.btn_forget)
    public void doForgetPassword() {
        startActivity(new Intent(LoginActivity.this, ForgetPassActivity.class));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    @OnClick(R.id.btn_log_in)
    public void dpLogIn() {

        String email = etUserName.getText().toString();
        final String password = etPass.getText().toString();

        if (TextUtils.isEmpty(email)) {
            alertDialog.showAlertDialog(getResources().getString(R.string.error), getResources().getString(R.string.empty_email));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            alertDialog.showAlertDialog(getResources().getString(R.string.error), getResources().getString(R.string.empty_pass));
            return;
        }
        waitingDialog.showDialog();
        presenter.doLogin(email, password);
    }

    public void startMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    @Override
    public void onHideWaitingDialog() {
        waitingDialog.hideDialog();
    }

    @Override
    public void onLogInSuccess() {
        startMain();
    }

    @Override
    public void onShowAlert(String title, String message) {
        alertDialog.showAlertDialog(title, message);
    }

    @Override
    public void onPasswordWeek() {
        etPass.setError(getResources().getString(R.string.pass_weak));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.removeListener();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
