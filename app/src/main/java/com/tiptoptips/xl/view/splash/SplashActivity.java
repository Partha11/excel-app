package com.tiptoptips.xl.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.tiptoptips.xl.R;
import com.tiptoptips.xl.utility.SharedPrefs;
import com.tiptoptips.xl.view.dashboard.DashboardActivity;
import com.tiptoptips.xl.view.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_text)
    AppCompatTextView splashText;

    private boolean doubleBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        splashText.setText(getResources().getString(getResources().getIdentifier("hello",
                "string", getPackageName())));

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
