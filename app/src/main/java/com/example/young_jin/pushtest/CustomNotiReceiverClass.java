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
import com.example.young_jin.pushtest.push.BuyCardNotification;
import com.example.young_jin.pushtest.push.NotiManager;

import java.io.Serializable;

/**
 * Created by Young-Jin on 2016-02-26.
 */

public class CustomNotiReceiverClass extends BroadcastReceiver implements Serializable{

    private TaskStackBuilder stackBuilder;
    private PendingIntent resultPendingIntent;
    private NotificationCompat.Builder mBuilder;
    private Intent i;
    private Intent pushIntent;
    private APMSPopup aPmsPopup;

    @Override
    public void onReceive(final Context context, Intent intent) {
        // notification 터치 시에 “push notiMsg:${notiMsg}” 라는 텍스트를 Toast로 출력합니다.

        PushMsg pushMsg = new PushMsg(intent.getExtras());
        Log.i("==============", pushMsg.toString() + "");

        NotiManager notiManager = new NotiManager();
        int msgId = Integer.valueOf(pushMsg.msgId);
        int[] intArray = context.getResources().getIntArray(R.array.push_type);

        for (int i = 0; i < intArray.length; i++){
            if(msgId == intArray[i]){
                notiManager.setNotification(new BuyCardNotification(context, intent));
                notiManager.makeNoti(intArray[i]);

                break;
            }
        }

//        if(pushMsg.msgId != null) {
//            int msgId = Integer.valueOf(pushMsg.msgId);
//            switch (msgId) {
//                case 6500799:
//                    notification = new BuyCardNotification(context, intent);
//                    notification.makeNoti(MainActivity.class, R.integer.push_type_1);
//
//                    break;
//                case 6500879:
//                    notification = new CancelCardNotification(context, intent);
//                    notification.makeNoti(SecondActivity.class, R.integer.push_type_2);
//
//                    break;
//                case R.integer.push_type_3:
//                    notification = new CancelCardNotification(context, intent);
//                    notification.makeNoti(ThirdActivity.class, R.integer.push_type_3);
//
//                    break;
//                default:
//                    notification = new BuyCardNotification(context, intent);
//                    notification.makeNoti(MainActivity.class, R.integer.push_type_1);
//            }
//        }

    }

}
