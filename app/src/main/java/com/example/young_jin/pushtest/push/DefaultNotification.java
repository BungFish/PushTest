package com.example.young_jin.pushtest.push;

import android.content.Context;
import android.content.Intent;

/**
 * Created by slogup on 2016. 2. 29..
 */
public class DefaultNotification extends Notification {
    public DefaultNotification(Context context, Intent intent) {
        super(context, intent);
    }

    @Override
    public void makeNoti(int notification_id, String activityName) {
        prepareNoti(notification_id, activityName);
    }
}
