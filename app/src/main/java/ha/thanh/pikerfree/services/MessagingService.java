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

import ha.thanh.pikerfree.R;
import ha.thanh.pikerfree.activities.main.MainActivity;
import ha.thanh.pikerfree.dataHelper.NotificationDataHelper;
import ha.thanh.pikerfree.models.Notification.DataPayload;
import ha.thanh.pikerfree.dataHelper.SQLiteNotification;


public class MessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessageService";
    private NotificationDataHelper dataHelper;


    @Override
    public void onCreate() {
        super.onCreate();
        android.os.Debug.waitForDebugger();  //
        dataHelper = new NotificationDataHelper(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        try {
            DataPayload dataPayload = (DataPayload) remoteMessage.getData();
            SQLiteNotification sqLiteNotification = new SQLiteNotification();
            sqLiteNotification.setRead(0);
            sqLiteNotification.setDataID(dataPayload.getDataID());
            sqLiteNotification.setType(Integer.valueOf(dataPayload.getType()));
            sqLiteNotification.setMess(remoteMessage.getNotification().getBody());

        }
        catch (Exception e){
            Log.e("Service", e.getMessage());
        }
        String notificationTitle = null, notificationBody = null;

        if (remoteMessage.getNotification() != null) {
            notificationTitle = remoteMessage.getNotification().getTitle();
            notificationBody = remoteMessage.getNotification().getBody();
        }

        sendNotification(notificationTitle, notificationBody);
    }

    private void sendNotification(String notificationTitle, String notificationBody) {
        Intent intent = new Intent(this, MainActivity.class);
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
