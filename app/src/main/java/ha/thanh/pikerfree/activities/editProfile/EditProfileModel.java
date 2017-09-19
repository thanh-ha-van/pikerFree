package ha.thanh.pikerfree.activities.editProfile;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import ha.thanh.pikerfree.constants.Constants;

/**
 * Created by HaVan on 9/10/2017.
 */

public class EditProfileModel {
    private EditProfileInterface.RequiredPresenterOps mPresenterOpt;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    EditProfileModel(Context context, EditProfileInterface.RequiredPresenterOps presenterOpt) {
        this.mPresenterOpt = presenterOpt;
        sharedPreferences = context.getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getUserNameStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_NAME, "");

    }

    public String getUserAddressStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_ADDRESS, "");

    }

    public String getLocalImageStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_PROFILE_PIC_PATH, "");
    }

    public void saveLocal(String username, String userAddress, String path) {
        editor.putString(Constants.USER_NAME, username);
        editor.putString(Constants.USER_ADDRESS, userAddress);
        editor.putString(Constants.USER_PROFILE_PIC_PATH, path);
        editor.apply();

    }
}
