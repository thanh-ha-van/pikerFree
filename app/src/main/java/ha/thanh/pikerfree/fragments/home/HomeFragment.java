package ha.thanh.pikerfree.fragments.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.adapters.PostAdapter;
import ha.thanh.pikerfree.models.Post;

public class HomeFragment extends Fragment implements HomeInterface.RequiredViewOps {
    @BindView(R.id.rv_my_post)
    public RecyclerView rvPost;
    private List<Post> posts;
    private HomePresenter homePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        homePresenter = new HomePresenter(this.getContext(), this);
        initList();
        initView();
        return view;
    }

    private void initView() {
        if (posts != null) {
            PostAdapter adapter = new PostAdapter(this.getContext(), posts);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rvPost.setLayoutManager(layoutManager);
            rvPost.setAdapter(adapter);
        }
    }
    private void initList() {
        posts = homePresenter.loadAllMyPost();
    }
}
