package ha.thanh.pikerfree.activities.newPostActivity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.adapters.ImageAdapter;


public class NewPostActivity extends AppCompatActivity implements NewPostInterface.RequiredViewOps {


    @BindView(R.id.rv_images)
    public RecyclerView recyclerViewImage;
    private ArrayList<Uri> uris = new ArrayList<>();
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

        uris.add(Uri.EMPTY);
    }
    private void initView() {

    }

    @OnClick(R.id.bnt_post_this)
    public void postNew() {
    }
}
