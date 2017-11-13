package com.csci150.newsapp.entirenews.utils;

/**
 * Created by Shifatul Islam (Denocyte) on 11/11/2017 4:23 PM.
 * A listing app, where you can find everything in one place.
 */

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csci150.newsapp.entirenews.R;

import uk.co.chrisjenx.calligraphy.CalligraphyUtils;

public final class FontAwareTabLayout extends TabLayout {

    private String fontPath;

    public FontAwareTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        fontPath = pullFontPathFromView(context, attrs, new int[]{R.attr.fontPath});
    }

    /**
     * Tries to pull the Custom Attribute directly from the TextView.
     *
     * @param context     Activity Context
     * @param attrs       View Attributes
     * @param attributeId if -1 returns null.
     * @return null if attribute is not defined or added to View
     */
    static String pullFontPathFromView(Context context, AttributeSet attrs, int[] attributeId) {
        if (attributeId == null || attrs == null) return null;

        final String attributeName;
        try {
            attributeName = context.getResources().getResourceEntryName(attributeId[0]);
        } catch (Resources.NotFoundException e) {
            // invalid attribute ID
            return null;
        }

        final int stringResourceId = attrs.getAttributeResourceValue(null, attributeName, -1);
        return stringResourceId > 0 ? context.getString(stringResourceId)
                : attrs.getAttributeValue(null, attributeName);
    }

    @Override
    public void addTab(@NonNull Tab tab, int position, boolean setSelected) {
        super.addTab(tab, position, setSelected);

        ViewGroup mainView = (ViewGroup) getChildAt(0);
        ViewGroup tabView = (ViewGroup) mainView.getChildAt(tab.getPosition());
        int tabChildCount = tabView.getChildCount();
        for (int i = 0; i < tabChildCount; i++) {
            View tabViewChild = tabView.getChildAt(i);
            if (tabViewChild instanceof TextView) {
                CalligraphyUtils.applyFontToTextView(getContext(), (TextView) tabViewChild, fontPath);
            }
        }
    }
}