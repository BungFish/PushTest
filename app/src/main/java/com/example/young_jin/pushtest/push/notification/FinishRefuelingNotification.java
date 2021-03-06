package com.example.young_jin.pushtest.push.notification;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.young_jin.pushtest.R;
import com.example.young_jin.pushtest.activities.ThirdActivity;
import com.example.young_jin.pushtest.push.AlertDialogManager;
import com.example.young_jin.pushtest.push.Notification;

import java.util.List;

/**
 * Created by Young-Jin on 2016-02-28.
 */
public class FinishRefuelingNotification extends Notification {

    private Intent i;
    private TaskStackBuilder stackBuilder;
    private PendingIntent resultPendingIntent;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private Intent pushIntent;

    public FinishRefuelingNotification(Context context, Intent intent) {
        super(context, intent);
    }

    @Override
    public void makeNoti(int notification_id, String activityName) {

        i = new Intent(getContext(), ThirdActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        i.putExtra("msg", getmPushMsg().notiMsg);

        stackBuilder = TaskStackBuilder.create(getContext());
        stackBuilder.addNextIntentWithParentStack(i);
        resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(getContext()).setSmallIcon(R.drawable.jamon3)
                .setContentTitle(getmPushMsg().notiTitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(getmPushMsg().notiMsg))
                .setContentText(getmPushMsg().notiMsg)
                .setAutoCancel(true)
                .setVibrate(new long[]{0, 500});

        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager = (android.app.NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notification_id, mBuilder.build());

        if(getmPushMsg().popupFlag.equals("Y")){

            pushIntent = new Intent(getContext(), AlertDialogManager.class);
            pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            pushIntent.putExtras(getIntent().getExtras());
            pushIntent.putExtra("activity", notification_id);

            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, pushIntent, PendingIntent.FLAG_ONE_SHOT);

            ActivityManager am = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> runList = am.getRunningTasks(10);
            ComponentName name = runList.get(0).topActivity;
            String className = name.getClassName();
            boolean isAppRunning = false;

            if(className.contains(getContext().getPackageName())) {
                isAppRunning = true;
            }

            if(isAppRunning == true) {

                // 앱이 실행중일 경우 로직 구현
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }

            } else {

                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }

            }

        }

    }
}
