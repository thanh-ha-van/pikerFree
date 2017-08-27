package ha.thanh.pikerfree.activities.newPostActivity;

import android.content.Context;

/**
 * Created by HaVan on 8/23/2017.
 */

public class NewPostModel {

    private NewPostInterface.RequiredPresenterOps mPresenter;

    NewPostModel(Context context, NewPostInterface.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }
}
