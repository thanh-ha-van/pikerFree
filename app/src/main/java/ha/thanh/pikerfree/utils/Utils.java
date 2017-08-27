package ha.thanh.pikerfree.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class Utils {

    public static String md5(String md5) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ignored) {
        }
        return "";
    }

    public static int getCurrentTimestamp() {
        return (int) (System.currentTimeMillis() / 1000);
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

    public static int getTimeNextDay(int timestamp) {
        return startTimeToday(timestamp) + 86400;
    }


    public static int get30DaysBefore() {
        return startTimeToday(getCurrentTimestamp()) - 30 * 86400;
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
