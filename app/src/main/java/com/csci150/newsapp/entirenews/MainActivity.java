package com.csci150.newsapp.entirenews;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.csci150.newsapp.entirenews.utils.FontAwareTabLayout;
import com.csci150.newsapp.entirenews.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends BaseActivity implements OnFragmentInteractionListener {
    private final String TAG = "MainActivity";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private PagerAdapter mPagerAdapter;
    private CoordinatorLayout mCoordinatorLayout;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private String[] mTabsChoicesAll;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.print(TAG, "onCreate()");
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mTabsChoicesAll = getResources().getStringArray(R.array.list_sources_names);

        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        mCoordinatorLayout = findViewById(R.id.coordinator_layout);
        setupToolbar(false, true);
        // if (savedInstanceState == null)
        animateToolbar();

        tabLayout = findViewById(R.id.tabs);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mPagerAdapter = new PagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new FontAwareTabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new FontAwareTabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        for (String str : mTabsChoicesAll) {
            if (sp.getBoolean(Utils.createSlug(str), true)) {
                tabLayout.addTab(tabLayout.newTab().setText(str));
                mPagerAdapter.addTabPage(str);
                Utils.print(TAG, "addTab(" + str + ")");
            }
        }
        if (mPagerAdapter.getSelectedTabs().size() == 0) {
            for (String str : mTabsChoicesAll) {
                tabLayout.addTab(tabLayout.newTab().setText(str));
                mPagerAdapter.addTabPage(str);
                Utils.print(TAG, "addForcedTab(" + str + ")");
            }
        }
        //tabLayout.post(tabLayoutConfig);
        //Utils.showSnackbar(mCoordinatorLayout, getApplicationContext(), R.string.response_saved);
    }

    private Runnable tabLayoutConfig = new Runnable() {
        @Override
        public void run() {
            if (tabLayout.getWidth() < MainActivity.this.getResources().getDisplayMetrics().widthPixels) {
                tabLayout.setTabMode(FontAwareTabLayout.MODE_FIXED);
                ViewGroup.LayoutParams mParams = tabLayout.getLayoutParams();
                mParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                tabLayout.setLayoutParams(mParams);
            } else
                tabLayout.setTabMode(FontAwareTabLayout.MODE_SCROLLABLE);
        }
    };

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.print(TAG, "onResume()");

        List<String> temp = new ArrayList<>();
        for (String tab : mTabsChoicesAll) {
            if (sp.getBoolean(Utils.createSlug(tab), true))
                temp.add(tab);
        }
        if (mPagerAdapter.getSelectedTabs().equals(temp))
            Utils.print(TAG, "Tabs not changed");
        else {
            Utils.print(TAG, "Tabs changed");
            tabLayout.removeAllTabs();
            mPagerAdapter.removeTabs();
            if (temp.size() == 0) {
                for (String str : mTabsChoicesAll) {
                    tabLayout.addTab(tabLayout.newTab().setText(str));
                    mPagerAdapter.addTabPage(str);
                    Utils.print(TAG, "addForcedTab(" + str + ")");
                }
            } else {
                for (String str : temp) {
                    tabLayout.addTab(tabLayout.newTab().setText(str));
                    mPagerAdapter.addTabPage(str);
                    Utils.print(TAG, "addTab(" + str + ")");
                }
            }
            mViewPager.setAdapter(mPagerAdapter);
            //tabLayout.post(tabLayoutConfig);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_saved:
                intent = new Intent(getApplicationContext(), SavedActivity.class);
                startActivity(intent);
                return true;
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
    public void showSnackBar(int resId) {
        Utils.showSnackbar(mCoordinatorLayout, getApplicationContext(), getString(resId));
    }

    @Override
    public void showSnackBar(String msg) {
        Utils.showSnackbar(mCoordinatorLayout, getApplicationContext(), msg);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class PagerAdapter extends FragmentStatePagerAdapter {
        private List<String> mTabsChoicesSelected;

        PagerAdapter(FragmentManager fm) {
            super(fm);
            mTabsChoicesSelected = new ArrayList<>();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Fragment getItem(int position) {
            Utils.print(TAG, "getItem()");
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            String slug = Utils.createSlug(mTabsChoicesSelected.get(position));
            return NewsItemFragment.newInstance(slug);
        }

        @Override
        public int getCount() {
            return mTabsChoicesSelected.size();
        }

        List<String> getSelectedTabs() {
            return mTabsChoicesSelected;
        }

        void addTabPage(String title) {
            mTabsChoicesSelected.add(title);
            notifyDataSetChanged();
        }

        void removeTabPage(int position) {
            if (!mTabsChoicesSelected.isEmpty() && position < mTabsChoicesSelected.size()) {
                mTabsChoicesSelected.remove(position);
                notifyDataSetChanged();
            }
        }

        void removeTabs() {
            if (!mTabsChoicesSelected.isEmpty()) {
                mTabsChoicesSelected = new ArrayList<>();
                notifyDataSetChanged();
            }
        }
    }
}
