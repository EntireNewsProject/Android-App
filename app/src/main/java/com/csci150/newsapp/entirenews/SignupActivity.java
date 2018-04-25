package com.csci150.newsapp.entirenews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.csci150.newsapp.entirenews.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends DataActivity implements View.OnClickListener {
    private static final String TAG = "SignupActivity";

    private CoordinatorLayout mCoordinatorLayout;
    private EditText etUsername, etPassword, etFullName, etEmail, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Utils.print(TAG, "onCreate()");

        mCoordinatorLayout = findViewById(R.id.coordinator_layout);
        Button btRegister = findViewById(R.id.btSignUp);

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etCPassword);

        btRegister.setOnClickListener(this);
    }

    @Override
    protected void onMessage(int resId) {
        Utils.print(TAG, "onMessage(int)");
        Utils.showSnackbar(mCoordinatorLayout, mContext, resId);
    }

    @Override
    protected void onMessage(String msg) {
        Utils.print(TAG, "onMessage(String)");
        Utils.showSnackbar(mCoordinatorLayout, mContext, msg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSignUp:
                String fullName = etFullName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                if (password.equals(confirmPassword))
                    register(fullName, username, email, password);
                else
                    onMessage("Confirm password don't match. Please try again.");
                break;
        }
    }

    private void register(String fullName, String username, String email, String password) {
        Utils.print(TAG, "register()");
        getApi().register(email, password, username, fullName).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(@NonNull Call<Register> call, @NonNull Response<Register> response) {
                Utils.print(TAG, "onResponse()");
                Utils.print(TAG, "URL: " + response.raw().request().url());
                Utils.print(TAG, "Status Code: " + response.code());
                if (response.isSuccessful()) {
                    onMessage(response.body().getMessage());
                    //TODO start login, close this one
                    onBackPressed();
                } else {
                    //TODO get error message from server
                    Utils.print(TAG, "ServerResponse: " + response.message(), Log.ERROR);
                    Utils.showSnackbar(mCoordinatorLayout, mContext, "Registration failed, please try again", true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Register> call, @NonNull Throwable t) {
                Utils.print(TAG, "onFailure()", Log.ERROR);
                Utils.print(TAG, t.toString(), Log.ERROR);
                Utils.showSnackbar(mCoordinatorLayout, mContext, R.string.response_fail, true);
            }
        });
    }
}
