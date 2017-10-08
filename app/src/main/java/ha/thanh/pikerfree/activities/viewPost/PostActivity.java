package ha.thanh.pikerfree.activities.viewPost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.adapters.ImageSlideAdapter;
import ha.thanh.pikerfree.customviews.CustomTextView;
import ha.thanh.pikerfree.customviews.WaitingDialog;
import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.utils.Utils;

public class PostActivity extends AppCompatActivity  implements PostInterface.RequiredViewOps, ImageSlideAdapter.OnclickView{

    private ImageSlideAdapter adapter;
    private PostPresenter mPresenter;
    private ImageView[] dots;
    private int currentPosition = 0;
    @BindView(R.id.layout_count_dot)
    public LinearLayout pager_indicator;
    @BindView(R.id.rv_images)
    RecyclerView rvImage;
    @BindView(R.id.tv_title)
    CustomTextView title;
    @BindView(R.id.tv_description)
    CustomTextView description;
    @BindView(R.id.tv_send_request)
    CustomTextView sendRequest;
    @BindView(R.id.tv_day)
    CustomTextView dayTime;
    @BindView(R.id.tv_distance)
    CustomTextView distance;
    @BindView(R.id.tv_owner_name)
    CustomTextView ownwerName;
    @BindView(R.id.tv_post_status)
    CustomTextView postStatus;
    WaitingDialog waitingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initData();
        initView();
    }

    private void initData() {

        mPresenter = new PostPresenter(this, this);
        mPresenter.getPostData("1");
        mPresenter.getImageLinksFromId("1");
    }

    private void initView() {
        ButterKnife.bind(this);
        adapter = new ImageSlideAdapter(this, mPresenter.getImagePostList(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvImage.setLayoutManager(layoutManager);
        rvImage.setAdapter(adapter);
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(rvImage);
        waitingDialog = new WaitingDialog(this);
        waitingDialog.showDialog();
        //setUiPageViewController();
    }

    private void setUiPageViewController() {
        dots = new ImageView[adapter.getItemCount()];
        for (int i = 0; i < adapter.getItemCount(); i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageResource(R.drawable.none_seclected_dot);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(6, 0, 6, 0);
            pager_indicator.addView(dots[i], params);
        }
        dots[0].setImageResource(R.drawable.seclected_dot);
    }


    private void pageSelected(int position) {
        currentPosition = position;
        switchDot();
    }

    private void switchDot() {
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (i == currentPosition)
                dots[i].setImageResource(R.drawable.seclected_dot);
            else
                dots[i].setImageResource(R.drawable.none_seclected_dot);
        }

    }
    @Override
    public void onClick(int position) {

    }

    @Override
    public void getPostDone(Post post) {
        title.setText(post.getTitle());
        description.setText(post.getDescription());
        dayTime.setText(Utils.getTimeString(post.getTimePosted()));
        distance.setText(mPresenter.getDistance());
        waitingDialog.hideDialog();
        postStatus.setText(mPresenter.getStatus());
    }

    @Override
    public void getPostFail() {

    }

    @Override
    public void onInternetFail(String error) {

    }

    @Override
    public void getLinkDone() {
        adapter.notifyDataSetChanged();
    }
}
