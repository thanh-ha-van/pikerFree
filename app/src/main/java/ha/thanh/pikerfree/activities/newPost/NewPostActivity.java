package ha.thanh.pikerfree.activities.newPost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;


import com.vlk.multimager.activities.GalleryActivity;
import com.vlk.multimager.utils.Constants;
import com.vlk.multimager.utils.Image;
import com.vlk.multimager.utils.Params;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.adapters.ImagePickerAdapter;
import ha.thanh.pikerfree.customviews.CustomAlertDialog;
import ha.thanh.pikerfree.customviews.WaitingDialog;
import ha.thanh.pikerfree.models.Post;


public class NewPostActivity extends AppCompatActivity implements NewPostInterface.RequiredViewOps, ImagePickerAdapter.ItemClickListener {


    @BindView(R.id.rv_images)
    public RecyclerView recyclerViewImage;
    @BindView(R.id.et_item_title)
    public TextView tvTitle;
    @BindView(R.id.et_description)
    public TextView tvDescription;

    private NewPostPresenter mPresenter;
    private ImagePickerAdapter adapter;

    private WaitingDialog waitingDialog;
    private CustomAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {

        waitingDialog = new WaitingDialog(this);
        alertDialog = new CustomAlertDialog(this);
        mPresenter = new NewPostPresenter(this, this);
        adapter = new ImagePickerAdapter(this, mPresenter.getItemList(), this);

    }

    private void initView() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewImage.setLayoutManager(gridLayoutManager);
        recyclerViewImage.setAdapter(adapter);
    }

    @OnClick(R.id.bnt_post_this)
    public void đoPostToServer() {

        String tile = tvTitle.getText().toString();
        String description = tvDescription.getText().toString();

        if (TextUtils.isEmpty(tile)) {
            alertDialog.showAlertDialog("Oop!", "Please enter the title for your item");
            return;
        }
        if (TextUtils.isEmpty(description)) {
            alertDialog.showAlertDialog("Oop!", "Please enter some description");
            return;
        }
        waitingDialog.showDialog();
        mPresenter.uploadPostToDatabase(tile, description);
    }

    @Override
    public void onPostDone() {
        waitingDialog.hideDialog();
    }

    @Override
    public void onPostFail(String error) {
        waitingDialog.hideDialog();
        alertDialog.showAlertDialog("Error", error);
    }

    @Override
    public void onAddImagesToAdapter() {
        Intent intent = new Intent(this, GalleryActivity.class);
        Params params = new Params();
        params.setCaptureLimit(4);
        params.setPickerLimit(4);
        params.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        params.setActionButtonColor(getResources().getColor(R.color.colorPrimary));
        params.setButtonTextColor(getResources().getColor(R.color.white));
        intent.putExtra(Constants.KEY_PARAMS, params);
        startActivityForResult(intent, Constants.TYPE_MULTI_PICKER);
    }

    @Override
    public void onItemDeleted(int position) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constants.TYPE_MULTI_PICKER:
                ArrayList<Image> imagesList = data.getParcelableArrayListExtra(Constants.KEY_BUNDLE_LIST);
                mPresenter.addAllImage(imagesList);
                adapter.notifyDataSetChanged();
                mPresenter.startUploadImages();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onUploadSingleImageDone() {
        adapter.notifyDataSetChanged();
    }
}
