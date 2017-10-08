package ha.thanh.pikerfree.activities.viewPost;


import android.content.Context;

import ha.thanh.pikerfree.services.GPSTracker;

public class PostModel {
    private GPSTracker gpsTracker;
    private Context context;

    public PostModel(Context context) {
        this.context = context;
        this.gpsTracker = new GPSTracker(context);
    }

    public double getUserLat() {
        return  gpsTracker.getLatitude();
    }
    public double getUserLng() {
        return  gpsTracker.getLongitude();
    }

}
