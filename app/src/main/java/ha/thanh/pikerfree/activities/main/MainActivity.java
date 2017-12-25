package ha.thanh.pikerfree.activities.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.viewPost.PostActivity;
import ha.thanh.pikerfree.activities.viewProfile.ViewProfileActivity;
import ha.thanh.pikerfree.adapters.ViewPagerAdapter;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.constants.Globals;
import ha.thanh.pikerfree.dataHelper.NotificationDataHelper;
import ha.thanh.pikerfree.dataHelper.SQLiteNotification;
import ha.thanh.pikerfree.fragments.home.HomeFragment;
import ha.thanh.pikerfree.fragments.messages.MessageFragment;
import ha.thanh.pikerfree.fragments.news.NewsFragment;
import ha.thanh.pikerfree.fragments.settings.SettingFragment;
import ha.thanh.pikerfree.otherHandle.HandlePermission;
import ha.thanh.pikerfree.utils.Utils;


public class MainActivity extends AppCompatActivity implements HandlePermission.CallbackRequestPermission {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private MenuItem prevMenuItem;
    private HandlePermission handlePermission;
    private int intentData;
    private List<Fragment> fragments;
    private HomeFragment homeFragment;
    private MessageFragment messageFragment;
    private NewsFragment newsFragment;
    private SettingFragment settingFragment;
    private NotificationDataHelper dataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        handlePermission = new HandlePermission(this, this);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        dataHelper = new NotificationDataHelper(this);
        Intent intent = getIntent();
        getIntentOfNotification(intent);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);
        changeOnlineStatus();

    }

    private void getIntentOfNotification(Intent intent) {

        if (intent != null && intent.getExtras() != null) {
            Bundle extras = intent.getExtras();
            String type = extras.getString("type");
            String dataId = extras.getString("dataID");
            String mess = extras.getString("body");

            if(type == null) return;
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
                startActivity(intent);
                break;
            case 3:
            case 4:
            case 5:// got new post request
                intent = new Intent(this, PostActivity.class);
                intent.putExtra(Constants.POST_VIEW, Integer.valueOf(sqLiteNotification.getDataID()));
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void changeOnlineStatus() {

        FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getUid())
                .child("isOnline").setValue(true);
    }

    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_summary:
                            viewPager.setCurrentItem(0);
                            break;
                        case R.id.action_quest:
                            viewPager.setCurrentItem(1);
                            break;
                        case R.id.action_message:
                            viewPager.setCurrentItem(2);
                            break;
                        case R.id.action_setting:
                            viewPager.setCurrentItem(3);
                            break;
                    }
                    return false;
                }
            };

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (prevMenuItem != null) {
                prevMenuItem.setChecked(false);
            } else {
                bottomNavigationView.getMenu().getItem(0).setChecked(false);
            }
            bottomNavigationView.getMenu().getItem(position).setChecked(true);
            prevMenuItem = bottomNavigationView.getMenu().getItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setupViewPager(ViewPager viewPager) {
        fragments = new ArrayList<>();
        homeFragment = new HomeFragment();
        newsFragment = new NewsFragment();
        messageFragment = new MessageFragment();
        settingFragment = new SettingFragment();
        fragments.add(homeFragment);
        fragments.add(newsFragment);
        fragments.add(messageFragment);
        fragments.add(settingFragment);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getUid())
                .child("isOnline").setValue(false);
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        handlePermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onRequestPermissionSuccess() {
        Globals.getIns().getConfig().setPermissionWriteFile(true);
        Globals.getIns().getConfig().setPermissionCamera(true);
        Globals.getIns().getConfig().setPermissionLocation(true);
    }

    @Override
    public void onRequestPermissionFail() {
        Globals.getIns().getConfig().setPermissionWriteFile(false);
        Globals.getIns().getConfig().setPermissionCamera(false);
        Globals.getIns().getConfig().setPermissionLocation(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        messageFragment = new MessageFragment();
    }
}
