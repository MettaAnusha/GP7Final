package com.example.gp7final;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.gp7final.R;

public class NotificationReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "notification_channel";
    private static final String CHANNEL_NAME = "Medicine Reminder";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Create notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel(notificationManager);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Medicine Reminder")
                .setContentText("Time to take your medicine!")
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build();

        // Show notification
        notificationManager.notify(0, notification);
    }

    private void createNotificationChannel(NotificationManager notificationManager) {
        // Check SDK version for Notification Channel creation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
