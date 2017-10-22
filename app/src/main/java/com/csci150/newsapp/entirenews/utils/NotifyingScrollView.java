package com.csci150.newsapp.entirenews.utils;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Created by Shifatul Islam (Denocyte) on 10/22/2017 1:13 AM.
 * A listing app, where you can find everything in one place.
 */

public class NotifyingScrollView extends NestedScrollView {
    private OnScrollChangedListener mOnScrollChangedListener;

    public NotifyingScrollView(Context context) {
        super(context);
    }

    public NotifyingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NotifyingScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        mOnScrollChangedListener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldL, int oldT) {
        super.onScrollChanged(l, t, oldL, oldT);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(this, l, t, oldL, oldT);
        }
    }

    /**
     * The Scrollview scroll position listener
     */
    public interface OnScrollChangedListener {
        /***
         * @param who  the view that perform scroll
         * @param l    Current horizontal scroll origin.
         * @param t    Current vertical scroll origin.
         * @param oldl Previous horizontal scroll origin.
         * @param oldt Previous vertical scroll origin.
         */
        void onScrollChanged(NestedScrollView who, int l, int t, int oldl, int oldt);
    }
}
