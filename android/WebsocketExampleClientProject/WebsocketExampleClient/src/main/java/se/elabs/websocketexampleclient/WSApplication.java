package se.elabs.websocketexampleclient;

import android.app.Application;

public class WSApplication extends Application {
    private static NotificationService notificationService;

    @Override
    public void onCreate(){
        super.onCreate();
        notificationService = new NotificationService();
    }

    public NotificationService getNotificationService(){
        return notificationService;
    }
}
