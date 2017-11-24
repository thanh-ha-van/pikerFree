package ha.thanh.pikerfree.utils;

import android.location.Location;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.models.Messages.Message;
import ha.thanh.pikerfree.models.Post;
import ha.thanh.pikerfree.models.User;

public class Utils {


    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    public static String getTextFromIntCategory(int intput) {
        switch (intput) {
            case Constants.CATE_ACCESSORY:
                return "ACCESSORIES";
            case Constants.CATE_BABY:
                return "BABY AND TOYS";
            case Constants.CATE_ELECTRONIC:
                return "ELECTRONIC";
            case Constants.CATE_FASHION:
                return "FASHION";
            case Constants.CATE_GROCERY:
                return "GROCERIES";
            case Constants.CATE_HOME:
                return "HOME AND STUFFS";
            case Constants.CATE_OTHER:
                return "OTHERS";
            case Constants.CATE_PET:
                return "PETS";
            default:
                return "OTHERS";
        }
    }

    public static List<String> getLinkImages(Post post) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            list.add(Constants.BASE_STORAGE_URL + "postImages/" + post.getPostId() + "/" + "image_no_" + i);
        }
        return list;
    }

    public Message getMessageFromId(String conversationId, int messId){

        return  null;
    }

    public static String getTimeString(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = calendar.getTimeZone();
            calendar.setTimeInMillis(timestamp);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date currentTimeZone = calendar.getTime();
            return sdf.format(currentTimeZone);
        } catch (Exception e) {
            Log.e("Utils", e.getMessage());
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
        int distanceInt = (Math.round(distance)/100)*100;
        String dist = distanceInt + " m away";

        if (distanceInt > 1000) {
            double roundOff = Math.round(distance ) / 1000;
            dist = roundOffTo2DecPlaces(roundOff) + " km away";
        }
        return dist;
    }

    private static String roundOffTo2DecPlaces(double val) {
        return String.format("%.2f", val);
    }

}
