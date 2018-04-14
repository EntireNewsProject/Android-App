package com.csci150.newsapp.entirenews;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.csci150.newsapp.entirenews.utils.ElasticDragDismissFrameLayout;
import com.csci150.newsapp.entirenews.utils.FourThreeImageView;
import com.csci150.newsapp.entirenews.utils.Utils;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;

public class NewsActivity extends BaseActivity implements View.OnClickListener {
    public final static String RESULT_EXTRA_NEWS_ID = "RESULT_EXTRA_NEWS_ID";
    public static final String EXTRA_NEWS_ITEM = "EXTRA_NEWS_ITEM";

    private FourThreeImageView ivCover;
    private FloatingActionButton fab;
    //private RecyclerView mRecyclerView;
    private ElasticDragDismissFrameLayout draggableFrame;
    private NewsItem newsItem;
    private ElasticDragDismissFrameLayout.SystemChromeFader chromeFader;
    private TextView tvTitle, tvArticle, tvViews, tvDate, tvSource;
    private Map<String, String> mSources = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        //toolbar = findViewById(R.id.toolbar);
        //setupToolbar(false, true);
        //fab = findViewById(R.id.fab);
        //fab.setOnClickListener(this);
        ivCover = findViewById(R.id.iv_cover);
        tvTitle = findViewById(R.id.tv_title);
        tvArticle = findViewById(R.id.tv_article);
        tvViews = findViewById(R.id.tv_views);
        tvDate = findViewById(R.id.tv_date);
        tvSource = findViewById(R.id.tv_source);
        draggableFrame = findViewById(R.id.draggable_frame);
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

        final Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_NEWS_ITEM)) {
            newsItem = Parcels.unwrap(intent.getParcelableExtra(EXTRA_NEWS_ITEM));
            bindNews(true);
        } else if (intent.getData() != null && intent.getDataString() != null) {
            final HttpUrl url = HttpUrl.parse(intent.getDataString());

            // do stuff here
            bindNews(false);
        }
    }

    void bindNews(final boolean postponeEnterTransition) {
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
                        if (postponeEnterTransition) startPostponedEnterTransition();
                        return true;
                    }
                });

        tvTitle.setText(newsItem.getTitle());
        tvArticle.setText(newsItem.getSubtitle());
        if (newsItem.getViews() > 0) {
            tvViews.setVisibility(View.VISIBLE);
            String views = getResources()
                    .getQuantityString(R.plurals.views, newsItem.getViews(), newsItem.getViews());
            tvViews.setText(views);
        } else {
            tvViews.setVisibility(View.GONE);
        }
        String date = Utils.getDateAgo(getApplicationContext(), newsItem.getCreatedAt());
        tvDate.setText(date);
        String source = mSources.get(newsItem.getSource());
        if (source != null)
            tvSource.setText(source);
        else
            tvSource.setText(R.string.news);
    }

    private void createMap() {
        String[] mTabsChoices = getResources().getStringArray(R.array.list_sources_names);
        for (String str : mTabsChoices)
            mSources.put(Utils.createSlug(str), "- by " + str);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        }
    }

    void setResultAndFinish() {
        final Intent resultData = new Intent();
        resultData.putExtra(RESULT_EXTRA_NEWS_ID, "gfhjfgjftghjdtuhrtedy"); //newsItem.id);
        setResult(RESULT_OK, resultData);
        finishAfterTransition();
    }

    @Override
    protected void onResume() {
        super.onResume();
        draggableFrame.addListener(chromeFader);
    }

    @Override
    protected void onPause() {
        draggableFrame.removeListener(chromeFader);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        setResultAndFinish();
    }

    @Override
    public boolean onNavigateUp() {
        setResultAndFinish();
        return true;
    }
}
