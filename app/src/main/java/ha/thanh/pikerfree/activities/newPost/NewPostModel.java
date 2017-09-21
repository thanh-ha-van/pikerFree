package ha.thanh.pikerfree.activities.newPost;

import android.content.Context;
import android.content.SharedPreferences;

import ha.thanh.pikerfree.activities.editProfile.EditProfileInterface;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.models.User;

/**
 * Created by HaVan on 8/23/2017.
 */

public class NewPostModel {

    private NewPostInterface.RequiredPresenterOps mPresenter;
    private EditProfileInterface.RequiredPresenterOps mPresenterOpt;
    private SharedPreferences sharedPreferences;
    NewPostModel(Context context, NewPostInterface.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
        sharedPreferences = context.getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
    }


    public String getUserNameStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_NAME, "");

    }

    public String getUserAddressStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_ADDRESS, "");

    }

}
