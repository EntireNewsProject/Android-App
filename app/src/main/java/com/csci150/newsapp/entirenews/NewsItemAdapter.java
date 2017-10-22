package com.csci150.newsapp.entirenews;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link NewsItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.ViewHolder> {
    private static final String TAG = "NewsItemAdapter";

    // we need to hold on to an activity ref for the shared element transitions :/
    private final Activity host;
    private final List<NewsItem> mItems;
    private final OnListFragmentInteractionListener mListener;

    NewsItemAdapter(Activity hostActivity, List<NewsItem> items, OnListFragmentInteractionListener listener) {
        host = hostActivity;
        mItems = items;
        mListener = listener;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_newsitem_wide, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mItems.get(position);
        holder.tvTitle.setText(holder.mItem.title);
        holder.tvSubtitle.setText(holder.mItem.subtitle);
        holder.tvViews.setText(String.valueOf(holder.mItem.views));
        holder.tvDate.setText(holder.mItem.date);

        Glide.with(host)
                .load(holder.mItem.cover)
                .apply(new RequestOptions().centerCrop().error(R.drawable.sample))
                .into(holder.ivCover);

        holder.ivCover.setBackground(new ColorDrawable(Color.DKGRAY));
        holder.ivCover.setTransitionName(holder.mItem.title);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
                Intent intent = new Intent();
                intent.setClass(host, ScrollingActivity.class);
                intent.putExtra(NewsActivity.EXTRA_NEWS_ITEM,
                        getItem(holder.getAdapterPosition()));
                //setGridItemContentTransitions(holder.ivCover);
                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation(host,
                                Pair.create((View) holder.ivCover, host.getString(R.string.transition_news)),
                                Pair.create(holder.vBody, host.getString(R.string.transition_news_background)));
                host.startActivityForResult(intent, 100, options.toBundle());

            }
        });
    }

    private NewsItem getItem(int position) {
        if (position < 0 || position >= mItems.size()) return null;
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        private final TextView tvTitle, tvSubtitle, tvViews, tvDate;
        private final ImageView ivCover;
        private final View vBody;
        NewsItem mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            vBody = view.findViewById(R.id.v_body);
            tvTitle = view.findViewById(R.id.tv_title);
            tvSubtitle = view.findViewById(R.id.tv_subtitle);
            tvViews = view.findViewById(R.id.tv_views);
            tvDate = view.findViewById(R.id.tv_date);
            ivCover = view.findViewById(R.id.iv_cover);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTitle.getText() + "'";
        }
    }
}