package com.bdlabit.shaqib.jubot.Utility;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import com.bdlabit.shaqib.jubot.ProfileActivity;
import com.bdlabit.shaqib.jubot.R;
import com.bdlabit.shaqib.jubot.ReviewActivity;

public class NotificationHelper {

    //Notification Channel
    //Notification Builder
    //Notification Manager

    private static final String CHANNEL_ID = "FCM_ID";

    public static void displayNotification(Context context, String title, String body, String clickAction){

        Intent intent = new Intent(clickAction);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

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
