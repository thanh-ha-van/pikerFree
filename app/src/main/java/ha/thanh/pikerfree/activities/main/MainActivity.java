package ha.thanh.pikerfree.activities.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.adapters.ViewPagerAdapter;
import ha.thanh.pikerfree.constants.Globals;
import ha.thanh.pikerfree.fragments.home.HomeFragment;
import ha.thanh.pikerfree.fragments.messages.MessagesFragment;
import ha.thanh.pikerfree.fragments.news.NewsFragment;
import ha.thanh.pikerfree.fragments.settings.SettingFragment;
import ha.thanh.pikerfree.otherHandle.HandlePermission;

public class MainActivity extends AppCompatActivity implements HandlePermission.CallbackRequestPermission {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private MenuItem prevMenuItem;
    private MainPresenter mainPresenter;
    private HandlePermission handlePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        handlePermission = new HandlePermission(this, this);
        mainPresenter = new MainPresenter(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        setupViewPager(viewPager);
        viewPager.setCurrentItem(0);
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
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new NewsFragment());
        fragments.add(new MessagesFragment());
        fragments.add(new SettingFragment());
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mainPresenter.saveConfig();
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
    }

    @Override
    public void onRequestPermissionFail() {
        Globals.getIns().getConfig().setPermissionWriteFile(false);
        Globals.getIns().getConfig().setPermissionCamera(false);
    }
}
