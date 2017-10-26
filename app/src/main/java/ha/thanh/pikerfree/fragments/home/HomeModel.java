package ha.thanh.pikerfree.fragments.home;

import android.content.Context;
import android.content.SharedPreferences;

import ha.thanh.pikerfree.constants.Constants;

class HomeModel {
    private SharedPreferences sharedPreferences;

    HomeModel(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
    }

    String getUserNameStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_NAME, "");
    }

    String getUserAddressStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_ADDRESS, "");

    }

    String getLocalImageStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_PROFILE_PIC_PATH, "");
    }

    String getUserIdFromSharePf() {
        return sharedPreferences.getString(Constants.USER_ID, "");
    }
}
