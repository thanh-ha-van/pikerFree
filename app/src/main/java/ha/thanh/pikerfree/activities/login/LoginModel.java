package ha.thanh.pikerfree.activities.login;

import android.content.Context;

/**
 * Created by HaVan on 9/10/2017.
 */

public class LoginModel {
    private LoginInterface.RequiredPresenterOps mPresenter;
    private Context mCon;


    LoginModel(Context context, LoginInterface.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
        this.mCon =  context;
    }
}
