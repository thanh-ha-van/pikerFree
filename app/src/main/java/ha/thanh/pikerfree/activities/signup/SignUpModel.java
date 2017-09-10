package ha.thanh.pikerfree.activities.signup;

import android.content.Context;

/**
 * Created by HaVan on 9/10/2017.
 */

public class SignUpModel {
    private SignUpInterface.RequiredPresenterOps mPresenter;
    private Context mCon;


    SignUpModel(Context context, SignUpInterface.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
        this.mCon =  context;
    }
}
