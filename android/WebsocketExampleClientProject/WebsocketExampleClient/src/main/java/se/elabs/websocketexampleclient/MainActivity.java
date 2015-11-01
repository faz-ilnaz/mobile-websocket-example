package se.elabs.websocketexampleclient;

import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends Activity {

    int notificationId = 1001;

    private Activity that = this;

    private WebSocketService webSocketService;
    private NotificationService notificationService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webSocketService = ((WSApplication)getApplicationContext()).getWebSocketService();
        notificationService = ((WSApplication)getApplicationContext()).getNotificationService();
        webSocketService.addHandler(new WebSocketService.Handler() {
            @Override
            public void handle(final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(message.startsWith("You received a new badge")) {
                            notificationService.incrementNotificationCount();
                            notificationService.sendNotification(that);
                            ((WSApplication)getApplicationContext()).getMessages().add(message);
                        } else if (message.startsWith("Connecting")) {
                            webSocketService.sendMessage("Username:" + getDeviceId());
                        }else{
                            TextView textView = (TextView) findViewById(R.id.messages);
                            textView.setText(textView.getText() + "\n" + message);
                        }
                    }
                });
            }
        });
        webSocketService.connect();

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

//    public void sendMessage(View view) {
//        EditText editText = (EditText)findViewById(R.id.message);
//        webSocketService.sendMessage("Username:" + editText.getText().toString());
//        editText.setText("");
//    }

    private String getDeviceId() {
        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceID = tMgr.getDeviceId();
        System.out.println(deviceID);
        return deviceID;
    }

}
