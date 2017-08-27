package ha.thanh.pikerfree.activities.newPostActivity;

import android.content.Context;

/**
 * Created by HaVan on 8/23/2017.
 */

public class NewPostPresenter implements NewPostInterface.RequiredPresenterOps {
    private NewPostInterface.RequiredViewOps mView;
    private NewPostModel mModel;

    NewPostPresenter(Context context, NewPostInterface.RequiredViewOps mView) {
        this.mView = mView;
        mModel = new NewPostModel(context, this);
    }
}
