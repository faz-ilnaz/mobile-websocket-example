package se.elabs.websocketexampleclient;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;

public class NotificationService {

    int notificationId = 1001;


    public void sendNotification(Activity activity) {
        Intent intent = new Intent(activity, BadgesListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0);

        Notification notification  = new Notification.Builder(activity)
                .setContentTitle("Оповещение")
                .setContentText("Вы получили бейдж!")
                .setSmallIcon(android.R.drawable.star_on)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .addAction(android.R.drawable.ic_menu_gallery,
                        "Open", pendingIntent)
                .build();
        NotificationManager notificationManager =
                (NotificationManager) activity.getSystemService(
                        activity.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notification);
    }
}
