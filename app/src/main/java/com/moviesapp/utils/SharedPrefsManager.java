package com.moviesapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public abstract class SharedPrefsManager {

    private static final String TAG = SharedPrefsManager.class.getSimpleName();

    protected Context mContext;
    protected String mPrefsName;

    protected SharedPrefsManager(Context context, String prefsName) {
        mContext = context.getApplicationContext();
        mPrefsName = prefsName;
    }

    public SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(mPrefsName, Context.MODE_PRIVATE);
    }

    protected void setStringPolicy(String key, String value) {
        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    protected void setBooleanPolicy(String key, boolean value) {
        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    protected void setIntegerPolicy(String key, int policy) {
        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, policy);
        editor.apply();
    }

    protected String getStringPolicy(String key, String defaultValue) {
        String value = defaultValue;
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = getSharedPreferences();
            value = sp.getString(key, defaultValue);
        }
        return value;
    }

    protected boolean getBooleanPolicy(String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (!TextUtils.isEmpty(key)) {
            SharedPreferences sp = getSharedPreferences();
            value = sp.getBoolean(key, defaultValue);
        }
        return value;
    }

    protected int getIntegerPolicy(String key, int defVal) {
        int value = defVal;
        try {
            if (!TextUtils.isEmpty(key)) {
                SharedPreferences sp = getSharedPreferences();
                value = sp.getInt(key, defVal);
            }
            return Integer.parseInt(String.valueOf(value));
        } catch (Exception e) {
            return defVal;
        }
    }
}

