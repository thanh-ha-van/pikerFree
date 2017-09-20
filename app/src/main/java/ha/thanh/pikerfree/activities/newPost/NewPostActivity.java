package ha.thanh.pikerfree.activities.newPost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.adapters.ImagePickerAdapter;
import ha.thanh.pikerfree.models.ImagePost;


public class NewPostActivity extends AppCompatActivity implements NewPostInterface.RequiredViewOps, ImagePickerAdapter.ItemClickListener {


    @BindView(R.id.rv_images)
    public RecyclerView recyclerViewImage;

    private NewPostPresenter mPresenter;
    private ImagePickerAdapter adapter;
    private List<ImagePost> imagePostList = new ArrayList<>();

    private static final int REQUEST_CODE = 123;
    private ArrayList<String> mResults = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        ButterKnife.bind(this);
        mPresenter = new NewPostPresenter(this, this, imagePostList);
        initData();
        initView();
    }

    private void initData() {
        Fresco.initialize(getApplicationContext());
        imagePostList = new ArrayList<>();
        imagePostList.add(new ImagePost(""));
        adapter = new ImagePickerAdapter(this, imagePostList, this);

    }

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerViewImage.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        recyclerViewImage.setAdapter(adapter);
    }

    @OnClick(R.id.bnt_post_this)
    public void đoPostToServer() {
    }

    @Override
    public void onPostDone() {

    }

    @Override
    public void onPostFail(String error) {

    }

    @Override
    public void onAddImagesToAdapter() {

        Intent intent = new Intent(this, ImagesSelectorActivity.class);
        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 5);
        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
        intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onItemDeleted(int position) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        int count = 1;
        // get selected images from selector
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                assert mResults != null;
                for (String result : mResults) {
                    imagePostList.add(0, new ImagePost(result));
                    adapter.notifyDataSetChanged();
                    mPresenter.upLoadSingleImage(result, "Image_no_" + count );
                    count ++;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onUploadSingleImageDone() {
        adapter.notifyDataSetChanged();
    }
}
