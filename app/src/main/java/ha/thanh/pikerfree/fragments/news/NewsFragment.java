package ha.thanh.pikerfree.fragments.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.viewListPost.ViewListPostActivity;
import ha.thanh.pikerfree.constants.Constants;

public class NewsFragment extends Fragment
        implements NewsInterface.RequiredViewOps {

    NewsPresenter newsPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        newsPresenter = new NewsPresenter(this.getContext(), this);
        initData();
        initView(view);
        return view;
    }

    private void initData() {

    }

    private void initView(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.accessories:
                        goToList(Constants.CATE_ACCESSORY);
                        break;
                    case R.id.near_by:
                        goToList(Constants.CATE_NEAR_BY);
                        break;
                    case R.id.recent:
                        goToList(Constants.CATE_RECENT);
                        break;
                    case R.id.baby_and_toy:
                        goToList(Constants.CATE_BABY);
                        break;
                    case R.id.cloths:
                        goToList(Constants.CATE_FASHION);
                        break;
                    case R.id.groceries:
                        goToList(Constants.CATE_GROCERY);
                        break;
                    case R.id.electronics:
                        goToList(Constants.CATE_ELECTRONIC);
                        break;
                    case R.id.home_living:
                        goToList(Constants.CATE_HOME);
                        break;
                    case R.id.others:
                        goToList(Constants.CATE_OTHER);
                        break;
                    case R.id.pet:
                        goToList(Constants.CATE_PET);
                        break;
                    default:
                        goToList(Constants.CATE_ACCESSORY);
                        break;
                }
            }
        });
    }

    private void goToList(int category) {
        Intent intent = new Intent(this.getContext(), ViewListPostActivity.class);
        intent.putExtra(Constants.CATEGORY, category);
        startActivity(intent);
    }

    @Override
    public void onGetPostsDone() {

    }

    @Override
    public void onGetPostsFailed() {

    }
}