package ha.thanh.pikerfree.activities.viewPost;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.adapters.ImageSlideAdapter;
import ha.thanh.pikerfree.adapters.UserAdapter;
import ha.thanh.pikerfree.customviews.CustomAlertDialog;
import ha.thanh.pikerfree.customviews.CustomTextView;
import ha.thanh.pikerfree.customviews.CustomYesNoDialog;
import ha.thanh.pikerfree.customviews.WaitingDialog;
import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.models.User;
import ha.thanh.pikerfree.utils.Utils;

public class PostActivity extends AppCompatActivity implements
        PostInterface.RequiredViewOps,
        CustomYesNoDialog.YesNoInterFace,
        UserAdapter.ItemClickListener,
        OnMapReadyCallback {

    @BindView(R.id.vp_image_slide)
    ViewPager vpImageSlide;

    @BindView(R.id.tv_title)
    CustomTextView title;
    @BindView(R.id.tv_description)
    CustomTextView description;
    @BindView(R.id.tv_day)
    CustomTextView dayTime;
    @BindView(R.id.tv_distance)
    CustomTextView distance;
    @BindView(R.id.tv_owner_name)
    CustomTextView ownerName;
    @BindView(R.id.tv_post_status)
    CustomTextView postStatus;
    @BindView(R.id.owner_pic)
    CircleImageView ownerPic;
    @BindView(R.id.tv_meet_owner)
    CustomTextView meetOwner;
    @BindView(R.id.tv_chat)
    CustomTextView chatToOwner;
    @BindView(R.id.tv_send_request)
    CustomTextView sendRequest;

    @BindView(R.id.view_owner)
    View ownerView;
    @BindView(R.id.scroll_view)
    View scrollView;
    @BindView(R.id.view_bottom_action)
    View bottomView;
    @BindView(R.id.view_requesting_user_list)
    View requestingUserView;
    @BindView(R.id.rv_requesting_user)
    RecyclerView rvRequestingUser;


    @BindView(R.id.mapView)
    MapView mMapView;

    private ImageSlideAdapter imageSlideAdapter;
    private UserAdapter userAdapter;
    private PostPresenter mPresenter;
    private WaitingDialog waitingDialog;
    private CustomYesNoDialog confirmDialog;
    private CustomAlertDialog alertDialog;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initData();
        initView();
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);
    }

    @OnClick(R.id.tv_chat)
    public void startChat() {
        mPresenter.handleChatOrClose();
    }

    @OnClick(R.id.tv_send_request)
    public void setSendRequest() {
        mPresenter.showConfirmDialog();
    }

    @Override
    public void onYesClicked() {
        mPresenter.handleRequestOrDelete();
    }

    @Override
    public void onNoClicked() {

    }

    private void initData() {

        mPresenter = new PostPresenter(this, this);
        mPresenter.getPostData("3");
        mPresenter.getImageLinksFromId("3");
    }

    private void initView() {
        ButterKnife.bind(this);
        confirmDialog = new CustomYesNoDialog(this, this);
        alertDialog = new CustomAlertDialog(this);

        scrollView.setVisibility(View.INVISIBLE);

        imageSlideAdapter = new ImageSlideAdapter(this, mPresenter.getImagePostList());
        vpImageSlide.setAdapter(imageSlideAdapter);

        waitingDialog = new WaitingDialog(this);
        waitingDialog.showDialog();
        userAdapter = new UserAdapter(this, mPresenter.getRequestingUsers(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRequestingUser.setLayoutManager(layoutManager);
        rvRequestingUser.setAdapter(userAdapter);
    }

    @Override
    public void onGetRequestingUserDone() {
        requestingUserView.setVisibility(View.VISIBLE);
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChooseUser(int position) {

    }

    @Override
    public void onUserIsOwner() {
        ownerView.setVisibility(View.GONE);
        meetOwner.setText(getResources().getString(R.string.you_own_this));
        sendRequest.setText(getResources().getString(R.string.delete_this));
        chatToOwner.setText(getResources().getString(R.string.edit_this));
    }

    void updateMap(double lat, double lng) {
        LatLng sydney = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Post's location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
    }

    @Override
    public void getPostDone(Post post) {
        scrollView.setVisibility(View.VISIBLE);
        title.setText(post.getTitle());
        description.setText(post.getDescription());
        dayTime.setText(Utils.getTimeString(post.getTimePosted()));
        distance.setText(mPresenter.getDistance());
        waitingDialog.hideDialog();
        postStatus.setText(mPresenter.getStatus());
        updateMap(post.getLat(), post.getLng());
    }

    @Override
    public void getPostFail() {

    }

    @Override
    public void getOwnerDone(User user) {
        ownerName.setText(user.getName());

    }

    @Override
    public void onDeleteDone() {
        alertDialog.showAlertDialog("Deleted", "Your post has been deleted. Press ok and back to previous screen.");
        alertDialog.setListener(new CustomAlertDialog.AlertListener() {
            @Override
            public void onOkClicked() {
                onBackPressed();
            }
        });
    }

    @Override
    public void getOwnerImageDone(Uri uri) {
        Glide.with(this)
                .load(uri)
                .apply(new RequestOptions()
                        .error(R.drawable.action_button_bg)
                        .centerCrop()
                        .dontAnimate()
                        .override(200, 200)
                        .dontTransform())
                .into(ownerPic);
    }

    @Override
    public void getLinkDone() {
        imageSlideAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onAlreadyRequested() {
        alertDialog.showAlertDialog("Uh oh!", "You already send the request before.");
    }

    @Override
    public void onRequestSent() {

        alertDialog.showAlertDialog("Done", "Your request has been sent to owner. Now just wait for their response.");

    }

    @Override
    public void showConfirmDialog(String mess) {
        confirmDialog.showAlertDialog("Confirm Action", mess);
    }
}
