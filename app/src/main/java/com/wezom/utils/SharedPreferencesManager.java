package com.wezom.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

public class SharedPreferencesManager {

    private static final String KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN";
    private static final String KEY_REFRESH_TOKEN = "KEY_REFRESH_TOKEN";
    private static final String KEY_TOKEN_EXP_TIME = "KEY_TOKEN_EXP_TIME";

    private SharedPreferences sp;

    public SharedPreferencesManager(Context context) {
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getAccessToken() { return sp.getString(KEY_ACCESS_TOKEN, null); }
    public void setAccessToken(String token) { sp.edit().putString(KEY_ACCESS_TOKEN, token).apply(); }

    public String getRefreshToken() { return sp.getString(KEY_REFRESH_TOKEN, null); }
    public void setRefreshToken(String token) { sp.edit().putString(KEY_REFRESH_TOKEN, token).apply(); }

    public long getTokenExpTime() { return sp.getLong(KEY_TOKEN_EXP_TIME, 0); }
    public void setTokenExpTime(long time) { sp.edit().putLong(KEY_TOKEN_EXP_TIME, time).apply(); }
}
