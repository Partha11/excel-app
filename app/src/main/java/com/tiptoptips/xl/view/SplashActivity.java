package com.tiptoptips.xl.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.tiptoptips.xl.R;
import com.tiptoptips.xl.utility.SharedPrefs;
import com.tiptoptips.xl.view.dashboard.DashboardActivity;
import com.tiptoptips.xl.view.splash.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPrefs prefs = new SharedPrefs(this);
        new Handler().postDelayed(() -> {

            Intent intent;

            if (prefs.getInstalled()) {

                intent = new Intent(SplashActivity.this, DashboardActivity.class);

            } else {

                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }

            startActivity(intent);
            finish();

        }, 1200);
    }
}
