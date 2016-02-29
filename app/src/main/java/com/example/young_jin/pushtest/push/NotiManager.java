package com.example.young_jin.pushtest.push;

import android.content.Context;
import android.content.Intent;

import com.example.young_jin.pushtest.R;

/**
 * Created by Young-Jin on 2016-02-28.
 */
public class NotiManager {
    private Notification mNotification;
    private Context mContext;
    private Intent mIntent;

    public NotiManager(Context context, Intent intent) {
        this.mContext = context;
        this.mIntent = intent;
        mNotification = new DefaultNotification(context, intent);
    }

    public void setNotification(Notification notification) {
        this.mNotification = notification;
    }

    public void makeNoti(int notification_id) {

        int[] intArray = mContext.getResources().getIntArray(R.array.push_type);
        String[] stringArray = mContext.getResources().getStringArray(R.array.target_activity);

        for (int i = 0; i < intArray.length; i++){
            if(intArray[i] == notification_id){
                mNotification.makeNoti(notification_id, stringArray[i]);
                break;
            }
        }


    }
}
