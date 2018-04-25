package com.csci150.newsapp.entirenews;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toolbar;

import com.csci150.newsapp.entirenews.utils.ApiInterface;
import com.csci150.newsapp.entirenews.utils.ApiPrefs;
import com.csci150.newsapp.entirenews.utils.Utils;

/**
 * Created by Sif Islam on 10/13/2017 9:25 PM.
 * A news app, where you can find everything in one place.
 */

public abstract class DataActivity extends Activity {
    private static final String TAG = "BaseActivity";

    protected Toolbar toolbar;
    protected Context mContext;
    protected ApiPrefs mAppPrefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        mAppPrefs = ApiPrefs.get(mContext);
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

    protected abstract void onMessage(int resId);

    protected abstract void onMessage(String msg);

    protected ApiInterface getApi() {
        return mAppPrefs.getApi();
    }
}
