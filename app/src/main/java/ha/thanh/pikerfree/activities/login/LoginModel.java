package ha.thanh.pikerfree.activities.login;

import android.content.Context;
import android.content.SharedPreferences;

import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.models.User;

/**
 * Created by HaVan on 9/10/2017.
 */

class LoginModel {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    LoginModel(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
    }

    void saveDataLocal(User user, String path) {
        editor = sharedPreferences.edit();
        editor.putString(Constants.USER_NAME, user.getName());
        editor.putString(Constants.USER_ADDRESS, user.getAddress());
        editor.putString(Constants.USER_ID, user.getId());
        editor.putString(Constants.USER_PROFILE_PIC_PATH, path);
        editor.apply();
    }
}
