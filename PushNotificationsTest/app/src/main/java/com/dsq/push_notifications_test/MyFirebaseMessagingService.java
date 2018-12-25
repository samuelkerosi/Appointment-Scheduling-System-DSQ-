package com.dsq.push_notifications_test;

import android.app.NotificationChannel;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.os.Build.VERSION;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public enum NotificationChannelImportance { Low, Medium, High, Urgent }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        sendMyNotification(message.getNotification().getTitle(), message.getNotification().getBody(), "Channel ID");
    }

    public void createNotificationChannel(Context context, String channelID, String channelName, String channelDescription, NotificationChannelImportance channelImportance) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = channelName;
            String description = channelDescription;

            int importance;
            switch (channelImportance)
            {
                case Low :
                    importance = NotificationManager.IMPORTANCE_MIN;
                    break;
                case Medium :
                    importance = NotificationManager.IMPORTANCE_LOW;
                    break;
                case High :
                    importance = NotificationManager.IMPORTANCE_DEFAULT;
                    break;
                case Urgent :
                    importance = NotificationManager.IMPORTANCE_HIGH;
                    break;
                default :
                    importance = NotificationManager.IMPORTANCE_DEFAULT;
                    break;
            }

            NotificationChannel channel = new NotificationChannel(channelID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendMyNotification(String title, String message, String channelID) {

        //On click of notification it redirect to this Activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        //Build the notification
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        //Toast.makeText(getApplicationContext(), "Refreshed token: " + token, Toast.LENGTH_LONG).show();
        Log.d("INSTANCE ID TOKEN","Refreshed token: " + token);
    }
}