package com.csci150.newsapp.entirenews;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.csci150.newsapp.entirenews.utils.ApiInterface;
import com.csci150.newsapp.entirenews.utils.ApiPrefs;
import com.csci150.newsapp.entirenews.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListInteractionListener}
 * interface.
 */
public class NewsItemFragment extends Fragment implements OnListInteractionListener {
    private final String TAG = "NewsItemFragment";

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_PARAM_SOURCE = "param-source";

    private ApiPrefs mApiPrefs;
    private Context mContext;
    private int mPage = 1;
    private String mSource;
    private int mColumnCount;
    private List<NewsItem> mItems;
    private boolean connected = true;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout layoutEmpty, layoutNoInternet, layoutError, layoutNoSaved;

    protected NewsItemAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NewsItemFragment() {
    }

    public static NewsItemFragment newInstance(String source) {
        NewsItemFragment fragment = new NewsItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_SOURCE, source);
        fragment.setArguments(args);
        return fragment;
    }

    public static NewsItemFragment newInstance(String source, int columnCount) {
        NewsItemFragment fragment = new NewsItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_PARAM_SOURCE, source);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mContext == null)
            mContext = getActivity().getApplicationContext();
        Utils.print(TAG, "onCreate()");
        mApiPrefs = ApiPrefs.get(mContext);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT, 1);
            mSource = getArguments().getString(ARG_PARAM_SOURCE, "default");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newsitem_list, container, false);
        mRecyclerView = view.findViewById(R.id.list);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        // Set the adapter
        //mRecyclerView.setAdapter(new NewsItemAdapter(getActivity(), DummyContent.ITEMS, mListener));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!Utils.hasNavigationBar(TAG, getResources()))
            mRecyclerView.setPadding(0, 0, 0, 0);

        setupSwipeLayout();
        setupRecyclerView();
        checkConnectivity();
        if (connected)
            getNews(mSource, mPage = 1);
    }

    private void checkConnectivity() {
        Utils.print(TAG, "checkConnectivity()");
        connected = Utils.isInternetConnected(mContext);
        // TODO
        //showConnectedOrDisconnected();
    }

    private void checkConnectivityAndLoad(final boolean isCon) {
        Utils.print(TAG, "checkConnectivityAndLoad(isCon)");
        if (isCon && !connected) {
            // TODO - not working with data connection (test in emulator)
            //getNews();
            // show loader and fetch messages
            mSwipeRefreshLayout.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            getNews(mSource, mPage = 1);
                        }
                    }
            );
        }
        connected = isCon;
        // TODO
        //showConnectedOrDisconnected();
    }

    @Override
    public void onPause() {
        super.onPause();
        Utils.print(TAG, "onPause()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Utils.print(TAG, "onResume()");
        boolean isCon = Utils.isInternetConnected(mContext);
        if (connected != isCon)
            checkConnectivityAndLoad(isCon);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.print(TAG, "onDestroy()");
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Utils.print(TAG, "onAttach(activity)");
        if (Build.VERSION.SDK_INT < 23)
            onAttachToContext(activity);
    }

    @Override
    @TargetApi(23)
    public void onAttach(Context context) {
        super.onAttach(context);
        Utils.print(TAG, "onAttach(context)");
        onAttachToContext(context);
    }

    private void onAttachToContext(Context context) {
        if (context instanceof OnFragmentInteractionListener) {
            mContext = context;
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public ApiInterface getApi() {
        return mApiPrefs.getApi();
    }

    private void setupRecyclerView() {
        if (mColumnCount <= 1)
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        else
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), mColumnCount));
    }

    private void setupSwipeLayout() {
        Utils.print(TAG, "setupSwipeLayout");
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                connected = Utils.isInternetConnected(mContext);
                if (connected) getNews(mSource, mPage = 1);
                else {
                    if (mListener != null) mListener.showSnackBar(R.string.response_fail);
                    //Utils.showSnackbar(mCoordinatorLayout, mContext, getString(R.string.response_fail));
                    if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing())
                        mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    protected OnListInteractionListener getListener() {
        return this;
    }

    private void getNews(final String source, final int page) {
        Utils.print(TAG, "getNews(source: " + source + ")");
        if (page == 1 && mSwipeRefreshLayout != null && !mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(true);
        getApi().getNews(source, page).enqueue(new Callback<List<NewsItem>>() {

            @Override
            public void onResponse(@NonNull Call<List<NewsItem>> call,
                                   @NonNull Response<List<NewsItem>> response) {
                Utils.print(TAG, "onResponse()");
                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing())
                    mSwipeRefreshLayout.setRefreshing(false);
                Utils.print(TAG, "URL: " + response.raw().request().url());
                Utils.print(TAG, "Status Code: " + response.code());
                if (response.isSuccessful() && isAdded() && getActivity() != null) {
                    int size = response.body().size();
                    if (page == 1) {
                        if (size == 0) {
                            // TODO error
                            //if (mType == ITEM_LOVED) showNoLoved(true);
                            //else showEmptyList(true);
                        } else {
                            // TODO
                            //hideStubs();
                            mAdapter = new NewsItemAdapter(getActivity(), response.body(), getListener());
                            mRecyclerView.setAdapter(mAdapter);
                            //Utils.print(TAG, response.body().getItems().toString());
                        }
                    } else {
                        if (size > 0)
                            mAdapter.addItems(response.body());//, mLimit);
                        else {
                            mAdapter.addItems();
                            //Utils.showSnackbar(mCoordinatorLayout, mContext, R.string.response_cross_limit);
                        }
                    }
                } else {
                    Utils.print(TAG, "ServerResponse: " + response.message(), Log.ERROR);
                    // TODO
                    //if (isAdded() && getActivity() != null)
                    //if (response.code() == 401)
                    //    showError(true, R.string.response_login);
                    //else showError(true, R.string.response_error);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<NewsItem>> call, @NonNull Throwable t) {
                Utils.print(TAG, "onFailure()", Log.ERROR);
                Utils.print(TAG, t.toString(), Log.ERROR);
                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing())
                    mSwipeRefreshLayout.setRefreshing(false);
                // TODO
                //if (isAdded() && getActivity() != null)
                //    showError(true, R.string.response_error);
            }
        });
    }

    protected void setSave(final String id) {
        Utils.print(TAG, "setSave(id: " + id + ")");
        getApi().saveNews(id).enqueue(new Callback<NewsItem>() {

            @Override
            public void onResponse(@NonNull Call<NewsItem> call, @NonNull Response<NewsItem> response) {
                Utils.print(TAG, "onResponse()");
                Utils.print(TAG, "URL: " + response.raw().request().url());
                Utils.print(TAG, "Status Code: " + response.code());
            }

            @Override
            public void onFailure(@NonNull Call<NewsItem> call, @NonNull Throwable t) {
                Utils.print(TAG, "onFailure()", Log.ERROR);
                Utils.print(TAG, t.toString(), Log.ERROR);
                if (isAdded() && getActivity() != null)
                    if (mListener != null) mListener.showSnackBar(R.string.response_fail);
                //Utils.showSnackbar(mCoordinatorLayout, mContext, getString(R.string.response_fail));
            }
        });
    }

    @Override
    public void onLoadMore() {
        Utils.print(TAG, "onLoadMore()");
        getNews(mSource, ++mPage);
    }

    @Override
    public void onSave(final boolean save, final String id) {
        Utils.print(TAG, "onSave()");
        if (save) {
            if (mListener != null) mListener.showSnackBar(R.string.response_saved);
            //Utils.showSnackbar(mCoordinatorLayout, mContext, R.string.response_saved);
            setSave(id);
        } else {
            if (mListener != null) mListener.showSnackBar(R.string.response_unsaved);
            //Utils.showSnackbar(mCoordinatorLayout, mContext, R.string.response_unsaved);
        }
    }

    @Override
    public void onListFragmentInteraction(NewsItem item) {
        Utils.print(TAG, "onListFragmentInteraction()");
    }
}
