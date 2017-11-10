package com.csci150.newsapp.entirenews;

public interface OnListInteractionListener {

    void onLoadMore();

    void onSave(final boolean save, final String id);

    void onListFragmentInteraction(NewsItem item);
}
