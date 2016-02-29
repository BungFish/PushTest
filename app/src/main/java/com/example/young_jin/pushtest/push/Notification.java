package com.example.young_jin.pushtest.push;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.apms.sdk.bean.PushMsg;
import com.example.young_jin.pushtest.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Young-Jin on 2016-02-28.
 */
public abstract class Notification {

    private Context mContext;
    private Intent mReceivedIntent;
    private PushMsg mPushMsg;
    private Intent mPushIntent;
    private TaskStackBuilder mStackBuilder;
    private PendingIntent mResultPendingIntent;
    private Bitmap mIconImg;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private Intent mDialogIntent;

    public Notification(Context context, Intent intent) {
        this.mContext = context;
        this.mReceivedIntent = intent;
        mPushMsg = new PushMsg(intent.getExtras());

        WakeLocker.acquire(context);
        WakeLocker.release();

    }

    public abstract void makeNoti(int notification_id, String activityName);

    public void prepareNoti(int notification_id, String activityName){
        mPushIntent = new Intent();
        mPushIntent.setClassName(mContext, mContext.getPackageName() + ".activities." + activityName);
        mPushIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        mPushIntent.putExtra("msg", getmPushMsg().notiMsg);

        mStackBuilder = TaskStackBuilder.create(getContext());
        mStackBuilder.addNextIntentWithParentStack(mPushIntent);
        mResultPendingIntent = mStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        new GetImgaFromUrlTask().execute(notification_id, null, null);

    }

    public Context getContext() {
        return mContext;
    }

    public Intent getIntent() {
        return mReceivedIntent;
    }

    public PushMsg getmPushMsg() {
        return mPushMsg;
    }

    public Bitmap LoadImageFromWebOperations(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();

//            Drawable d = Drawable.createFromStream(is, "src name");
            Bitmap icon = BitmapFactory.decodeStream(input);
            return icon;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private class GetImgaFromUrlTask extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {

            try
            {
                mIconImg = LoadImageFromWebOperations(mPushMsg.notiImg);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(Integer notification_id) {
            super.onPostExecute(notification_id);

            mBuilder = new NotificationCompat.Builder(getContext()).setSmallIcon(R.drawable.jamon3).setLargeIcon(mIconImg)
                    .setContentTitle(getmPushMsg().notiTitle)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(getmPushMsg().notiMsg))
                    .setContentText(getmPushMsg().notiMsg)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{0, 500});

            mBuilder.setContentIntent(mResultPendingIntent);
            mNotificationManager = (android.app.NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(notification_id, mBuilder.build());


            if(getmPushMsg().popupFlag.equals("Y")){

                mDialogIntent = new Intent(getContext(), AlertDialogManager.class);
                mDialogIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mDialogIntent.putExtras(getIntent().getExtras());
                mDialogIntent.putExtra("intent", mPushIntent);
                mDialogIntent.putExtra("iconImg", mIconImg);
                mDialogIntent.putExtra("notiId", notification_id);

                PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, mDialogIntent, PendingIntent.FLAG_ONE_SHOT);

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

}
