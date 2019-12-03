package com.tiptoptips.xl.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharedPrefs(Context context) {

        preferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setInstalled(boolean value) {

        editor = preferences.edit();

        editor.putBoolean(Constants.PREF_INSTALLED, value);
        editor.apply();
    }

    public boolean getInstalled() {

        return preferences.getBoolean(Constants.PREF_INSTALLED, false);
    }

    public void setUid(String uid) {

        editor = preferences.edit();

        editor.putString(Constants.PREF_UID, uid);
        editor.apply();
    }

    public String getUid() {

        return preferences.getString(Constants.PREF_UID, "");
    }

    public void setUserEmail(String email) {

        editor = preferences.edit();

        editor.putString(Constants.PREF_USER_EMAIL, email);
        editor.apply();
    }

    public String getUserEmail() {

        return preferences.getString(Constants.PREF_USER_EMAIL, "");
    }

    public void setFirstLaunch(boolean value) {

        editor = preferences.edit();

        editor.putBoolean(Constants.PREF_FIRST_LAUNCH, value);
        editor.apply();
    }

    public boolean getFirstLaunch() {

        return preferences.getBoolean(Constants.PREF_FIRST_LAUNCH, true);
    }
}
