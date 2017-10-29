package ha.thanh.pikerfree.fragments.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.adapters.TypeAdapter;

public class NewsFragment extends Fragment
        implements NewsInterface.RequiredViewOps, TypeAdapter.ItemClickListener {

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
        initView();

        return view;

    }

    private void initData() {

    }

    private void initView() {

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onGetPostsDone() {

    }

    @Override
    public void onGetPostsFailed() {

    }
}