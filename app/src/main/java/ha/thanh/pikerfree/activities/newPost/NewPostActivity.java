package ha.thanh.pikerfree.activities.newPost;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.adapters.ImageAdapter;
import ha.thanh.pikerfree.otherHandle.ImageHolder;


public class NewPostActivity extends AppCompatActivity implements NewPostInterface.RequiredViewOps {


    @BindView(R.id.rv_images)
    public RecyclerView recyclerViewImage;
    private RecyclerView.Adapter<ImageHolder> mImageAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Uri> uris = new ArrayList<>();
    private NewPostPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        ButterKnife.bind(this);
        GridLayoutManager layoutManager
                = new GridLayoutManager(getApplicationContext(), 2);
        ArrayList<Integer> post = getImage();
        recyclerViewImage.setLayoutManager(layoutManager);
        this.layoutManager = new LinearLayoutManager(this);
        this.mImageAdapter = new ImageAdapter(post);
        this.recyclerViewImage.setLayoutManager(layoutManager);
        this.recyclerViewImage.setAdapter(mImageAdapter);
        mPresenter = new NewPostPresenter(this, this);
        initData();
        initView();
    }

    private void initData() {

        uris.add(Uri.EMPTY);
    }
    private void initView() {
    }

    @OnClick(R.id.bnt_post_this)
    public void postNew() {
    }

    private ArrayList<Integer> getImage() {
        ArrayList<Integer> postImage = new ArrayList<>();
        postImage.add(R.drawable.demo);
//        postImage.add(R.drawable.demo);
//        postImage.add(R.drawable.demo);
        return postImage;
    }
}
