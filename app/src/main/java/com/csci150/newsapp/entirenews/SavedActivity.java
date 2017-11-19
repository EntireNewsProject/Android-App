package com.csci150.newsapp.entirenews;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.csci150.newsapp.entirenews.utils.Utils;

import java.util.List;


public class SavedActivity extends Activity {
    private final String TAG = "SavedActivity";

    private int mPage = 1;
    private List<NewsItem> mItems;

    private RecyclerView mRecyclerView;
    private CoordinatorLayout mCoordinatorLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private NewsItemAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.print(TAG, "onCreate()");
        setContentView(R.layout.activity_saved);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.print(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.print(TAG, "onPause()");
    }

    @Override
    protected void onDestroy() {
        Utils.print(TAG, "onDestroy()");
        super.onDestroy();
    }

}
