package ha.thanh.pikerfree.activities.conversation;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.customviews.CustomEditText;
import ha.thanh.pikerfree.customviews.CustomTextView;
import ha.thanh.pikerfree.models.Messages.Message;
import ha.thanh.pikerfree.models.User;

public class ConActivity extends AppCompatActivity implements ConInterface.RequiredViewOps {


    public ConPresenter presenter;
    @BindView(R.id.img_op)
    public CircleImageView OPImage;
    @BindView(R.id.tv_op_name)
    public CustomTextView OPName;
    @BindView(R.id.btn_send)
    public ImageView sendButton;
    @BindView(R.id.et_mess_to_send)
    public CustomEditText tvMessToSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        String id1 = intent.getStringExtra(Constants.U_ID_1);
        String id2 = intent.getStringExtra(Constants.U_ID_2);
        presenter = new ConPresenter(this, this, id1, id2);
    }

    private void initView() {

        ButterKnife.bind(this);
    }

    @Override
    public void OnGoToProfile(String id) {

    }

    @Override
    public void OnNewMess(Message message) {

    }

    @Override
    public void getOPDone(User user) {

    }

    @Override
    public void getOwnerImageDone(Uri uri) {
        Glide.with(this)
                .load(uri)
                .apply(new RequestOptions()
                        .error(R.drawable.action_button_bg)
                        .centerCrop()
                        .dontAnimate()
                        .override(200, 200)
                        .dontTransform())
                .into(OPImage);
    }
}
