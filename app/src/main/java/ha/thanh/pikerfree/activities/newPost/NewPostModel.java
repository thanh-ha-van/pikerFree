package ha.thanh.pikerfree.activities.newPost;

import android.content.Context;
import android.content.SharedPreferences;

import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.services.GPSTracker;

/**
 * Created by HaVan on 8/23/2017.
 */

public class NewPostModel {

    private NewPostInterface.RequiredPresenterOps mPresenter;
    private GPSTracker gpsTracker;
    private SharedPreferences sharedPreferences;
    NewPostModel(Context context, NewPostInterface.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
        sharedPreferences = context.getSharedPreferences(Constants.SETTING_CONFIG, Context.MODE_PRIVATE);
        this.gpsTracker = new GPSTracker(context);
    }


    public String getUserNameStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_NAME, "");

    }

    public String getUserAddressStringFromSharePf() {
        return sharedPreferences.getString(Constants.USER_ADDRESS, "");

    }

    public double getUserLat() {
        return  gpsTracker.getLatitude();
    }
    public double getUserLng() {
        return  gpsTracker.getLongitude();
    }

}
