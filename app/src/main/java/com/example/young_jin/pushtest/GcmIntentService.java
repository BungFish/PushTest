package com.example.young_jin.pushtest;

import android.app.IntentService;
import android.content.Intent;

import com.example.young_jin.pushtest.manager.PushManagerOne;

public class GcmIntentService extends IntentService {

    private PushManagerOne pushManagerOne;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        pushManagerOne = new PushManagerOne(intent, getApplicationContext());
        pushManagerOne.sendNotification(R.drawable.cast_ic_notification_0, "데이잡", MainActivity.class);
        // Release the wake lock provided by the WakefulBroadcastReceiver.
//        CustomNotiReceiverClass.completeWakefulIntent(intent);
    }
}