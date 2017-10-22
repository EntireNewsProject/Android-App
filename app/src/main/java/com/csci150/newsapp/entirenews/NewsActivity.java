package com.csci150.newsapp.entirenews;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.csci150.newsapp.entirenews.utils.ElasticDragDismissFrameLayout;
import com.csci150.newsapp.entirenews.utils.FourThreeImageView;

import okhttp3.HttpUrl;

public class NewsActivity extends BaseActivity implements View.OnClickListener {
    public final static String RESULT_EXTRA_NEWS_ID = "RESULT_EXTRA_NEWS_ID";
    public static final String EXTRA_NEWS_ITEM = "EXTRA_NEWS_ITEM";

    FourThreeImageView ivCover;
    FloatingActionButton fab;
    ImageButton ibBack;
    RecyclerView mRecyclerView;
    ElasticDragDismissFrameLayout draggableFrame;
    NewsItem newsItem;
    private ElasticDragDismissFrameLayout.SystemChromeFader chromeFader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_demo2);
        //toolbar = findViewById(R.id.toolbar);
        //setupToolbar(false, true);
        //fab = findViewById(R.id.fab);
        //fab.setOnClickListener(this);
        ivCover = findViewById(R.id.iv_cover);
        ibBack = findViewById(R.id.back);
        draggableFrame = findViewById(R.id.draggable_frame);

        chromeFader = new ElasticDragDismissFrameLayout.SystemChromeFader(this) {
            @Override
            public void onDragDismissed() {
                setResultAndFinish();
            }
        };

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResultAndFinish();
            }
        });

        final Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_NEWS_ITEM)) {
            newsItem = intent.getParcelableExtra(EXTRA_NEWS_ITEM);
            bindNews(true);
        } else if (intent.getData() != null && intent.getDataString() != null) {
            final HttpUrl url = HttpUrl.parse(intent.getDataString());

            // do stuff here
            bindNews(false);
        }
    }

    void bindNews(final boolean postponeEnterTransition) {
        final Resources res = getResources();

        Glide.with(this)
                .load(newsItem.cover)
                .apply(new RequestOptions().centerCrop().error(R.drawable.sample))
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
