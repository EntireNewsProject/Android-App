package com.csci150.newsapp.entirenews;

import android.app.Activity;
import android.os.Bundle;

public class PrivatePolicyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_policy);

        toolbar = findViewById(R.id.toolbar);
        setupToolbar(false, true);
    }
}


