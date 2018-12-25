package com.dsq.push_notifications_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dsq.push_notifications_test.MyFirebaseMessagingService.NotificationChannelImportance;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyFirebaseMessagingService myFMS = new MyFirebaseMessagingService();
        myFMS.createNotificationChannel(this, "Channel ID", "Channel Name", "Channel Description", NotificationChannelImportance.Urgent);
    }
}
