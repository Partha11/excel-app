package com.tiptoptips.xl.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharedPrefs(Context context) {

        preferences = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setInstalled() {

        editor = preferences.edit();

        editor.putBoolean(Constants.PREF_INSTALLED, true);
        editor.apply();
    }

    public boolean getInstalled() {

        return preferences.getBoolean(Constants.PREF_INSTALLED, false);
    }
}
