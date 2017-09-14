package ha.thanh.pikerfree.activities.splash;

import android.content.Context;
/**
 * Created by HaVan on 5/24/2017.
 */

class SplashModel {

    private Context context;
    private SplashInterface.RequiredPresenterOps mPresenter;

    SplashModel(Context context, SplashInterface.RequiredPresenterOps mPresenter) {
        this.context = context;
        this.mPresenter = mPresenter;
    }
}
