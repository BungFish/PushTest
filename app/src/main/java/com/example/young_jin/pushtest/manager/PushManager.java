package com.example.young_jin.pushtest.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.young_jin.pushtest.WakeLocker;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by Young-Jin on 2016-02-25.
 */
public abstract class PushManager {

    private Intent intent;
    private Context context;
    private android.app.NotificationManager mNotificationManager;
    private String msg;
    private Bundle extras;
    private GoogleCloudMessaging gcm;
    private String messageType;

    public PushManager(){

    }

    public PushManager(Intent intent, Context context){
        this.intent = intent;
        this.context = context;
        msg = intent.getStringExtra("msg");

        extras = intent.getExtras();
        gcm = GoogleCloudMessaging.getInstance(context);
        messageType = gcm.getMessageType(intent);
        if (!extras.isEmpty()) {

            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                msg = "Send error: " + extras.toString();
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                msg = "Deleted messages on server: " + extras.toString();
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                msg = intent.getStringExtra("msg");

                Log.i("GcmIntentService", "Received: " + extras.toString());
            }
        }

        WakeLocker.acquire(context);
        WakeLocker.release();
        mNotificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    }

    public abstract void sendNotification(int resiurceId, String title, Class className);

    public Intent getIntent() {
        return intent;
    }

    public Context getContext() {
        return context;
    }

    public android.app.NotificationManager getmNotificationManager() {
        return mNotificationManager;
    }

    public String getMsg() {
        return msg;
    }
}
