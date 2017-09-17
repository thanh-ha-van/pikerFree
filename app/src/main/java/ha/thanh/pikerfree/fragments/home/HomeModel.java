package ha.thanh.pikerfree.fragments.home;

import android.content.Context;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.constants.DummyData;
import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.utils.Utils;

/**
 * Created by HaVan on 8/27/2017.
 */

public class HomeModel {
    private HomeInterface.RequiredPresenterOps mPresenter;
    private Context mCon;


    HomeModel(Context context, HomeInterface.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
        this.mCon = context;
    }

    public List<Post> loadAllMyPost() {
        List<Post> dummyPost = new ArrayList<>();
        dummyPost.add(new Post(0, "Old laptop", mCon.getResources().getString(R.string.dummy_string), "235SEGSO"));
        dummyPost.add(new Post(0, "Old laptop", mCon.getResources().getString(R.string.dummy_string), "235SEGSO"));
        dummyPost.add(new Post(0, "Old laptop", mCon.getResources().getString(R.string.dummy_string), "235SEGSO"));
        dummyPost.add(new Post(0, "Old laptop", mCon.getResources().getString(R.string.dummy_string), "235SEGSO"));
        dummyPost.add(new Post(0, "Old laptop", mCon.getResources().getString(R.string.dummy_string), "235SEGSO"));
        dummyPost.add(new Post(0, "Old laptop", mCon.getResources().getString(R.string.dummy_string), "235SEGSO"));
        dummyPost.add(new Post(0, "Old laptop", mCon.getResources().getString(R.string.dummy_string), "235SEGSO"));
        return dummyPost;
    }
}
