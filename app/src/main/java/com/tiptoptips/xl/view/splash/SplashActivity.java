package com.tiptoptips.xl.view.splash;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tiptoptips.xl.R;
import com.tiptoptips.xl.utility.SharedPrefs;
import com.tiptoptips.xl.view.dashboard.DashboardActivity;
import com.tiptoptips.xl.view.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.statusTS)
    TextSwitcher statusTS;
    private boolean doubleBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        initialize();
    }

    public void initialize() {

        statusTS.setFactory(() -> {

            TextView textView = new TextView(SplashActivity.this);
            textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextSize(18);
            textView.setTextColor(Color.WHITE);
            return textView;
        });

        statusTS.setInAnimation(this, android.R.anim.slide_in_left);
        statusTS.setOutAnimation(this, android.R.anim.slide_out_right);
        statusTS.setText(getResources().getString(R.string.initializing));

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

    @Override
    public void onBackPressed() {

        if (doubleBackPressed) {

            super.onBackPressed();
            return;
        }

        this.doubleBackPressed = true;
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> doubleBackPressed = false, 2000);
    }
}
