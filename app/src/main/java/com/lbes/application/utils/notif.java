package com.lbes.application.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.lbes.application.Activities.MainActivity;
import com.lbes.application.R;

/**
 * Created by next on 26/03/17.
 */
public class notif {

    public static NotificationManager mManager;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("static-access")
    public static void generateNotification(Context context){

        android.support.v7.app.NotificationCompat.Builder nb = new android.support.v7.app.NotificationCompat.Builder(context);
        nb.setSmallIcon(R.drawable.info_icon);
        nb.setContentTitle("Bon voyage !");
        nb.setContentText("Il reste 1 heure pour votre départ.");
        nb.setTicker("Bon voyage");

        nb.setAutoCancel(true);

        nb.setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND);

        //get the bitmap to show in notification bar
        Bitmap bitmap_image = BitmapFactory.decodeResource(context.getResources(), R.drawable.info_icon);
        android.support.v7.app.NotificationCompat.BigPictureStyle s = new android.support.v7.app.NotificationCompat.BigPictureStyle().bigPicture(bitmap_image);
        s.setSummaryText("Hadish can makes our life Enlightened");
        //nb.setStyle(s);



        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder TSB = TaskStackBuilder.create(context);
        TSB.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        TSB.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                TSB.getPendingIntent( 0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        nb.setContentIntent(resultPendingIntent);
        nb.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(11221, nb.build());


    }
}
