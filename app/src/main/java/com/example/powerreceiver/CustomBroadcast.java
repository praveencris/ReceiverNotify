package com.example.powerreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomBroadcast extends BroadcastReceiver {
    private static final String EXTRA_NAME = "extra_name";
    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_CUSTOM_BROADCAST))
            Toast.makeText(context,
                    "Received :" + intent.getStringExtra(EXTRA_NAME), Toast.LENGTH_SHORT).show();
    }
}
