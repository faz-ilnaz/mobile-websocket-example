package se.elabs.websocketexampleclient;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.service.notification.NotificationListenerService;
import android.view.View;

import java.io.Serializable;

public class NotificationService implements Serializable {

    int notificationId = 1001;

    private int notificationCount = 0;

    public int getNotificationCount(){
        return notificationCount;
    }
    public void setNotificationCount(int count){
        this.notificationCount = count;
    }

    public void sendNotification(Activity activity) {
        Intent intent = new Intent(activity, BadgesListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0);
        String contentText = null;
        if(this.getNotificationCount() == 1){
            contentText = "Вы получили бейдж!";
        }else{
            contentText = String.format("У вас %d новых оповещений",this.getNotificationCount());
        }
        Notification notification  = new Notification.Builder(activity)
                .setContentTitle("Оповещение")
                .setContentText(contentText)
                .setSmallIcon(android.R.drawable.star_on)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager =
                (NotificationManager) activity.getSystemService(
                        activity.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, notification);
    }
    public void incrementNotificationCount(){
        this.setNotificationCount(this.getNotificationCount() + 1);
    }
    public void clearNotificationCount(){
        this.setNotificationCount(0);
    }

    public void removeNotification(Activity activity) {
        NotificationManager notificationManager =
                (NotificationManager) activity.getSystemService(
                        activity.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
    }
}
