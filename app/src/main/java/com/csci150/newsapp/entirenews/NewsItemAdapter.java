package com.csci150.newsapp.entirenews;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * specified {@link OnListInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.ViewHolder> {
    private static final String TAG = "NewsItemAdapter";
    private static final int DEFAULT_SIZE = 12;
    private static final int ITEM_NEWS_LOAD = -1;

    // we need to hold on to an activity ref for the shared element transitions :/
    private final Activity host;
    private final List<NewsItem> mItems;
    private final OnListInteractionListener mListener;
    private boolean isLoading, isLoadMoreAvailable;
    private int size;

    NewsItemAdapter(Activity hostActivity, List<NewsItem> items, OnListInteractionListener listener) {
        size = items.size();
        isLoadMoreAvailable = size >= DEFAULT_SIZE;
        host = hostActivity;
        mItems = items;
        mListener = listener;
        isLoading = false;
        //setHasStableIds(true);
        Utils.print(TAG, "ItemAdapter: Construct [size: " + size + "]");
    }

    void addItems(final List<NewsItem> items) { //, final int requestLimit) {
        int len = items.size();
        size += len;
        isLoading = false;
        //removeLoading();
        mItems.addAll(items);
        isLoadMoreAvailable = len >= DEFAULT_SIZE;
        notifyDataSetChanged();
        Utils.print(TAG, "addItems[size: " + len + "]");
    }

    void addItems() {
        isLoading = false;
        //removeLoading();
        isLoadMoreAvailable = false;
        //notifyItemChanged(staticSize - 1);
        Utils.print(TAG, "addItems[]");
        // Force remove load more
        notifyItemChanged(size - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ITEM_NEWS_LOAD:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_newsitem_wide_load, parent, false);
                break;
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_newsitem_wide, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.position = holder.getAdapterPosition();
        holder.mItem = mItems.get(position);
        holder.tvTitle.setText(holder.mItem.getTitle());

        holder.tvTitle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                holder.tvTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                switch (holder.tvTitle.getLineCount()) {
                    case 1:
                        holder.tvSubtitle.setMaxLines(3);
                        holder.tvSubtitle.setText(holder.mItem.getSubtitle());
                        break;
                    case 2:
                        holder.tvSubtitle.setMaxLines(2);
                        holder.tvSubtitle.setText(holder.mItem.getSubtitle());
                        break;
                    case 3:
                        holder.tvSubtitle.setMaxLines(1);
                        holder.tvSubtitle.setText(holder.mItem.getSubtitle());
                        break;
                    default:
                        holder.tvSubtitle.setMaxLines(2);
                        holder.tvSubtitle.setText(null);
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

        if (holder.mItem.getSaves() > 0) {
            holder.tvSaves.setVisibility(View.VISIBLE);
            String saves = host.getResources().getQuantityString(R.plurals.saves,
                    holder.mItem.getSaves(), holder.mItem.getSaves());
            holder.tvSaves.setText(saves);
        } else {
            holder.tvSaves.setVisibility(View.GONE);
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

                    mItems.get(holder.position).setSaves(mItems.get(holder.position).getSaves() - 1);
                    if (holder.mItem.getSaves() > 0) {
                        holder.tvSaves.setVisibility(View.VISIBLE);
                        String saves = host.getResources().getQuantityString(R.plurals.saves,
                                holder.mItem.getSaves(), holder.mItem.getSaves());
                        holder.tvSaves.setText(saves);
                    } else {
                        holder.tvSaves.setVisibility(View.GONE);
                    }
                } else {
                    holder.ivSave.setImageResource(R.drawable.ic_star_solid_24dp);

                    mItems.get(holder.position).setSaves(mItems.get(holder.position).getSaves() + 1);
                    holder.tvSaves.setVisibility(View.VISIBLE);
                    String saves = host.getResources().getQuantityString(R.plurals.saves,
                            holder.mItem.getSaves(), holder.mItem.getSaves());
                    holder.tvSaves.setText(saves);

                }
                int pos = holder.position;
                boolean saved = !mItems.get(pos).isSaved();
                mItems.get(pos).setSaved(saved);
                if (mListener != null)
                    mListener.onSave(saved, holder.mItem);
                //holder.mItem.setSaved(saved);
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

               // intent.putExtra(NewsActivity.EXTRA_NEWS_ITEM,
                //        getItem(holder.getAdapterPosition()));

                //setGridItemContentTransitions(holder.ivCover);
                ActivityOptions options =
                        ActivityOptions.makeSceneTransitionAnimation(host,
                                Pair.create((View) holder.ivCover, host.getString(R.string.transition_news)),
                                Pair.create(holder.vBody, host.getString(R.string.transition_news_background)));
                host.startActivityForResult(intent, 100, options.toBundle());

                mItems.get(holder.position).setViews(mItems.get(holder.position).getViews() + 1);
                //holder.mItem.addView();
                holder.tvViews.setVisibility(View.VISIBLE);
                String views = host.getResources().getQuantityString(R.plurals.views,
                        holder.mItem.getViews(), holder.mItem.getViews());
                holder.tvViews.setText(views);
            }
        });

        // Load more News
        if (position >= getStaticSize() - 1 && !isLoading && isLoadMoreAvailable) {
            Utils.print(TAG, "Load more data...", Log.INFO);
            isLoading = true;
            if (mListener != null)
                mListener.onLoadMore();
        }

    }

    private int getStaticSize() {
        if (size > 0) return size;
        else size = getItemCount();
        return size;
    }

    private NewsItem getItem(final int position) {
        if (position < 0 || position >= mItems.size()) return null;
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadMoreAvailable && position == size - 1) return ITEM_NEWS_LOAD;
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        private final TextView tvTitle, tvSubtitle, tvViews, tvSaves, tvDate;
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
            tvSaves = view.findViewById(R.id.tv_saves);
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