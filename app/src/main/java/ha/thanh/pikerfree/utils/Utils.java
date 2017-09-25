package ha.thanh.pikerfree.utils;
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
        return  System.currentTimeMillis();
    }


    public static int startTimeToday(int timestamp) {
        Calendar cal = Calendar.getInstance();
        long timeCurrent = timestamp * 1000L;
        cal.setTimeInMillis(timeCurrent);
        System.out.print(cal.getTime().toString());
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    public static List<String> getLinkImages(Post post){
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 6; i ++) {
            list.add(Constants.BASE_STORAGE_URL + "postImages/" + post.getPostId() + "/" + "image_no_" + i);
        }
        return list;
    }
    public static int getTimeNextDay(int timestamp) {
        return startTimeToday(timestamp) + 86400;
    }


    public static String getTimeString(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = calendar.getTimeZone();
            calendar.setTimeInMillis(timestamp);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date currentTimeZone = calendar.getTime();
            return sdf.format(currentTimeZone);
        }catch (Exception e) {
        }
        return "";
    }

    public static String getFormatTime(int timestamp) {
        int h = (timestamp / 3600);
        int m = (timestamp - h * 3600) / 60;
        int s = timestamp - h * 3600 - m * 60;
        if (h < 0) {
            h = 0;
        }
        if (m < 0) {
            m = 0;
        }
        if (s < 0) {
            s = 0;
        }
        return formatDigits(h) + ":" + formatDigits(m) + ":" + formatDigits(s);
    }

    public static String formatDigits(int num) {
        return (num < 10) ? "0" + num : Integer.toString(num);
    }
}
