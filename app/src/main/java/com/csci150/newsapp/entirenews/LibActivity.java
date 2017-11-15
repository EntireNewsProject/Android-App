package com.csci150.newsapp.entirenews;

import android.app.Activity;
import android.os.Bundle;

public class LibActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib);

        toolbar = findViewById(R.id.toolbar);
        setupToolbar(false, true);
    }
}


