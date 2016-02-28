package com.example.young_jin.pushtest;

import android.content.Context;

/**
 * Created by Jin on 2015-06-22.
 */
public class PreferenceUtil extends BasePreferenceUtil{
    private static PreferenceUtil _instance = null;

    private static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String USERNAME = "username";
    private static final String ISLOGGEDIN = "is_logged_in";

    public static synchronized PreferenceUtil instance(Context $context)
    {
        if (_instance == null)
            _instance = new PreferenceUtil($context);
        return _instance;
    }

    protected PreferenceUtil(Context $context)
    {
        super($context);
    }

    public void putRedId(String $regId)
    {
        put(PROPERTY_REG_ID, $regId);
    }

    public String regId()
    {
        return get(PROPERTY_REG_ID);
    }

    public void putAppVersion(int $appVersion)
    {
        put(PROPERTY_APP_VERSION, $appVersion);
    }

    public int appVersion()
    {
        return get(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
    }

    public void putUsername(String $username)
    {
        put(USERNAME, $username);
    }

    public String username()
    {
        return get(USERNAME);
    }

    public void putIsloggedin(Boolean $is_logged_in)
    {
        put(ISLOGGEDIN, $is_logged_in);
    }

    public Boolean is_logged_in()
    {
        return get(ISLOGGEDIN, false);
    }
}