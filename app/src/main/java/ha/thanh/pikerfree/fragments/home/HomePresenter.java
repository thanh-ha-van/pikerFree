package ha.thanh.pikerfree.fragments.home;

import android.content.Context;

import java.util.List;

import ha.thanh.pikerfree.models.Post;

/**
 * Created by HaVan on 8/27/2017.
 */

public class HomePresenter implements HomeInterface.RequiredPresenterOps {
    private HomeInterface.RequiredViewOps mView;
    private HomeModel mModel;

    HomePresenter(Context context, HomeInterface.RequiredViewOps mView) {
        this.mView = mView;
        mModel = new HomeModel(context, this);
    }

    public List<Post> loadAllMyPost() {
        return mModel.loadAllMyPost();
    }
}
