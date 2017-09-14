package ha.thanh.pikerfree.activities.intro;

import android.content.Context;


/**
 * Created by HaVan on 5/22/2017.
 */

class IntroPresenter implements IntroInterface.RequiredPresenterOps {
    private IntroInterface.RequiredViewOps mView;
    private IntroModel mModel;

    IntroPresenter(Context context, IntroInterface.RequiredViewOps mView) {
        this.mView = mView;
        mModel = new IntroModel(context, this);
    }

}
