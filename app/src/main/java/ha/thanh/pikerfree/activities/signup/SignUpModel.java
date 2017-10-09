package ha.thanh.pikerfree.activities.signup;

import android.content.Context;
import android.content.SharedPreferences;

import ha.thanh.pikerfree.constants.Constants;

/**
 * Created by HaVan on 9/10/2017.
 */

public class SignUpModel {
    private SignUpInterface.RequiredPresenterOps mPresenter;
    private Context mCon;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    SignUpModel(Context context, SignUpInterface.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
        this.mCon =  context;
        sharedPreferences = context.getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
    }

    void saveLocal(String username) {
        editor = sharedPreferences.edit();
        editor.putString(Constants.USER_NAME, username);
        editor.apply();
    }
}
