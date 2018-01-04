package ha.thanh.pikerfree.activities.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.intro.IntroActivity;
import ha.thanh.pikerfree.activities.login.LoginActivity;
import ha.thanh.pikerfree.activities.main.MainActivity;
import ha.thanh.pikerfree.activities.viewPost.PostActivity;
import ha.thanh.pikerfree.activities.viewProfile.ViewProfileActivity;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.dataHelper.NotificationDataHelper;
import ha.thanh.pikerfree.dataHelper.SQLiteNotification;
import ha.thanh.pikerfree.utils.Utils;

public class SplashActivity extends AppCompatActivity implements SplashInterface.RequiredViewOps {

    private static final int LOAD_SUCCESS = 1;
    private static final int LOAD_ERROR = 2;
    private Handler handler = new Handler();
    private boolean isFirstRun = true;
    private int statusLoadLanguage;
    private SplashPresenter mPresenter;
    private NotificationDataHelper dataHelper;
    @BindView(R.id.tv_network_error)
    TextView tvNetworkError;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        dataHelper = new NotificationDataHelper(this);
        intent = getIntent();
        mPresenter = new SplashPresenter(SplashActivity.this, this);
        isFirstRun = mPresenter.isFirstRun();
    }

    private void getIntentOfNotification(Intent intent) {

        if (intent != null && intent.getExtras() != null) {
            Bundle extras = intent.getExtras();
            String type = extras.getString("type");
            String dataId = extras.getString("dataID");
            String mess = extras.getString("body");

            if (type == null) {
                startMain();
                return;
            }
            if (!type.equalsIgnoreCase("1")) {
                SQLiteNotification sqLiteNotification = new SQLiteNotification();
                sqLiteNotification.setType(Integer.valueOf(type));
                sqLiteNotification.setDataID(dataId);
                sqLiteNotification.setMess(mess);
                sqLiteNotification.setRead(0);
                sqLiteNotification.setTimestamp(Utils.getCurrentTimestamp());
                dataHelper.addNotification(sqLiteNotification);
                processFlow(sqLiteNotification);
            }
        }
    }

    private void processFlow(SQLiteNotification sqLiteNotification) {
        Intent intent;
        switch (sqLiteNotification.getType()) {
            case 2: // got new follower
                intent = new Intent(this, ViewProfileActivity.class);
                intent.putExtra(Constants.USER_ID, sqLiteNotification.getDataID());
                intent.putExtra(Constants.IS_FIRST_RUN, 1);
                startActivity(intent);
                finish();
                break;
            case 3:
            case 4:
            case 5:// got new post request
                intent = new Intent(this, PostActivity.class);
                intent.putExtra(Constants.POST_VIEW, Integer.valueOf(sqLiteNotification.getDataID()));
                intent.putExtra(Constants.IS_FIRST_RUN, 1);
                startActivity(intent);
                finish();
                break;
            default:
                startMain();
                break;
        }
    }

    public void startMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isFirstRun) {
                if (statusLoadLanguage == LOAD_SUCCESS) {
                    getIntentOfNotification(intent);
                } else if (statusLoadLanguage == LOAD_ERROR) {
                    tvNetworkError.setVisibility(View.VISIBLE);
                    tvNetworkError.setText(getResources().getString(R.string.turn_network_on));
                } else {
                    handler.postDelayed(this, 100);
                }
            } else {
                startIntroActivity();
                mPresenter.setIsFirstRun();
            }
        }
    };


    private void startIntroActivity() {
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    @Override
    public void onAutoLoginFail() {
        startLoginActivity();
    }

    @Override
    public void onLoadConfigDone() {
        statusLoadLanguage = LOAD_SUCCESS;
    }

    @Override
    public void onNetworkFail() {
        statusLoadLanguage = LOAD_ERROR;
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 1000);
    }
}
