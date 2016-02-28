package com.example.young_jin.pushtest.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.view.WindowManager;

import com.apms.sdk.APMS;
import com.example.young_jin.pushtest.MainActivity;
import com.example.young_jin.pushtest.R;
import com.example.young_jin.pushtest.activities.SecondActivity;
import com.example.young_jin.pushtest.activities.ThirdActivity;

public class AlertDialogManager extends Activity{

    private Intent intent;
    private Intent i;
    private NotificationManager mNotificationManager;
    private TaskStackBuilder stackBuilder;
    private PendingIntent resultPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        intent = getIntent();

        mNotificationManager = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int noti_id = intent.getIntExtra("activity", R.integer.push_type_1);

        if(noti_id == R.integer.push_type_1){
            i = new Intent(AlertDialogManager.this, MainActivity.class);
        } else if (noti_id == R.integer.push_type_2){
            i = new Intent(AlertDialogManager.this, SecondActivity.class);
        } else if(noti_id == R.integer.push_type_3){
            i = new Intent(AlertDialogManager.this, ThirdActivity.class);
        }

        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        i.putExtra("msg", intent.getStringExtra(APMS.KEY_NOTI_MSG));

        stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(i);
        resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

//        Context mContext = getApplicationContext();
//        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
//        View layout = inflater.inflate( R.layout.push_dialog, (ViewGroup)findViewById( R.id.root_layout));
//        TextView text = (TextView)layout.findViewById(R.id.text);
//        text.setText(intent.getStringExtra(APMS.KEY_NOTI_MSG));


        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//        alertDialog.setView(layout);

        // Setting Dialog Title
        alertDialog.setTitle(intent.getStringExtra(APMS.KEY_NOTI_TITLE));

        // Setting Dialog Message
        alertDialog.setMessage(intent.getStringExtra(APMS.KEY_NOTI_MSG));

        alertDialog.setIcon(R.drawable.jamon3);

        alertDialog.setButton2("닫기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialog.setButton("자세히 보기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mNotificationManager.cancel(intent.getIntExtra("activity", R.integer.push_type_1));
                stackBuilder.startActivities();
                finish();
            }
        });

        alertDialog.setCancelable(false);

        // Showing Alert Message
        alertDialog.show();
    }

}