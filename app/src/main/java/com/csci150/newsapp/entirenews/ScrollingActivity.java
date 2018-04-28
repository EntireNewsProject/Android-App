package com.csci150.newsapp.entirenews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.csci150.newsapp.entirenews.utils.ApiInterface;
import com.csci150.newsapp.entirenews.utils.ApiPrefs;
import com.csci150.newsapp.entirenews.utils.ElasticDragDismissFrameLayout;
import com.csci150.newsapp.entirenews.utils.FourThreeImageView;
import com.csci150.newsapp.entirenews.utils.NotifyingScrollView;
import com.csci150.newsapp.entirenews.utils.RealmController;
import com.csci150.newsapp.entirenews.utils.Utils;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrollingActivity extends Activity implements
        NotifyingScrollView.OnScrollChangedListener,
        ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "ScrollingActivity";
    private static final long CLICK_TIME_INTERVAL = 900;
    private long mLastClickTime = System.currentTimeMillis();

    public final static String RESULT_EXTRA_NEWS_ID = "RESULT_EXTRA_NEWS_ID";
    public final static String RESULT_EXTRA_NEWS_SAVED = "RESULT_EXTRA_NEWS_SAVED";
    public static final String EXTRA_NEWS_ITEM = "EXTRA_NEWS_ITEM";

    private ApiPrefs mApiPrefs;
    private static final float mVerticalParallaxSpeed = 0.3f;
    private int fabOffset;
    private int mCoverImageHeight;
    private NewsItem newsItem;
    private FloatingActionButton fab;

    private FourThreeImageView ivCover;
    private ConstraintLayout mLayout;
    private ImageButton ibShare;
    //private FabToggle fab2;
    private ElasticDragDismissFrameLayout draggableFrame;
    private ElasticDragDismissFrameLayout.SystemChromeFader chromeFader;
    private TextView tvTitle, tvArticle, tvViews, tvSaves, tvDate, tvSource;
    private Map<String, String> mSources = new HashMap<>();

    private Realm realm;
    private boolean defaultSaved;
    private boolean isRemoved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.print(TAG, "onCreate");
        setContentView(R.layout.activity_scrolling);
        mApiPrefs = ApiPrefs.get(getApplicationContext());

        //get realm instance
        this.realm = RealmController.with(this).getRealm();

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        final CoordinatorLayout mCoordinatorLayout = findViewById(R.id.coordinator_layout);

        if (!Utils.hasNavigationBar(TAG, getResources()))
            findViewById(R.id.view).setVisibility(View.GONE);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // to prevent spamming the button
                long now = System.currentTimeMillis();
                if (now - mLastClickTime < CLICK_TIME_INTERVAL)
                    return;
                mLastClickTime = now;

                if (newsItem.isSaved()) {
                    fab.setImageResource(R.drawable.ic_star_white_24dp);
                    Utils.showSnackbar(mCoordinatorLayout, getApplicationContext(),
                            getString(R.string.response_unsaved));
                    try {
                        realm.beginTransaction();
                        newsItem.setSaved(!newsItem.isSaved());
                        realm.commitTransaction();
                    } catch (Exception ignored) {
                        realm.commitTransaction();
                    }
                    isRemoved = true;
                } else {
                    fab.setImageResource(R.drawable.ic_star_white_solid_24dp);
                    Utils.showSnackbar(mCoordinatorLayout, getApplicationContext(),
                            getString(R.string.response_saved));
                    newsItem.setSaved(!newsItem.isSaved());
                    try {
                        realm.beginTransaction();
                        realm.copyToRealm(newsItem);
                        realm.commitTransaction();
                    } catch (Exception ignored) {
                        realm.commitTransaction();
                    }
                    isRemoved = false;
                }
            }
        });

        ibShare = findViewById(R.id.ib_share);
        ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.share(TAG, getApplicationContext(), getString(R.string.share),
                        newsItem.getTitle(), newsItem.getUrl());
            }
        });

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //fab2 = findViewById(R.id.fab2);
        ivCover = findViewById(R.id.iv_cover);
        tvTitle = findViewById(R.id.tv_title);
        tvArticle = findViewById(R.id.tv_article);
        tvViews = findViewById(R.id.tv_views);
        tvSaves = findViewById(R.id.tv_saves);
        tvDate = findViewById(R.id.tv_date);
        tvSource = findViewById(R.id.tv_source);
        draggableFrame = findViewById(R.id.draggable_frame);
        draggableFrame = findViewById(R.id.draggable_frame);
        mLayout = findViewById(R.id.constraint_layout);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResultAndFinish();
            }
        });
        chromeFader = new ElasticDragDismissFrameLayout.SystemChromeFader(this) {
            @Override
            public void onDragDismissed() {
                setResultAndFinish();
            }
        };

        ((NotifyingScrollView) findViewById(R.id.scroll_view)).setOnScrollChangedListener(this);
        ivCover.getViewTreeObserver().addOnGlobalLayoutListener(this);

        final Intent intent = getIntent();

        if (intent.hasExtra(RESULT_EXTRA_NEWS_ID) && intent.getExtras() != null) {
            String id = intent.getExtras().getString(RESULT_EXTRA_NEWS_ID);

            newsItem = RealmController.with(this).getNewsItem(id);
            bindNews(true, true);
            //Utils.print(TAG, "AAA " + id);
        } else if (intent.hasExtra(EXTRA_NEWS_ITEM)) {
            newsItem = Parcels.unwrap(intent.getParcelableExtra(EXTRA_NEWS_ITEM));
            bindNews(true, false);
            //Utils.print(TAG, "BBB " + newsItem.getTitle());
        } else {
            Utils.print(TAG, "No news to show");
        }
    }

    public ApiInterface getApi() {
        return mApiPrefs.getApi();
    }

    void bindNews(final boolean postponeEnterTransition, final boolean isLocal) {
        //final Resources res = getResources();
        createMap();
        Glide.with(this)
                .load(newsItem.getCover())
                .apply(new RequestOptions().centerCrop().error(R.drawable.error))
                .into(ivCover);

        if (postponeEnterTransition) postponeEnterTransition();
        ivCover.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        ivCover.getViewTreeObserver().removeOnPreDrawListener(this);
                        calculateFabPosition();
                        if (postponeEnterTransition) startPostponedEnterTransition();
                        return true;
                    }
                });

        tvTitle.setText(newsItem.getTitle());

        String date = Utils.getDateAgo(getApplicationContext(), newsItem.getCreatedAt());
        tvDate.setText(date);
        String source = mSources.get(newsItem.getSource());
        if (source != null)
            tvSource.setText(source);
        else
            tvSource.setText(R.string.news);

        defaultSaved = newsItem.isSaved();
        if (defaultSaved)
            fab.setImageResource(R.drawable.ic_star_white_solid_24dp);
        else
            fab.setImageResource(R.drawable.ic_star_white_24dp);

        String views;
        if (isLocal)
            views = getResources()
                    .getQuantityString(R.plurals.views, (newsItem.getViews()),
                            (newsItem.getViews()));
        else
            views = getResources()
                    .getQuantityString(R.plurals.views, (newsItem.getViews() + 1),
                            (newsItem.getViews()) + 1);
        tvViews.setText(views);

        if (newsItem.getSaves() > 0) {
            String saves = getResources()
                    .getQuantityString(R.plurals.saves, newsItem.getSaves(), newsItem.getSaves());
            tvSaves.setText(saves);
            tvSaves.setVisibility(View.VISIBLE);
        } else {
            tvSaves.setVisibility(View.GONE);
        }

        if (isLocal)
            bindNews(newsItem, isLocal);
        else
            getNews(newsItem.get_id());
    }

    void bindNews(final NewsItem item, final boolean isLocal) {
        newsItem = item;
        if (!isLocal)
            newsItem.setSaved(defaultSaved);
        if (TextUtils.isEmpty(newsItem.getSummary()) || newsItem.getSummary().length() <= newsItem.getTitle().length())
            tvArticle.setText(newsItem.getArticle());
        else
            tvArticle.setText(newsItem.getSummary());
    }

    private void getNews(final String id) {
        Utils.print(TAG, "getNews(id: " + id + ")");
        getApi().getNews(mApiPrefs.getAccessToken(), id).enqueue(new Callback<NewsItem>() {

            @Override
            public void onResponse(@NonNull Call<NewsItem> call, @NonNull Response<NewsItem> response) {
                Utils.print(TAG, "onResponse()");
                Utils.print(TAG, "URL: " + response.raw().request().url());
                Utils.print(TAG, "Status Code: " + response.code());
                if (response.isSuccessful()) {
                    bindNews(response.body(), false);
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
            public void onFailure(@NonNull Call<NewsItem> call, @NonNull Throwable t) {
                Utils.print(TAG, "onFailure()", Log.ERROR);
                Utils.print(TAG, t.toString(), Log.ERROR);
                // TODO
                //if (isAdded() && getActivity() != null)
                //    showError(true, R.string.response_error);
            }
        });
    }

    void calculateFabPosition() {
        // calculate 'natural' position i.e. with full height image. Store it for use when scrolling
        //fabOffset = ivCover.getHeight() - (fab2.getHeight() / 2);
        //fab2.setOffset(fabOffset);
        // calculate min position i.e. pinned to the collapsed image when scrolled
        //fab2.setMinOffset(ivCover.getMinimumHeight() - (fab2.getHeight() / 2));
    }

    void setResultAndFinish() {
        //content.setBackgroundColor(getResources().getColor(R.color.transparent));
        if (isRemoved)
            RealmController.with(getApplication()).deleteNewsItems(newsItem.get_id());
        //} else {
        final Intent resultData = new Intent();
        if (newsItem.isSaved() != defaultSaved) {
            resultData.putExtra(RESULT_EXTRA_NEWS_ID, newsItem.get_id());
            resultData.putExtra(RESULT_EXTRA_NEWS_SAVED, newsItem.isSaved());
        }
        setResult(RESULT_OK, resultData);
        // }
        finishAfterTransition();
    }

    private void createMap() {
        String[] mTabsChoices = getResources().getStringArray(R.array.list_sources_names);
        for (String str : mTabsChoices) {
            mSources.put(Utils.createSlug(str), "- by " + str);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.print(TAG, "onResume");
        draggableFrame.addListener(chromeFader);
    }

    @Override
    protected void onPause() {
        draggableFrame.removeListener(chromeFader);
        super.onPause();
        Utils.print(TAG, "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.print(TAG, "onDestroy");
    }

    @Override
    public void onBackPressed() {
        setResultAndFinish();
        Utils.print(TAG, "onBackPressed");
    }

    @Override
    public boolean onNavigateUp() {
        setResultAndFinish();
        Utils.print(TAG, "onNavigateUp");
        return true;
    }

    @Override
    public void onScrollChanged(NestedScrollView who, int l, int t, int oldL, int oldT) {
        if (t < mCoverImageHeight) {
            fab.show();
            //final float ratio = (float) Math.min(Math.max(t, 0), mCoverImageHeight) / mCoverImageHeight;
            //ivCover.setTranslationY(ratio * mCoverImageHeight * mVerticalParallaxSpeed);
        } else
            fab.hide();
        //final int scrollY = mLayout.getTop();
        //fab2.setOffset(fabOffset + scrollY);
    }

    @Override
    public void onGlobalLayout() {
        mCoverImageHeight = ivCover.getHeight() / 3 * 2;
        ViewTreeObserver obs = ivCover.getViewTreeObserver();
        obs.removeOnGlobalLayoutListener(this);
    }
}
