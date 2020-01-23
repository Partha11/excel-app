package com.tiptoptips.xl.view.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import com.tiptoptips.xl.R;


public class SettingsFragment extends PreferenceFragmentCompat {

    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    public SettingsFragment() {

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.settings, rootKey);
        listenForChanges();
    }

    private void listenForChanges() {

        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {

            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


                if (key.equals(getContext().getResources().getString(R.string.app_textsize_key))) {

                    findPreference(key).setSummary(sharedPreferences.getString(key, ""));

                } else if (key.equals(getContext().getResources().getString(R.string.guidelines_key))) {

                    findPreference(key).callChangeListener(sharedPreferences.getBoolean(key, false));

                } else if (key.equals(getContext().getResources().getString(R.string.table_textsize_key))) {

                    findPreference(key).setSummary(sharedPreferences.getString(key, ""));

                } else if (key.equals(getContext().getResources().getString(R.string.currency_key))) {

                    findPreference(key).setSummary(sharedPreferences.getString(key, ""));

                } else if (key.equals(getContext().getResources().getString(R.string.number_separator_key))) {

                    findPreference(key).setSummary(sharedPreferences.getString(key, ""));

                } else if (key.equals(getContext().getResources().getString(R.string.emptycell_display_key))) {

                    findPreference(key).callChangeListener(sharedPreferences.getBoolean(key, false));

                }
            }
        };
    }

    public void updateSavedSettings() {

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();

        String key = getContext().getResources().getString(R.string.app_textsize_key);
        findPreference(key).setSummary(sharedPreferences.getString(key, ""));

        key = getContext().getResources().getString(R.string.guidelines_key);
        findPreference(key).callChangeListener(sharedPreferences.getBoolean(key, false));

        key = getContext().getResources().getString(R.string.table_textsize_key);
        findPreference(key).setSummary(sharedPreferences.getString(key, ""));

        key = getContext().getResources().getString(R.string.currency_key);
        findPreference(key).setSummary(sharedPreferences.getString(key, ""));

        key = getContext().getResources().getString(R.string.number_separator_key);
        findPreference(key).setSummary(sharedPreferences.getString(key, ""));

        key = getContext().getResources().getString(R.string.emptycell_display_key);
        findPreference(key).callChangeListener(sharedPreferences.getBoolean(key, false));

    }

    @Override
    public void onResume() {

        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        updateSavedSettings();
    }

    @Override
    public void onPause() {

        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);

    }

}