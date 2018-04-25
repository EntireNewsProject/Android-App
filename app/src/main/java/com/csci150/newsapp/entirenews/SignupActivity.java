package com.csci150.newsapp.entirenews;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.csci150.newsapp.entirenews.utils.Utils;

public class SignupActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "SignupActivity";

    private Button btRegister;
    private EditText etUsername, etPassword, etFullName, etEmail, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Utils.print(TAG, "onCreate()");

        btRegister = findViewById(R.id.btSignUp);

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etCPassword);

        btRegister.setOnClickListener(this);
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



                break;
        }
    }
}
