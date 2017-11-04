package ha.thanh.pikerfree.activities.viewCategory;

import android.content.Context;
import android.content.SharedPreferences;

import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.services.GPSTracker;


class ViewCategoryModel {

    private GPSTracker gpsTracker;
    private SharedPreferences sharedPreferences;
    private ViewCategoryInterface.PresenterOpt presenterOpt;

    ViewCategoryModel(Context context, ViewCategoryInterface.PresenterOpt presenterOpt) {
        this.gpsTracker = new GPSTracker(context);
        this. presenterOpt = presenterOpt;
        sharedPreferences = context.getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
    }

    String getUserIdFromSharePref() {
        return sharedPreferences.getString(Constants.USER_ID, "");

    }

    double getUserLat() {
        return  gpsTracker.getLatitude();
    }
    double getUserLng() {
        return  gpsTracker.getLongitude();
    }
}
