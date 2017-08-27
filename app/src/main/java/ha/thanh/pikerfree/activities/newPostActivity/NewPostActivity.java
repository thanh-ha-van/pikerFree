package ha.thanh.pikerfree.activities.newPostActivity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.adapters.ImageAdapter;


public class NewPostActivity extends AppCompatActivity implements NewPostInterface.RequiredViewOps, ImageAdapter.ItemClickListener {
    @BindView(R.id.rv_images)
    public RecyclerView recyclerViewImage;
    public ImageAdapter imageAdapter;
    private ArrayList<Uri> uris = new ArrayList<>();
    private NewPostPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        ButterKnife.bind(this);
        initData();
        initView();
        mPresenter = new NewPostPresenter(this, this);
    }

    private void initData() {
        uris.add(Uri.EMPTY);
    }
    private void initView() {

        imageAdapter = new ImageAdapter(this, uris);
        imageAdapter.setClickListener(this);
        recyclerViewImage.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewImage.setAdapter(imageAdapter);

    }

    @Override
    public void onItemClick(int position) {
    }
}
