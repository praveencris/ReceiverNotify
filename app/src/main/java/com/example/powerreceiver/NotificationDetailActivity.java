package com.example.powerreceiver;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "extra_text" + NotificationDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail_acticity);
        ((TextView) findViewById(R.id.notifyDetailText))
                .setText(getIntent().getStringExtra(EXTRA_TEXT));
    }
}