package ha.thanh.pikerfree.activities.viewPost;


import android.content.Context;

import ha.thanh.pikerfree.services.GPSTracker;

class PostModel {
    private GPSTracker gpsTracker;
    private Context context;

    PostModel(Context context) {
        this.context = context;
        this.gpsTracker = new GPSTracker(context);
    }

    double getUserLat() {
        return  gpsTracker.getLatitude();
    }
    double getUserLng() {
        return  gpsTracker.getLongitude();
    }

}
