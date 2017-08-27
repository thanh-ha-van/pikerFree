package ha.thanh.pikerfree.activities.splashActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.introActivity.IntroActivity;

public class SplashActivity extends AppCompatActivity implements SplashInterface.RequiredViewOps {

    private Handler handler = new Handler();
    private boolean isFirstRun = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        handler.postDelayed(this.runnable, 3000);

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startIntroActivity();
        }
    };

    private void startIntroActivity() {
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

}
