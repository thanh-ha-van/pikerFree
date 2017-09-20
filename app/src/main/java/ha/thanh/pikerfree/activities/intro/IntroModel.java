package ha.thanh.pikerfree.activities.intro;

import android.content.Context;

/**
 * Created by HaVan on 5/22/2017.
 */

class IntroModel {

    private IntroInterface.RequiredPresenterOps mPresenter;

    IntroModel(Context context, IntroInterface.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }
}
