package com.csci150.newsapp.entirenews;


import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.csci150.newsapp.entirenews.utils.RealmController;
import com.csci150.newsapp.entirenews.utils.Utils;

import java.util.List;

import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedFragment extends Fragment implements OnListInteractionListener {
    private final String TAG = "SavedFragment";

    private int mPage = 1;
    private List<NewsItem> mItems;

    private RecyclerView mRecyclerView;
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar toolbar;

    private NewsItemAdapter mAdapter;

    private Realm realm;

    public SavedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SavedFragment.
     */
    public static SavedFragment newInstance() {
        return new SavedFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.print(TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
        mRecyclerView = view.findViewById(R.id.list);
        mCoordinatorLayout = view.findViewById(R.id.coordinator_layout);

        toolbar = view.findViewById(R.id.toolbar);
        setupToolbar(false, true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get realm instance
        this.realm = RealmController.with(getActivity()).getRealm();

        mItems = RealmController.with(getActivity()).getNewsItems();
        //Collections.reverse(mItems);
        mAdapter = new NewsItemAdapter(getActivity(), mItems, realm, getListener());
        mRecyclerView.setAdapter(mAdapter);
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

    private OnListInteractionListener getListener() {
        return this;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_scrolling, menu);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onSave(boolean save, NewsItem news) {

    }

    @Override
    public void onListFragmentInteraction(NewsItem item) {

    }
}
