package com.example.powerreceiver;

/*https://developer.android.com/training/notify-user/build-notification*/
/*https://developer.android.com/codelabs/android-training-broadcast-receivers?index=..%2F..%2Fandroid-training#3*/

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity {

    private static final int RC_NOTIFY = 100;
    private final CustomBroadcast mReceiver = new CustomBroadcast();
    private static final String EXTRA_NAME = "extra_name";
    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.sendBroadcastButton).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_NAME, "Custom Broadcast");
            intent.setAction(ACTION_CUSTOM_BROADCAST);
            LocalBroadcastManager.getInstance(MainActivity.this)
                    .sendBroadcast(intent);
        });

        createNotificationChannel();
        findViewById(R.id.sendNotificationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "My First Notification";
                sendNotification("First Notification", message);
            }
        });
    }

    private void sendNotification(String textTitle, String textContent) {
        //Create Pending Intent
        // Create an explicit intent for an Activity in your app
        Intent intent=new Intent(this,NotificationDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(NotificationDetailActivity.EXTRA_TEXT,"Hi this is my first notification");
        PendingIntent pendingIntent=PendingIntent.getActivity(this,RC_NOTIFY,intent,PendingIntent.FLAG_UPDATE_CURRENT);

    //Notification Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        //Send Notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());


    }

    private static final String CHANNEL_ID = "Channel ID";
    private static final int NOTIFICATION_ID = 200;

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //register broadcast receiver
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CUSTOM_BROADCAST);
        LocalBroadcastManager.getInstance(this).
                registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregister broadcast receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }
}