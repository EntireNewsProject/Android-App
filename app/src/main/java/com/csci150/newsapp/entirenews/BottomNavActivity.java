package com.csci150.newsapp.entirenews;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.MenuItem;

import com.csci150.newsapp.entirenews.utils.ApiPrefs;
import com.csci150.newsapp.entirenews.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomNavActivity extends Activity implements OnFragmentInteractionListener {
    private final String TAG = "BottomNavActivity";

    private Fragment fragment;
    private FragmentManager fragmentManager;
    //public ApiInterface api;
    private ApiPrefs mAppPrefs;
    private CoordinatorLayout mCoordinatorLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_all_news:
                    fragment = AllNewsFragment.newInstance();
                    //fragment = NewsItemFragment.newInstance(2, "trending");
                    break;
                case R.id.navigation_recommended:
                    fragment = NewsItemFragment.newInstance(1, "recommendations");
                    break;
                case R.id.navigation_trending:
                    fragment = NewsItemFragment.newInstance(2, "trending");
                    break;
                case R.id.navigation_saved:
                    fragment = SavedFragment.newInstance();
                    break;
            }
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment, null).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        Utils.print(TAG, "onCreate");

        fragmentManager = getFragmentManager();
        mAppPrefs = ApiPrefs.get(this);

        mCoordinatorLayout = findViewById(R.id.coordinator_layout);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            Utils.print(TAG, "savedInstanceState == null");
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, AllNewsFragment.newInstance(), null)
                    .commit();
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String message = bundle.getString("message", null);
            String token = bundle.getString("token", null);
            String fullName = bundle.getString("fullName", "");
            String username = bundle.getString("username", null);
            String email = bundle.getString("email", null);
            int type = bundle.getInt("type", 0);
            if (message != null)
                Utils.showSnackbar(mCoordinatorLayout, getApplicationContext(), message);
            if (token != null && username != null && email != null) {
                User user = new User(fullName, username, email, type);
                mAppPrefs.login(user, token);
            }
        }
    }

    private void ping() {
        Utils.print(TAG, "ping(token)");
        mAppPrefs.getApi().ping(mAppPrefs.getAccessToken()).enqueue(new Callback<Ping>() {
            @Override
            public void onResponse(@NonNull Call<Ping> call, @NonNull Response<Ping> response) {
                Utils.print(TAG, "onResponse(ping)");
                Utils.print(TAG, "URL: " + response.raw().request().url());
                Utils.print(TAG, "Status Code: " + response.code());
                if (response.isSuccessful()) {
                    mAppPrefs.login(response.body().getUser(), response.body().getToken());
                    updateUi();
                } else {
                    Utils.print(TAG, "ServerResponse: " + response.message(), Log.ERROR);
                    updateUi();
                    mAppPrefs.logout();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Ping> call, @NonNull Throwable t) {
                Utils.print(TAG, "onFailure(ping)", Log.ERROR);
                Utils.print(TAG, t.toString(), Log.ERROR);
                updateUi();
                mAppPrefs.logout();
            }
        });
    }

    private void updateUi() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.print(TAG, "onResume");
        if (mAppPrefs.isLoggedIn()) ping();
        else updateUi();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.print(TAG, "onPause");
    }

    @Override
    public void showSnackBar(int resId) {
        Utils.showSnackbar(mCoordinatorLayout, getApplicationContext(), resId);
    }

    @Override
    public void showSnackBar(String msg) {
        Utils.showSnackbar(mCoordinatorLayout, getApplicationContext(), msg);
    }
}
