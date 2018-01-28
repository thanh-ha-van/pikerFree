package ha.thanh.pikerfree.fragments.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.editProfile.EditProfileActivity;
import ha.thanh.pikerfree.activities.newPost.NewPostActivity;
import ha.thanh.pikerfree.activities.viewPost.PostActivity;
import ha.thanh.pikerfree.activities.viewProfile.ViewProfileActivity;
import ha.thanh.pikerfree.adapters.PostAdapter;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.customviews.CustomAlertDialog;
import ha.thanh.pikerfree.customviews.CustomTextView;
import ha.thanh.pikerfree.models.User;

public class HomeFragment extends Fragment
        implements HomeInterface.RequiredViewOps, PostAdapter.ItemClickListener {
    @BindView(R.id.rv_my_post)
    public RecyclerView rvPost;
    @BindView(R.id.profile_image)
    public CircleImageView userImage;
    @BindView(R.id.user_name)
    CustomTextView tvUserName;
    @BindView(R.id.tv_loading_post)
    CustomTextView tvLoadingPost;
    @BindView(R.id.tv_no_data)
    CustomTextView tvNoData;
    PostAdapter adapter;
    private HomePresenter homePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }


    private void initView() {

        adapter = new PostAdapter(this.getContext(),
                homePresenter.getPostList(),
                this, homePresenter.getUserLat(),
                homePresenter.getUserLng());
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvPost.setLayoutManager(layoutManager);
        rvPost.setAdapter(adapter);

    }

    @Override
    public void onItemClick(int position) {
        try {
            Intent in = new Intent(this.getContext(), PostActivity.class);
            in.putExtra(Constants.POST_VIEW, homePresenter.getPostList().get(position).getPostId());
            startActivity(in);
            this.getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        } catch (IndexOutOfBoundsException e) {
            e.getCause();
        }

    }

    private void initData() {
        homePresenter = new HomePresenter(this.getContext(), this);
    }

    @OnClick(R.id.btn_new_post)
    public void doNewPost() {
        startActivity(new Intent(this.getContext(), NewPostActivity.class));
        this.getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @OnClick(R.id.btn_edit_profile)
    public void editProfile() {
        startActivity(new Intent(this.getContext(), EditProfileActivity.class));
        this.getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onResume() {
        super.onResume();
        homePresenter.loadAllMyPost();
    }

    @Override
    public void getOwnerImageDone(Uri link) {
        try {
            Glide.with(this)
                    .load(link)
                    .apply(new RequestOptions()
                            .error(R.drawable.ic_user)
                            .centerCrop()
                            .dontAnimate()
                            .override(150, 150)
                            .dontTransform())
                    .into(userImage);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @OnClick(R.id.view_user)
    public void goGoMyProfile() {
        Intent intent = new Intent(this.getContext(), ViewProfileActivity.class);
        intent.putExtra(Constants.USER_ID, homePresenter.getUserId());
        startActivity(intent);
        this.getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onNoGPS() {

        CustomAlertDialog alertDialog = new CustomAlertDialog(this.getActivity());
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        alertDialog.showAlertDialog("GPS is off", "Please turn on your GPS so we can determine your posts distance");
        alertDialog.setListener(new CustomAlertDialog.AlertListener() {
            @Override
            public void onOkClicked() {
                startActivity(new Intent(action));
            }
        });

    }

    @Override
    public void onGetUserDataDone(User user) {
        tvUserName.setText(user.getName());
    }

    @Override
    public void onGetUserPostsDone() {
        rvPost.setVisibility(View.VISIBLE);
        tvNoData.setVisibility(View.GONE);
        tvLoadingPost.setText("Your posts");
        adapter.notifyDataSetChanged();
    }
}
