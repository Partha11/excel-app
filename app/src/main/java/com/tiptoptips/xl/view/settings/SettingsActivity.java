package com.tiptoptips.xl.view.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tiptoptips.xl.R;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.settings);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new SettingsFragment())
                .commit();
    }
}
