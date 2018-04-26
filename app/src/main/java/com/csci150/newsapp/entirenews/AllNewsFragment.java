package com.csci150.newsapp.entirenews;


import android.app.ActionBar;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.csci150.newsapp.entirenews.utils.AnimUtils;
import com.csci150.newsapp.entirenews.utils.ApiPrefs;
import com.csci150.newsapp.entirenews.utils.FontAwareTabLayout;
import com.csci150.newsapp.entirenews.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllNewsFragment extends Fragment {
    private final String TAG = "AllNewsFragment";

    private Toolbar toolbar;
    private ApiPrefs mApiPrefs;
    private Context mContext;

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

    public AllNewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AllNewsFragment.
     */
    public static AllNewsFragment newInstance() {
        return new AllNewsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Utils.print(TAG, "onCreate");
        if (mContext == null)
            mContext = getActivity().getApplicationContext();
        mApiPrefs = ApiPrefs.get(mContext);
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mTabsChoicesAll = getResources().getStringArray(R.array.list_sources_names);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Utils.print(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_all_news, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        mCoordinatorLayout = view.findViewById(R.id.coordinator_layout);

        setupToolbar(false, true);
        animateToolbar();

        tabLayout = view.findViewById(R.id.tabs);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mPagerAdapter = new PagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = view.findViewById(R.id.container_tabs);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new FontAwareTabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new FontAwareTabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        for (String str : mTabsChoicesAll) {
            if (sp.getBoolean(Utils.createSlug(str), true)) {
                tabLayout.addTab(tabLayout.newTab().setText(str));
                //mPagerAdapter.addTabPage(str);
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

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Utils.print(TAG, "onResume");
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
    public void onPause() {
        super.onPause();
        Utils.print(TAG, "onPause");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Utils.print(TAG, "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Utils.print(TAG, "onDetach");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_scrolling, menu);
        if (mApiPrefs.isLoggedIn()) {
            MenuItem item = menu.getItem(0);
            item.setTitle(mApiPrefs.getUserUsername() + " (Logout)");
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_login:
                if (mApiPrefs.isLoggedIn()) {
                    //logout
                    item.setTitle("Log in");
                    mApiPrefs.logout();
                } else {
                    //login
                    intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                break;
            case R.id.action_settings:
                intent = new Intent(mContext, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_about:
                intent = new Intent(mContext, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void setupToolbar(boolean showTitle, boolean homeAsUp) {
        getActivity().setActionBar(toolbar);
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(showTitle);
            actionBar.setDisplayHomeAsUpEnabled(homeAsUp);
            actionBar.setDisplayShowHomeEnabled(homeAsUp);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
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
                .setInterpolator(AnimUtils.getFastOutSlowInInterpolator(mContext));
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
