package com.bdlabit.shaqib.jubot.Utility;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


import com.bdlabit.shaqib.jubot.R;
import com.bdlabit.shaqib.jubot.TabActivity;

public class NotificationHelper {

    //Notification Channel
    //Notification Builder
    //Notification Manager

    private static final String CHANNEL_ID = "FCM_ID";

    public static void displayNotification(Context context, String title, String body){

        Intent intent = new Intent(context, TabActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(context);
        mNotificationManager.notify(1, mBuilder.build());

    }
}
