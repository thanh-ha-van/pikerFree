package ha.thanh.pikerfree.utils;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.models.Post;

public class Utils {


    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }


    public static List<String> getLinkImages(Post post) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(Constants.BASE_STORAGE_URL + "postImages/" + post.getPostId() + "/" + "image_no_" + i);
        }
        return list;
    }

    public static String getTimeString(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = calendar.getTimeZone();
            calendar.setTimeInMillis(timestamp);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date currentTimeZone = calendar.getTime();
            return sdf.format(currentTimeZone);
        } catch (Exception e) {
        }
        return "";
    }

    public static String getDistance(final double lat1, final double lon1, final double lat2, final double lon2) {
        Location l1 = new Location("One");
        l1.setLatitude(lat1);
        l1.setLongitude(lon1);

        Location l2 = new Location("Two");
        l2.setLatitude(lat2);
        l2.setLongitude(lon2);

        float distance = l1.distanceTo(l2);
        int discaceint = Math.round(distance);
        String dist = discaceint + " M";

        if (distance > 1000.0f) {
            double roundOff = Math.round(distance * 10.0) / 10000.0;

            dist = roundOffTo2DecPlaces(roundOff) + " KM";
        }
        return dist;
    }

    final static String roundOffTo2DecPlaces(double val) {
        return String.format("%.2f", val);
    }

}
