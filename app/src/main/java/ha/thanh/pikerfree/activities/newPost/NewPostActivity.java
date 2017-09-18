package ha.thanh.pikerfree.activities.newPost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;


public class NewPostActivity extends AppCompatActivity implements NewPostInterface.RequiredViewOps {


    @BindView(R.id.rv_images)
    public RecyclerView recyclerViewImage;
    private NewPostPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        ButterKnife.bind(this);

        mPresenter = new NewPostPresenter(this, this);
        initData();
        initView();
    }

    private void initData() {
    }
    private void initView() {
    }

    @OnClick(R.id.bnt_post_this)
    public void postNew() {
    }

}
