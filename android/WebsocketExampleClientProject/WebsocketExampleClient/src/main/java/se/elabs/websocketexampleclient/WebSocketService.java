package se.elabs.websocketexampleclient;

import android.util.Log;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

public class WebSocketService {

    private WebSocketClient mWebSocketClient;
    private String url;

    private final List<Handler> handlers = new CopyOnWriteArrayList<Handler>();

    public WebSocketService(String url) {
        this.url = url;
    }

    public void connect() {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
            }

            @Override
            public void onMessage(String s) {
                for(Handler handler : handlers) {
                    if(handler != null){
                        handler.handle(s);
                    }
                }
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }
    public void sendMessage(String message){
        mWebSocketClient.send(message);
    }
    public void addHandler(Handler handler){
        if (handler == null) {
            throw new NullPointerException("handler == null");
        }
        this.handlers.add(handler);
    }

    public void removeHandler(Handler handler) {
        if (handler == null) {
            return;
        }
        this.handlers.remove(handler);
    }

    public static interface Handler{
        public void handle(String message);
    }
}
