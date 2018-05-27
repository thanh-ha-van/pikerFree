package ha.thanh.pikerfree.activities.conversation;

import android.content.Context;
import android.content.SharedPreferences;

import ha.thanh.pikerfree.constants.Constants;

/**
 * Created by HaVan on 10/16/2017.
 */

class ConModel {

    private SharedPreferences sharedPreferences;

    ConModel(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
    }

    String getUserIdFromSharePref() {
        return sharedPreferences.getString(Constants.USER_ID, "");

    }
}
