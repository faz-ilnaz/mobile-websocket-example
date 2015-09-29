package se.elabs.websocketexampleclient;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class WSApplication extends Application {
    private static NotificationService notificationService;
    private static List<String> messages = new ArrayList<>();
    private static WebSocketService webSocketService;
    @Override
    public void onCreate(){
        super.onCreate();
        notificationService = new NotificationService();
        webSocketService = new WebSocketService("ws://jbosswildfly-gdcgamification.rhcloud.com:8000/wsrest/echo");
    }

    public NotificationService getNotificationService(){
        return notificationService;
    }

    public List<String> getMessages() {
        return messages;
    }
    public WebSocketService getWebSocketService(){
        return webSocketService;
    }
    public static void setMessages(List<String> messages) {
        WSApplication.messages = messages;
    }
}
