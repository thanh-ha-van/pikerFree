package ha.thanh.pikerfree.fragments.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.information.AboutActivity;
import ha.thanh.pikerfree.activities.information.HelpActivity;
import ha.thanh.pikerfree.activities.information.ManageActivity;
import ha.thanh.pikerfree.activities.information.NotificationActivity;
import ha.thanh.pikerfree.activities.information.TermActivity;
import ha.thanh.pikerfree.activities.login.LoginActivity;
import ha.thanh.pikerfree.activities.viewListPost.ViewListPostActivity;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.customviews.CustomTextView;
import ha.thanh.pikerfree.customviews.CustomYesNoDialog;
import ha.thanh.pikerfree.services.PostDataHelper;


public class SettingFragment extends Fragment {

    @BindView(R.id.tv_favorite_count)
    CustomTextView tvFavCount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        PostDataHelper db = new PostDataHelper(this.getContext());
        tvFavCount.setText("Favorite posts (" + db.getPostCount() + ")");
    }

    @OnClick(R.id.view_privacy)
    public void goToPrivacy() {
        Intent intent = new Intent(this.getContext(), TermActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.view_about)
    public void goToAbout() {
        Intent intent = new Intent(this.getContext(), AboutActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.view_help)
    public void toToHelp() {
        Intent intent = new Intent(this.getContext(), HelpActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.view_manage_account)
    public void LogOut() {
        Intent intent = new Intent(this.getContext(), ManageActivity.class);
        startActivity(intent);

    }
    @OnClick(R.id.view_favorite)
    public void goToList() {
        Intent intent = new Intent(this.getContext(), ViewListPostActivity.class);
        intent.putExtra(Constants.CATEGORY, Constants.CATE_LOCAL);
        startActivity(intent);
    }

    @OnClick(R.id.view_notification)
    public void goToNotification() {
        Intent intent = new Intent(this.getContext(), NotificationActivity.class);
        startActivity(intent);
    }
}