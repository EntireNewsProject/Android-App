package com.csci150.newsapp.entirenews;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.csci150.newsapp.entirenews.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnListFragmentInteractionListener {
    private final String TAG = "MainActivity";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private String[] mTabsChoicesAll;
    private List<String> mTabsChoicesSelected = new ArrayList<>();
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.print(TAG, "onCreate()");
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mTabsChoicesAll = getResources().getStringArray(R.array.list_sources_names);

        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setupToolbar(false, true);
        // if (savedInstanceState == null)
        animateToolbar();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = findViewById(R.id.tabs);

        /*for (int i = 0; i < mTabChoicesLen; i++) {
            mTabsChoicesId[i] = Utils.createSlug(mTabsChoicesName[i]);
            if (sp.getBoolean(Utils.createSlug(mTabsChoicesId[i]), true)) {
                tabLayout.addTab(tabLayout.newTab().setText(mTabsChoicesId[i]));
                Utils.print(TAG, "addTab(" + mTabsChoicesId[i] + ")");
            }
        }*/
        if (!mTabsChoicesSelected.isEmpty())
            mTabsChoicesSelected = new ArrayList<>();
        for (String str : mTabsChoicesAll) {
            if (sp.getBoolean(Utils.createSlug(str), true)) {
                mTabsChoicesSelected.add(str);
                tabLayout.addTab(tabLayout.newTab().setText(str));
                Utils.print(TAG, "addTab(" + str + ")");
            }
        }
        if (mTabsChoicesSelected.size() == 0)
            for (String str : mTabsChoicesAll) {
                mTabsChoicesSelected.add(str);
                tabLayout.addTab(tabLayout.newTab().setText(str));
                Utils.print(TAG, "addForcedTab(" + str + ")");
            }

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    private Runnable tabLayoutConfig = new Runnable() {
        @Override
        public void run() {
            if (tabLayout.getWidth() < MainActivity.this.getResources().getDisplayMetrics().widthPixels) {
                tabLayout.setTabMode(TabLayout.MODE_FIXED);
                ViewGroup.LayoutParams mParams = tabLayout.getLayoutParams();
                mParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                tabLayout.setLayoutParams(mParams);
            } else
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Utils.print(TAG, "onResume()");
        if (!mTabsChoicesSelected.isEmpty())
            mTabsChoicesSelected = new ArrayList<>();
        tabLayout.removeAllTabs();
        //mSectionsPagerAdapter.notifyDataSetChanged();
        Utils.print(TAG, "Tabs changed");
        for (String str : mTabsChoicesAll) {
            if (sp.getBoolean(Utils.createSlug(str), true)) {
                mTabsChoicesSelected.add(str);
                tabLayout.addTab(tabLayout.newTab().setText(str));
                Utils.print(TAG, "addTab(" + str + ")");
            }
        }
        if (mTabsChoicesSelected.size() == 0)
            for (String str : mTabsChoicesAll) {
                mTabsChoicesSelected.add(str);
                tabLayout.addTab(tabLayout.newTab().setText(str));
                Utils.print(TAG, "addForcedTab(" + str + ")");
            }

        //mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager()));
        //mViewPager.setAdapter(mSectionsPagerAdapter);
        mSectionsPagerAdapter.notifyDataSetChanged();
        tabLayout.post(tabLayoutConfig);

        /*String[] mTemp = getResources().getStringArray(R.array.list_sources_names);
        if (!Arrays.equals(mTabsChoicesAll, mTemp)) {
            Utils.print(TAG, "Tabs changed");
            tabLayout.removeAllTabs();
            for (String str : mTabsChoicesAll) {
                if (sp.getBoolean(Utils.createSlug(str), true)) {
                    tabLayout.addTab(tabLayout.newTab().setText(str));
                    Utils.print(TAG, "addTab(" + str + ")");
                }
            }
            mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
            mViewPager.setAdapter(mSectionsPagerAdapter);
        }
        tabLayout.post(tabLayoutConfig);*/
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
    public void onListFragmentInteraction(NewsItem item) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
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
    }
}
