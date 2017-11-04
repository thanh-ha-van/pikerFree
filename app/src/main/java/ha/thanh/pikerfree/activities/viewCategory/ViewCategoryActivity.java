package ha.thanh.pikerfree.activities.viewCategory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.constants.Constants;

public class ViewCategoryActivity extends AppCompatActivity implements ViewCategoryInterface.ViewOpt {

    private ViewCategoryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);

        initData();
        initView();
    }

    private void initData() {

        Intent intent = getIntent();
        int category = intent.getIntExtra(Constants.CATEGORY, Constants.CATE_OTHER);
        presenter = new ViewCategoryPresenter(this, this);
        //presenter.LoadAllPost(category);
    }

    private void initView() {

    }


    @Override
    public void OnGetListPostDone() {

    }

    @Override
    public void OnGetListPostFaild() {

    }
}
