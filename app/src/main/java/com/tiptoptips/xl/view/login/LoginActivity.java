package com.tiptoptips.xl.view.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.tiptoptips.xl.R;
import com.tiptoptips.xl.utility.SharedPrefs;
import com.tiptoptips.xl.utility.Utils;
import com.tiptoptips.xl.view.dashboard.DashboardActivity;
import com.tiptoptips.xl.viewmodel.LoginViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_email)
    TextInputEditText loginEmail;
    @BindView(R.id.login_password)
    TextInputEditText loginPassword;
    @BindView(R.id.login_button)
    AppCompatButton loginButton;
    @BindView(R.id.login_layout)
    LinearLayout loginLayout;
    @BindView(R.id.sign_up_button)
    AppCompatTextView signUpButton;
    @BindView(R.id.register_email)
    TextInputEditText registerEmail;
    @BindView(R.id.register_password)
    TextInputEditText registerPassword;
    @BindView(R.id.register_confirm_password)
    TextInputEditText registerConfirmPassword;
    @BindView(R.id.register_button)
    AppCompatButton registerButton;
    @BindView(R.id.register_layout)
    LinearLayout registerLayout;
    @BindView(R.id.guide_to_register)
    LinearLayout guideToRegister;
    @BindView(R.id.sign_in_button)
    AppCompatTextView signInButton;
    @BindView(R.id.guide_to_login)
    LinearLayout guideToLogin;
    @BindView(R.id.confirm_email)
    TextInputEditText confirmEmail;
    @BindView(R.id.forgotPassword_layout)
    LinearLayout forgotPasswordLayout;

    private LoginViewModel viewModel;
    private ProgressDialog progressDialog;
    private SharedPrefs prefs;

    private boolean doubleBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        prefs = new SharedPrefs(this);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    private void initialize(String message) {

        progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Loading");
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @OnClick({R.id.login_button, R.id.sign_up_button, R.id.register_button, R.id.sign_in_button, R.id.forgotPassword, R.id.send_button})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.login_button:

                if (TextUtils.isEmpty(Objects.requireNonNull(loginEmail.getText()).toString())) {

                    loginEmail.setError(getResources().getString(R.string.email_error));

                } else if (!Utils.isValidEmail(loginEmail.getText().toString())) {

                    loginEmail.setError(getResources().getString(R.string.email_invalid));

                } else if (TextUtils.isEmpty(Objects.requireNonNull(loginPassword.getText()).toString())) {

                    loginPassword.setError(getResources().getString(R.string.email_error));

                } else {

                    initialize(getResources().getString(R.string.validating));

                    viewModel.loginUser(loginEmail.getText().toString(), loginPassword.getText().toString())
                            .observe(this, status -> {

                                progressDialog.dismiss();

                                if (status.isSuccess()) {

                                    prefs.setUid(status.getUserId());
                                    prefs.setUserEmail(loginEmail.getText().toString().trim());
                                    prefs.setInstalled(true);
                                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                    finish();

                                } else {

                                    Toast.makeText(this, status.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                break;

            case R.id.sign_up_button:

                setLayoutVisibility("register");
                break;

            case R.id.register_button:

                if (TextUtils.isEmpty(Objects.requireNonNull(registerEmail.getText()).toString())) {

                    registerEmail.setError(getResources().getString(R.string.email_error));

                } else if (!Utils.isValidEmail(registerEmail.getText().toString())) {

                    registerEmail.setError(getResources().getString(R.string.email_invalid));

                } else if (TextUtils.isEmpty(Objects.requireNonNull(registerPassword.getText()).toString())) {

                    registerPassword.setError(getResources().getString(R.string.email_error));

                } else if (TextUtils.isEmpty(Objects.requireNonNull(registerConfirmPassword.getText()).toString())) {

                    registerConfirmPassword.setError(getResources().getString(R.string.email_error));

                } else if (!TextUtils.equals(registerPassword.getText().toString(), registerConfirmPassword.getText().toString())) {

                    registerConfirmPassword.setError(getResources().getString(R.string.passwords_dont_match));

                } else if (registerPassword.getText().toString().length() < 6) {

                    registerPassword.setError(getResources().getString(R.string.password_length_error));

                } else {

                    initialize(getResources().getString(R.string.validating));

                    viewModel.registerUser(registerEmail.getText().toString(), registerPassword.getText().toString())
                            .observe(this, status -> {

                                progressDialog.dismiss();

                                if (status.isSuccess()) {

                                    prefs.setUid(status.getUserId());
                                    prefs.setUserEmail(registerEmail.getText().toString().trim());
                                    prefs.setInstalled(true);
                                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                    finish();

                                } else {

                                    Toast.makeText(this, status.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                break;

            case R.id.sign_in_button:

                setLayoutVisibility("login");
                break;

            case R.id.forgotPassword:

                setLayoutVisibility("forgotPassword");
                break;

            case R.id.send_button:      //works pending here..................................
                break;

        }
    }

    public void setLayoutVisibility(String flag) {

        switch (flag) {

            case "register":

                forgotPasswordLayout.setVisibility(View.GONE);
                loginLayout.setVisibility(View.GONE);
                registerLayout.setVisibility(View.VISIBLE);
                guideToRegister.setVisibility(View.GONE);
                guideToLogin.setVisibility(View.VISIBLE);
                break;

            case "login":

                forgotPasswordLayout.setVisibility(View.GONE);
                registerLayout.setVisibility(View.GONE);
                loginLayout.setVisibility(View.VISIBLE);
                guideToLogin.setVisibility(View.GONE);
                guideToRegister.setVisibility(View.VISIBLE);
                break;

            case "forgotPassword":

                registerLayout.setVisibility(View.GONE);
                loginLayout.setVisibility(View.GONE);
                guideToLogin.setVisibility(View.GONE);
                guideToRegister.setVisibility(View.GONE);
                forgotPasswordLayout.setVisibility(View.VISIBLE);
                break;
        }
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

