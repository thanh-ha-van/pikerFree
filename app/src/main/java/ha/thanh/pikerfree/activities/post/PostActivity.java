package ha.thanh.pikerfree.activities.post;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.adapters.ImageSlideAdapter;

public class PostActivity extends AppCompatActivity  implements ImageSlideAdapter.OnclickView{

    private ImageSlideAdapter adapter;
    @BindView(R.id.rv_images)
    RecyclerView rvImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initView();
    }
    private void initView() {
        ButterKnife.bind(this);
//        List<String> linkImages = mPresenter.getPostImageLinks();
//        adapter = new ImageSlideAdapter(this, linkImages, this);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        rvImage.setLayoutManager(layoutManager);
//        rvImage.setAdapter(adapter);
//        SnapHelper helper = new LinearSnapHelper();
//        helper.attachToRecyclerView(rvImage);
    }

    @Override
    public void onClick(int position) {

    }
}
