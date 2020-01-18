package com.tiptoptips.xl.view.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.tiptoptips.xl.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.settings, rootKey);
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        return inflater.inflate(R.layout.fragment_settings, container, false);
//    }
}
