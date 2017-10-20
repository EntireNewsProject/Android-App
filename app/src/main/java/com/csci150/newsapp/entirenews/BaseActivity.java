package com.csci150.newsapp.entirenews;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.csci150.newsapp.entirenews.utils.AnimUtils;
import com.csci150.newsapp.entirenews.utils.Utils;

/**
 * Created by Sif Islam on 10/13/2017 9:25 PM.
 * A news app, where you can find everything in one place.
 */

public abstract class BaseActivity extends Activity {
    private static final String TAG = "BaseActivity";

    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.print(TAG, "onCreate()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.print(TAG, "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.print(TAG, "onResume()");
    }

    protected void setupToolbar(boolean showTitle, boolean homeAsUp) {
        setActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(showTitle);
            actionBar.setDisplayHomeAsUpEnabled(homeAsUp);
            actionBar.setDisplayShowHomeEnabled(homeAsUp);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    protected void animateToolbar() {
        Utils.print(TAG, "animateToolbar()");
        TextView tvTitle = toolbar.findViewById(R.id.toolbar_title);
        tvTitle.setAlpha(0f);
        tvTitle.setScaleX(0.7f);
        tvTitle.animate()
                .alpha(1f)
                .scaleX(1f)
                .setStartDelay(300)
                .setDuration(900)
                .setInterpolator(AnimUtils.getFastOutSlowInInterpolator(this));

    }
}
