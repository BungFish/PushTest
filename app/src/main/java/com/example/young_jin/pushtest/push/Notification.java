package com.example.young_jin.pushtest.push;

import android.content.Context;
import android.content.Intent;

import com.apms.sdk.bean.PushMsg;
import com.example.young_jin.pushtest.WakeLocker;

/**
 * Created by Young-Jin on 2016-02-28.
 */
public abstract class Notification {

    private Context context;
    private Intent intent;
    private PushMsg pushMsg;

    public Notification(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        pushMsg = new PushMsg(intent.getExtras());

        WakeLocker.acquire(context);
        WakeLocker.release();

    }

    public abstract void makeNoti(int notification_id);

    public Context getContext() {
        return context;
    }

    public Intent getIntent() {
        return intent;
    }

    public PushMsg getPushMsg() {
        return pushMsg;
    }
}
