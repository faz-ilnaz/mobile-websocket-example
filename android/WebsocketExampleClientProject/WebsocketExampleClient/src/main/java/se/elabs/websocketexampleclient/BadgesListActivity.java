package se.elabs.websocketexampleclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BadgesListActivity extends Activity {

   private NotificationService notificationService;
   private WebSocketService webSocketService;

    private Activity that = this;

    private boolean mIsRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.badges_list_activity);
        mIsRunning = true;

        notificationService = ((WSApplication)getApplicationContext()).getNotificationService();
        webSocketService = ((WSApplication)getApplicationContext()).getWebSocketService();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.text_view, ((WSApplication)getApplicationContext()).getMessages());
        ListView listView = (ListView) findViewById(R.id.badgesView);
        listView.setAdapter(adapter);
        webSocketService.addHandler(new WebSocketService.Handler() {
            @Override
            public void handle(final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (message.startsWith("You received a new badge")) {
                            adapter.notifyDataSetChanged();
                            if(mIsRunning) {
                                resetNotifications();
                            }
                        }
                    }
                });
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mIsRunning = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsRunning = false;
        resetNotifications();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIsRunning = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetNotifications() {
        System.out.println("Clear notification count");
        notificationService.clearNotificationCount();
        notificationService.removeNotification(that);
    }
}
