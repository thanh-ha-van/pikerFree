package ha.thanh.pikerfree.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.main.MainActivity;
import ha.thanh.pikerfree.activities.viewPost.PostActivity;
import ha.thanh.pikerfree.activities.viewProfile.ViewProfileActivity;
import ha.thanh.pikerfree.constants.Constants;
import ha.thanh.pikerfree.dataHelper.NotificationDataHelper;
import ha.thanh.pikerfree.dataHelper.SQLiteNotification;


public class MessagingService extends FirebaseMessagingService {

    private NotificationDataHelper dataHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        dataHelper = new NotificationDataHelper(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String type = "";
        String dataId = "";
        String mess;
        // handle when app is killed or backgrounded
        try {
            Map<String, String> data = remoteMessage.getData();
            type = data.get("type");
            dataId = data.get("dataID");
            mess = data.get("body");
            SQLiteNotification sqLiteNotification = new SQLiteNotification();
            sqLiteNotification.setType(Integer.valueOf(type));
            sqLiteNotification.setDataID(dataId);
            sqLiteNotification.setMess(mess);
            sqLiteNotification.setRead(0);
            dataHelper.addNotification(sqLiteNotification);
            Log.e("Message service", "saved data");
        } catch (Exception e) {
            Log.e("Message service", e.getMessage());
        }

        // notification when app is foreground
        String notificationTitle = null, notificationBody = null;

        if (remoteMessage.getNotification() != null) {
            notificationTitle = remoteMessage.getNotification().getTitle();
            notificationBody = remoteMessage.getNotification().getBody();
        }
        sendNotification(notificationTitle, notificationBody, type, dataId);
    }

    private void sendNotification(String notificationTitle, String notificationBody, String type, String id) {

        Intent intent;
        switch (type) {

            case "2": // got new follower
                intent = new Intent(this, ViewProfileActivity.class);
                intent.putExtra(Constants.USER_ID, id);
                break;
            case "3":
            case "4":
            case "5":// got new post request
                intent = new Intent(this, PostActivity.class);
                intent.putExtra(Constants.POST_VIEW, Integer.valueOf(id));
                break;
            default:
                intent = new Intent(this, MainActivity.class);
                break;

        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)   //Automatically delete the notification
                .setSmallIcon(R.mipmap.ic_launcher) //Notification icon
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
