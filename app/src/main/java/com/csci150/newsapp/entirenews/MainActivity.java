package com.csci150.newsapp.entirenews;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

public class MainActivity extends BaseActivity implements OnListFragmentInteractionListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_abc));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_bbc));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_bloomberg));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_businessinsider));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_buzzfeed));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_cnn));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_cnbc));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_hacker));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_reuters));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_techcrunch));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_nytimes));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_verge));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_time));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_text_today));

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.post(tabLayoutConfig);
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
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position) {
                case 2:
                    return NewsItemFragment.newInstance(2);
                default:
                    return NewsItemFragment.newInstance(1);

            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
