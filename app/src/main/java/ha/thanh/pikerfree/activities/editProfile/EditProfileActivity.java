package ha.thanh.pikerfree.activities.editProfile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.customviews.CustomEditText;
import ha.thanh.pikerfree.customviews.WaitingDialog;

public class EditProfileActivity extends AppCompatActivity implements EditProfileInterface.RequiredViewOps {

    @BindView(R.id.profile_image)
    public CircleImageView imageView;
    @BindView(R.id.et_user_name)
    public CustomEditText etUserName;
    @BindView(R.id.et_user_address)
    public CustomEditText etUserAddress;
    private WaitingDialog waitingDialog;
    private Uri filePath;

    private static final int PICK_IMAGE_REQUEST = 234;
    private EditProfilePresenter profilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        initData();
    }
    private void initData() {
        profilePresenter = new EditProfilePresenter(this, this);
        profilePresenter.setListener(this);
        profilePresenter.addTextChangeListener(etUserName, etUserAddress);
        profilePresenter.getDataFromServer();
        waitingDialog = new WaitingDialog(this);
    }


    @OnClick(R.id.btn_change_image)
    public void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @OnClick(R.id.btn_save)
    public void saveEditing() {

        profilePresenter.uploadFile(filePath);
        profilePresenter.saveAuthSetting(etUserName.getText().toString(), etUserAddress.getText().toString());
        profilePresenter.saveDatabaseSetting();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showDialog() {
        waitingDialog.showDialog();
    }

    @Override
    public void hideDialog() {
        waitingDialog.hideDialog();

    }
}
