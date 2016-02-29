package com.example.young_jin.pushtest.push.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.young_jin.pushtest.push.Notification;

/**
 * Created by Young-Jin on 2016-02-28.
 */
public class CancelCardNotification extends Notification {
    private Intent i;
    private TaskStackBuilder stackBuilder;
    private PendingIntent resultPendingIntent;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private Intent pushIntent;
    private Bitmap iconImg;

    public CancelCardNotification(Context context, Intent intent) {
        super(context, intent);
    }

    @Override
    public void makeNoti(int notification_id, String activityName) {
    }

}
