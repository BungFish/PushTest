package com.example.young_jin.pushtest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.apms.sdk.APMS;
import com.apms.sdk.api.APIManager;
import com.apms.sdk.api.request.DeviceCert;
import com.apms.sdk.api.request.LoginPms;
import com.apms.sdk.api.request.LogoutPms;
import com.apms.sdk.api.request.SetConfig;
import com.apms.sdk.bean.PushMsg;
import com.apms.sdk.common.util.Prefs;
import com.example.young_jin.pushtest.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Prefs prefs;
    private Intent intent;
    private PushMsg pushMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = getIntent();

        APMS.getInstance(getApplicationContext());
        prefs = new Prefs(getApplicationContext());


        textView = (TextView) findViewById(R.id.textView);
//        textView.setText(APMSUtil.getGCMToken(getApplicationContext()));

        if(intent.getStringExtra("msg") != null){
            textView.setText(intent.getStringExtra("msg"));
        }

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject userData = new JSONObject();
                try {
                    userData.put("custName", "test");
                    userData.put("phoneNumber", "010xxxxxxxx");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new DeviceCert(getApplicationContext()).request(userData, new APIManager.APICallback() {
                    public void response(String code, JSONObject json) {
                        textView.setText(json.toString());
                    }
                });
            }
        });

        Button login = (Button) findViewById(R.id.button2);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject userData = new JSONObject();
                try {
                    userData.put("custName", "user");
                    userData.put("phoneNumber", "010xxxxxxxx");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new LoginPms(getApplicationContext()).request("userid", userData, new APIManager.APICallback() {
                    public void response(String code, JSONObject json) {
                        textView.setText(json.toString());
                    }
                });
            }
        });

        Button nextMsg = (Button) findViewById(R.id.button3);
        nextMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                new NewMsg(getApplicationContext()).request("N", APMS.getInstance(getApplicationContext()).getMaxUserMsgId(), "-1", "1", "50", new APIManager.APICallback() {
//                    public void response(String code, JSONObject json) {
//                        try {
//                            JSONArray jsonArray = json.getJSONArray("msgs");
//                            String str = "";
//                            for (int i = 0; i < jsonArray.length(); i++){
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                str += jsonObject.getString("msgId")+": "+ jsonObject.getString("pushMsg")+": "+ jsonObject.getString("msgText")+"\n";
//                            }
//                            textView.setText(str);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });

                new SetConfig(getApplicationContext()).request("Y", "Y", "Y", "0000", "0000", new APIManager.APICallback() {
                    @Override
                    public void response(String code, JSONObject json) {
                        textView.setText(json.toString());
                    }
                });

            }
        });

        Button prevMsg = (Button) findViewById(R.id.button4);
        prevMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                new NewMsg(getApplicationContext()).request("P", APMS.getInstance(getApplicationContext()).getMaxUserMsgId(), "-1", "1", "1", new APIManager.APICallback() {
//                    public void response(String code, JSONObject json) {
//                        textView.setText(json.toString());
//                    }
//                });

                new LogoutPms(getApplicationContext()).request(new APIManager.APICallback() {
                    @Override
                    public void response (String code, JSONObject json) {
                        textView.setText(json.toString());
                    }
                });

            }
        });

    }
}
