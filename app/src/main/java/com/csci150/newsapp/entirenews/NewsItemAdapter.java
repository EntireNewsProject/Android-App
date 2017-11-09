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
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.csci150.newsapp.entirenews.utils.Utils;

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
    private boolean isLoading, isLoadMoreAvailable;

    NewsItemAdapter(Activity hostActivity, List<NewsItem> items, OnListFragmentInteractionListener listener) {
        int size = items.size();
        //isLoadMoreAvailable = size >= requestLimit;
        host = hostActivity;
        mItems = items;
        mListener = listener;
        isLoading = false;
        //setHasStableIds(true);
        Utils.print(TAG, "ItemAdapter: Construct [size:" + size + "]");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_newsitem_wide, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.position = position;
        holder.mItem = mItems.get(position);
        holder.tvTitle.setText(holder.mItem.getTitle());

        holder.tvTitle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                holder.tvTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (holder.tvTitle.getLineCount() == 3) {
                    holder.tvSubtitle.setMaxLines(1);
                    holder.tvSubtitle.setText(holder.mItem.getSubtitle());
                } else if (holder.tvTitle.getLineCount() < 3) {
                    holder.tvSubtitle.setMaxLines(2);
                    holder.tvSubtitle.setText(holder.mItem.getSubtitle());
                } else {
                    holder.tvSubtitle.setMaxLines(2);
                }
            }
        });

        if (holder.mItem.getViews() > 0) {
            holder.tvViews.setVisibility(View.VISIBLE);
            String views = host.getResources().getQuantityString(R.plurals.views,
                    holder.mItem.getViews(), holder.mItem.getViews());
            holder.tvViews.setText(views);
        } else {
            holder.tvViews.setVisibility(View.GONE);
        }
        String date = Utils.getDateAgo(host, holder.mItem.getCreatedAt());
        holder.tvDate.setText(date);

        Glide.with(host)
                .load(holder.mItem.getCover())
                // TODO change err
                .apply(new RequestOptions().centerCrop().error(R.drawable.sample))
                .into(holder.ivCover);

        holder.ivCover.setBackground(new ColorDrawable(Color.DKGRAY));
        holder.ivCover.setTransitionName(holder.mItem.getTitle());

        if (holder.mItem.isSaved())
            holder.ivSave.setImageResource(R.drawable.ic_star_solid_24dp);
        else
            holder.ivSave.setImageResource(R.drawable.ic_star_24dp);

        holder.ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.mItem.isSaved()) {
                    holder.ivSave.setImageResource(R.drawable.ic_star_24dp);
                } else {
                    holder.ivSave.setImageResource(R.drawable.ic_star_solid_24dp);
                }
                int pos = holder.position;
                boolean saved = !mItems.get(pos).isSaved();
                mItems.get(pos).setSaved(saved);
                holder.mItem.setSaved(saved);
            }
        });
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
                mItems.get(holder.position).addView();
                //holder.mItem.addView();
                holder.tvViews.setVisibility(View.VISIBLE);
                String views = host.getResources().getQuantityString(R.plurals.views,
                        holder.mItem.getViews(), holder.mItem.getViews());
                holder.tvViews.setText(views);
            }
        });
    }

    private NewsItem getItem(final int position) {
        if (position < 0 || position >= mItems.size()) return null;
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addItems(final List<NewsItem> items) { //, final int requestLimit) {
        int size = items.size();
        isLoading = false;
        //removeLoading();
        mItems.addAll(items);
        //isLoadMoreAvailable = size >= requestLimit;
        notifyDataSetChanged();
        Utils.print(TAG, "addItems[size:" + size + "]");
    }

    public void addItems() {
        isLoading = false;
        //removeLoading();
        isLoadMoreAvailable = false;
        //notifyItemChanged(staticSize - 1);
        Utils.print(TAG, "addItems[]");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        private final TextView tvTitle, tvSubtitle, tvViews, tvDate;
        private final ImageView ivCover;
        private final ImageButton ivSave;
        private final View vBody;
        private NewsItem mItem;
        private int position;

        ViewHolder(View view) {
            super(view);
            mView = view;
            vBody = view.findViewById(R.id.v_body);
            tvTitle = view.findViewById(R.id.tv_title);
            tvSubtitle = view.findViewById(R.id.tv_subtitle);
            tvViews = view.findViewById(R.id.tv_views);
            tvDate = view.findViewById(R.id.tv_date);
            ivCover = view.findViewById(R.id.iv_cover);
            ivSave = view.findViewById(R.id.ib_save);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTitle.getText() + "'";
        }
    }
}