package ha.thanh.pikerfree.activities.information;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.login.LoginActivity;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.customviews.CustomYesNoDialog;

public class ManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        ButterKnife.bind(this);
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
