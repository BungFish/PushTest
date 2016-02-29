package com.example.young_jin.pushtest.push;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.view.WindowManager;

import com.apms.sdk.APMS;

public class AlertDialogManager extends Activity{

    private Intent mReceivedIntent;
    private NotificationManager mNotificationManager;
    private TaskStackBuilder mStackBuilder;
    private PendingIntent resultPendingIntent;
    private BitmapDrawable mIconImg;
    private Intent mCreatedIntent;
    private int mNotiId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mReceivedIntent = getIntent();
        mIconImg = new BitmapDrawable(getResources(), (Bitmap) mReceivedIntent.getParcelableExtra("iconImg"));
        mCreatedIntent = mReceivedIntent.getParcelableExtra("intent");
        mNotiId = mReceivedIntent.getIntExtra("notiId", 0);

        mNotificationManager = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mStackBuilder = TaskStackBuilder.create(this);
        mStackBuilder.addNextIntentWithParentStack(mCreatedIntent);
        resultPendingIntent = mStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

//        Context mContext = getApplicationContext();
//        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
//        View layout = inflater.inflate( R.layout.push_dialog, (ViewGroup)findViewById( R.id.root_layout));
//        TextView text = (TextView)layout.findViewById(R.id.text);


        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//        alertDialog.setView(layout);

        // Setting Dialog Title
        alertDialog.setTitle(mReceivedIntent.getStringExtra(APMS.KEY_NOTI_TITLE));

        // Setting Dialog Message
        alertDialog.setMessage(mReceivedIntent.getStringExtra(APMS.KEY_NOTI_MSG));

        alertDialog.setIcon(mIconImg);

        alertDialog.setButton2("닫기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialog.setButton("자세히 보기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mNotificationManager.cancel(mNotiId);
                mStackBuilder.startActivities();
                finish();
            }
        });

        alertDialog.setCancelable(false);

        // Showing Alert Message
        alertDialog.show();
    }

}