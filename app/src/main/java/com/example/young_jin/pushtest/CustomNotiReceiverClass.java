package com.example.young_jin.pushtest;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.apms.sdk.APMSPopup;
import com.apms.sdk.bean.PushMsg;
import com.example.young_jin.pushtest.push.NotiManager;

import java.io.Serializable;

/**
 * Created by Young-Jin on 2016-02-26.
 */

public class CustomNotiReceiverClass extends BroadcastReceiver implements Serializable{

    @Override
    public void onReceive(final Context context, Intent intent) {
        // notification 터치 시에 “push notiMsg:${notiMsg}” 라는 텍스트를 Toast로 출력합니다.

        PushMsg pushMsg = new PushMsg(intent.getExtras());
        Log.i("==============", pushMsg.toString() + "");

        if(pushMsg.msgId != null){
            int msgId = Integer.valueOf(pushMsg.msgId);

            NotiManager notiManager = new NotiManager(context, intent);
            notiManager.makeNoti(msgId);

        }

    }

}
