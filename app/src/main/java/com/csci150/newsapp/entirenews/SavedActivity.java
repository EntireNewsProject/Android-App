package com.csci150.newsapp.entirenews;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.csci150.newsapp.entirenews.utils.DBHelper;
import com.csci150.newsapp.entirenews.utils.Utils;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import nl.elastique.poetry.database.DatabaseHelper;

public class SavedActivity extends Activity {
    private final String TAG = "SavedActivity";

    private int mPage = 1;
    private List<NewsItem> mItems;

    private RecyclerView mRecyclerView;
    private CoordinatorLayout mCoordinatorLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private NewsItemAdapter mAdapter;

    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.print(TAG, "onCreate()");
        setContentView(R.layout.activity_saved);
        mDatabaseHelper = DBHelper.getHelper(this);


        new Thread(new Runnable() {
            @Override
            public void run() {
                readDatabase();
            }
        }).start();

    }

    private void readDatabase() {
        try {
            Dao<NewsItem, Integer> news = mDatabaseHelper.getDao(NewsItem.class);
            NewsItem n = news.queryForId(1);

            Log.d(TAG, String.format("'%s' %s __ %s",
                    n.getId(),
                    n.getTitle(),
                    n.getViews()));

        } catch (SQLException e) {
            Utils.print(TAG, "Error: SQLException", Log.ERROR);
            e.printStackTrace();
        }
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
        DatabaseHelper.releaseHelper();
        mDatabaseHelper = null;
        Utils.print(TAG, "onDestroy()");
        super.onDestroy();
    }

}
