package ha.thanh.pikerfree.activities.splash;

import android.content.Context;


/**
 * Created by HaVan on 5/24/2017.
 */

class SplashPresenter implements SplashInterface.RequiredPresenterOps {
    private SplashInterface.RequiredViewOps mView;
    private SplashModel mModel;

    SplashPresenter(Context context, SplashInterface.RequiredViewOps mView) {
        this.mView = mView;
        mModel = new SplashModel(context, this);
    }

}
