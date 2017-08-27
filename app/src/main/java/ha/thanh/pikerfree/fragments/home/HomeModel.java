package ha.thanh.pikerfree.fragments.home;

import android.content.Context;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.constants.DummyData;
import ha.thanh.pikerfree.objects.Post;
import ha.thanh.pikerfree.utils.Utils;

/**
 * Created by HaVan on 8/27/2017.
 */

public class HomeModel {
    private HomeInterface.RequiredPresenterOps mPresenter;
    private Context mCon;


    HomeModel(Context context, HomeInterface.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
        this.mCon =  context;
    }

    public List<Post> loadAllMyPost() {
        List<Post> dummyPost = new ArrayList<>();
        Uri uri = DummyData.getIns().getUri();
        long time = Utils.getCurrentTimestamp();
        ArrayList<Uri> list = new ArrayList<>();
        list.add(uri);
        list.add(uri);
        dummyPost.add(new Post(0 ,"Old laptop", mCon.getResources().getString(R.string.dummy_string) , list, 1, 0, null, time, null));
        dummyPost.add(new Post(1 ,"Old laptop", mCon.getResources().getString(R.string.dummy_string) , list, 2, 0, null, time, null));
        dummyPost.add(new Post(2 ,"Old laptop", mCon.getResources().getString(R.string.dummy_string) , list, 1, 0, null, time, null));
        dummyPost.add(new Post(3 ,"Old laptop", mCon.getResources().getString(R.string.dummy_string) , list, 2, 0, null, time, null));
        dummyPost.add(new Post(4 ,"Old laptop", mCon.getResources().getString(R.string.dummy_string) , list, 2, 0, null, time, null));
        dummyPost.add(new Post(5 ,"Old laptop", mCon.getResources().getString(R.string.dummy_string) , list, 2, 0, null, time, null));
        dummyPost.add(new Post(6 ,"Old laptop", mCon.getResources().getString(R.string.dummy_string) , list, 1, 0, null, time, null));
        dummyPost.add(new Post(7 ,"Old laptop", mCon.getResources().getString(R.string.dummy_string) , list, 1, 0, null, time, null));
        dummyPost.add(new Post(8 ,"Old laptop", mCon.getResources().getString(R.string.dummy_string) , list, 1, 0, null, time, null));
        return dummyPost;
    }
}
