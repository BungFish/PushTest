package com.example.young_jin.pushtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.apms.sdk.IAPMSConsts;
import com.apms.sdk.api.request.ReadMsg;
import com.apms.sdk.bean.PushMsg;
import com.apms.sdk.common.util.APMSUtil;
import com.apms.sdk.common.util.CLog;

import org.json.JSONArray;

/**
 * Created by Young-Jin on 2016-02-26.
 */
public class PushNotiReceiver extends BroadcastReceiver implements IAPMSConsts {

    @Override
    public void onReceive (Context context, Intent intent) {
        CLog.i("onReceive");

        PushMsg msg = new PushMsg(intent.getExtras());
        JSONArray reads = new JSONArray();
        reads.put(APMSUtil.getReadParam(msg.msgId));

        new ReadMsg(context).request(reads, null);

        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.putExtras(intent.getExtras());

        context.startActivity(i);
    }
}
