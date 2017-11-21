package ha.thanh.pikerfree.activities.conversation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.models.Message;

public class ConActivity extends AppCompatActivity implements ConInterface.RequiredViewOps {


    private ConPresenter presenter;

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

    }

    @Override
    public void OnGoToProfile(String id) {

    }

    @Override
    public void OnNewMess(Message message) {

    }
}
