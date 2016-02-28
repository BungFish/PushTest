package com.example.young_jin.pushtest.manager;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.young_jin.pushtest.PreferenceUtil;
import com.example.young_jin.pushtest.VolleySingleton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Young-Jin on 2016-02-25.
 */
public class GcmManager {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String SENDER_ID = "819012735242";
    private String _regId;
    private GoogleCloudMessaging _gcm;
    private Activity activity;

    public GcmManager(Activity activity){
        this.activity = activity;

        if (checkPlayServices())
        {
            _gcm = GoogleCloudMessaging.getInstance(activity);
            _regId = getRegistrationId();

//            if (TextUtils.isEmpty(_regId)) {
//                registerInBackground();
//            }

        }

    }

    // google play service가 사용가능한가
    private boolean checkPlayServices()
    {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS)
        {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
            {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else
            {
                Log.i("GcmActivity", "|This device is not supported.|");
                Toast.makeText(activity, "This device is not supported.", Toast.LENGTH_SHORT).show();
                activity.finish();
            }
            return false;
        }
        return true;
    }

    public String getRegistrationId()
    {
        String registrationId = PreferenceUtil.instance(activity).regId();
        if (TextUtils.isEmpty(registrationId))
        {
            Log.i("GcmActivity", "|Registration not found.|");
            return "";
        }
        int registeredVersion = PreferenceUtil.instance(activity).appVersion();
        int currentVersion = getAppVersion();
        if (registeredVersion != currentVersion)
        {
            Log.i("GcmActivity", "|App version changed.|");
            return "";
        }
        return registrationId;
    }

    private int getAppVersion()
    {
        try
        {
            PackageInfo packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            return packageInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    //등록
    public void registerInBackground(final String url, final String username)
    {
        new AsyncTask<Void, Void, String>()
        {
            @Override
            protected String doInBackground(Void... params)
            {
                String msg = "";
                try
                {
                    if (_gcm == null)
                    {
                        _gcm = GoogleCloudMessaging.getInstance(activity);
                    }
                    _regId = _gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + _regId;

                    // For this demo: we don't need to send it because the device
                    // will send upstream messages to a server that echo back the
                    // message using the 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(_regId);
                }
                catch (IOException ex)
                {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                update_regid(url, username);
                return msg;
            }

            @Override
            protected void onPostExecute(String msg)
            {
                Log.i("GcmActivity", "|" + msg + "|");
            }
        }.execute(null, null, null);
    }

    // registraion id를 preference에 저장한다.
    private void storeRegistrationId(String regId)
    {
        int appVersion = getAppVersion();
        Log.i("GcmActivity", "|" + "Saving regId on app version " + appVersion + "|");
        PreferenceUtil.instance(activity).putRedId(regId);
        PreferenceUtil.instance(activity).putAppVersion(appVersion);
    }

    public void disableGCMID(){

    }

    public void update_regid(String url, final String username) {
        RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                activity.finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "서버와 통신할수 없습니다. 인터넷 연결상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", username);
                params.put("gcm_regid", getRegistrationId());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("abc", "value");
                return headers;
            }
        };

        requestQueue.add(request);
    }

    public void delete_regid(String url) {
        RequestQueue requestQueue = VolleySingleton.getsInstance().getRequestQueue();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                PreferenceUtil.instance(activity).putUsername("");
                PreferenceUtil.instance(activity).putIsloggedin(false);
                Toast.makeText(activity, "로그아웃", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "서버와 통신할수 없습니다. 정상적으로 로그아웃되지 않았습니다.", Toast.LENGTH_SHORT).show();
                Log.d("MYTAG", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", PreferenceUtil.instance(activity).username());
                params.put("gcm_regid", "");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("abc", "value");
                return headers;
            }
        };

        requestQueue.add(request);
    }
}
