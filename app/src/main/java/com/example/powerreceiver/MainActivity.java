package com.example.powerreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity {

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