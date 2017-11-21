package com.csci150.newsapp.entirenews;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.csci150.newsapp.entirenews.utils.RealmController;
import com.csci150.newsapp.entirenews.utils.Utils;

import java.util.List;

import io.realm.Realm;


public class SavedActivity extends BaseActivity implements OnListInteractionListener {
    private final String TAG = "SavedActivity";

    private int mPage = 1;
    private List<NewsItem> mItems;

    private RecyclerView mRecyclerView;
    private CoordinatorLayout mCoordinatorLayout;

    private NewsItemAdapter mAdapter;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.print(TAG, "onCreate()");
        setContentView(R.layout.activity_saved);

        mRecyclerView = findViewById(R.id.list);
        mCoordinatorLayout = findViewById(R.id.coordinator_layout);

        toolbar = findViewById(R.id.toolbar);
        setupToolbar(false, true);

        //get realm instance
        this.realm = RealmController.with(this).getRealm();

        mItems = RealmController.with(this).getNewsItems();
        //Collections.reverse(mItems);
        mAdapter = new NewsItemAdapter(this, mItems, realm, getListener());
        mRecyclerView.setAdapter(mAdapter);
    }

    private OnListInteractionListener getListener() {
        return this;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_settings:
                intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_about:
                intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onLoadMore() {

    }

    @Override
    public void onSave(boolean save, NewsItem news) {
        Utils.print(TAG, "onSave()");
        if (!save) {
            showSnackBar(R.string.response_unsaved);
            RealmController.with(this).deleteNewsItems(news.get_id());
            RealmController.with(this).refresh();
            mAdapter = new NewsItemAdapter(this, RealmController.with(this).getNewsItems(), realm, getListener());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onListFragmentInteraction(NewsItem item) {

    }

    public void showSnackBar(int resId) {
        Utils.showSnackbar(mCoordinatorLayout, getApplicationContext(), getString(resId));
    }

    public void showSnackBar(String msg) {
        Utils.showSnackbar(mCoordinatorLayout, getApplicationContext(), msg);
    }
}
