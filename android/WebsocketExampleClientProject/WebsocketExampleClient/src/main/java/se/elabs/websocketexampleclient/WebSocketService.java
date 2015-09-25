package se.elabs.websocketexampleclient;

import android.util.Log;
import android.widget.TextView;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketService {

    private WebSocketClient mWebSocketClient;
    private String url;
    private Handler handler;

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
                final String message = s;
                if(handler != null){
                    handler.handle(message);
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
        this.handler = handler;
    }
    public static interface Handler{
        public void handle(String message);
    }
}
