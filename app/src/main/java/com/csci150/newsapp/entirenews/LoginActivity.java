package com.csci150.newsapp.entirenews;

import android.content.Intent;
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

public class LoginActivity extends DataActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    private CoordinatorLayout mCoordinatorLayout;
    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Utils.print(TAG, "onCreate()");

        mCoordinatorLayout = findViewById(R.id.coordinator_layout);

        Button btLogin = findViewById(R.id.btLogIn);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        etUsername.setText("thunder");
        etPassword.setText("123456");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String message = bundle.getString("message", null);
            if (message != null)
                onMessage(message);
        }

        btLogin.setOnClickListener(this);
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
            case R.id.btLogIn:
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                login(username, password);
        }
    }

    private void login(String username, String password) {
        Utils.print(TAG, "login()");
        getApi().login(username, password).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
                Utils.print(TAG, "onResponse()");
                Utils.print(TAG, "URL: " + response.raw().request().url());
                Utils.print(TAG, "Status Code: " + response.code());
                if (response.isSuccessful()) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("message", response.body().getMessage());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();

                } else {
                    Utils.print(TAG, "ServerResponse: " + response.message(), Log.ERROR);
                    Utils.showSnackbar(mCoordinatorLayout, mContext, "Login failed, please try again", true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                Utils.print(TAG, "onFailure()", Log.ERROR);
                Utils.print(TAG, t.toString(), Log.ERROR);
                Utils.showSnackbar(mCoordinatorLayout, mContext, R.string.response_fail, true);
            }
        });
    }
}


