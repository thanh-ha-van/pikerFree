package ha.thanh.pikerfree.activities.login;

import android.content.Context;

/**
 * Created by HaVan on 9/10/2017.
 */

public class LoginPresenter implements LoginInterface.RequiredPresenterOps {
    private LoginInterface.RequiredViewOps mView;
    private LoginModel mModel;

    LoginPresenter(Context context, LoginInterface.RequiredViewOps mView) {
        this.mView = mView;
        mModel = new LoginModel(context, this);
    }

}
