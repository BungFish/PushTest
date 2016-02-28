package com.example.young_jin.pushtest.manager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by Young-Jin on 2016-02-25.
 */
public class PushManagerOne extends PushManager {
    public static final int NOTIFICATION_ID = 19;
    private Intent intent;
    private TaskStackBuilder stackBuilder;
    private PendingIntent resultPendingIntent;
    private NotificationCompat.Builder mBuilder;

    public PushManagerOne(Intent intent, Context context){
        super(intent, context);
    }

    @Override
    public void sendNotification(int resiurceId, String title, Class className) {
        if(getMsg() != null){
            intent = new Intent(getContext(), className);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.putExtra("msg", getMsg());

            stackBuilder = TaskStackBuilder.create(getContext());
            stackBuilder.addNextIntentWithParentStack(intent);
            resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder = new NotificationCompat.Builder(getContext()).setSmallIcon(resiurceId)
                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(getMsg()))
                    .setContentText(getMsg())
                    .setAutoCancel(true)
                    .setVibrate(new long[]{0, 500});

            mBuilder.setContentIntent(resultPendingIntent);
            getmNotificationManager().notify(NOTIFICATION_ID, mBuilder.build());
        }
    }
}
