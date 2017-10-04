package ha.thanh.pikerfree.activities.viewPost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

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
    }

    @Override
    public void onClick(int position) {

    }

    @Override
    public void getPostDone(Post post) {
        title.setText(post.getTitle());
        description.setText(post.getDescription());
        dayTime.setText(Utils.getTimeString(post.getTimePosted()));
        //distance.setText(Utils.getDistance(post.getLat(), post.getLng()));
        waitingDialog.hideDialog();
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
