package com.csci150.newsapp.entirenews.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.csci150.newsapp.entirenews.R;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 * Created by Shifatul Islam (Denocyte) on 10/13/2017 9:29 PM.
 * A news app, where you can find everything in one place.
 */

public class Utils {
    public static void print(String title, String message, int type) {
        switch (type) {
            case Log.DEBUG:
                Log.d("EntireNews (" + title + ")", message);
                break;
            case Log.ERROR:
                Log.e("EntireNews (" + title + ")", message);
                break;
            case Log.INFO:
                Log.i("EntireNews (" + title + ")", message);
                break;
            case Log.VERBOSE:
                Log.v("EntireNews (" + title + ")", message);
                break;
            case Log.WARN:
                Log.w("EntireNews (" + title + ")", message);
                break;
            default:
                Log.d("EntireNews (" + title + ")", message);
        }
    }

    public static void print(String title, String message) {
        print(title, message, Log.DEBUG);
    }

    /**
     * Determine if the navigation bar will be on the bottom of the screen, based on logic in
     * PhoneWindowManager.
     */
    public static boolean isNavBarOnBottom(@NonNull Context context) {
        final Resources res = context.getResources();
        final Configuration cfg = context.getResources().getConfiguration();
        final DisplayMetrics dm = res.getDisplayMetrics();
        boolean canMove = (dm.widthPixels != dm.heightPixels &&
                cfg.smallestScreenWidthDp < 600);
        return (!canMove || dm.widthPixels < dm.heightPixels);
    }

    /**
     * Set the alpha component of {@code color} to be {@code alpha}.
     */
    public static @CheckResult
    @ColorInt
    int modifyAlpha(@ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }

    /**
     * Set the alpha component of {@code color} to be {@code alpha}.
     */
    public static @CheckResult
    @ColorInt
    int modifyAlpha(@ColorInt int color, @FloatRange(from = 0f, to = 1f) float alpha) {
        return modifyAlpha(color, (int) (255f * alpha));
    }

    public static String getDateAgo(final Context context, final String isoDate) {
        try {
            DateTime date = new DateTime(isoDate);
            DateTime now = new DateTime();
            Period period = new Period(date, now);
            PeriodFormatterBuilder builder = new PeriodFormatterBuilder();
            if (period.getYears() == 1)
                builder.appendYears().appendSuffix(" " + context.getString(R.string.ago_year));
            else if (period.getYears() > 1)
                builder.appendYears().appendSuffix(" " + context.getString(R.string.ago_years));
            else if (period.getMonths() == 1)
                builder.appendMonths().appendSuffix(" " + context.getString(R.string.ago_month));
            else if (period.getMonths() > 1)
                builder.appendMonths().appendSuffix(" " + context.getString(R.string.ago_months));
            else if (period.getWeeks() == 1)
                return context.getString(R.string.ago_week);
            else if (period.getWeeks() > 1)
                builder.appendWeeks().appendSuffix(" " + context.getString(R.string.ago_weeks));
            else if (period.getDays() == 1)
                return context.getString(R.string.ago_day);
            else if (period.getDays() > 1)
                builder.appendDays().appendSuffix(" " + context.getString(R.string.ago_days));
            else if (period.getHours() == 1)
                builder.appendHours().appendSuffix(" " + context.getString(R.string.ago_hour));
            else if (period.getHours() > 1)
                builder.appendHours().appendSuffix(" " + context.getString(R.string.ago_hours));
            else if (period.getMinutes() == 1)
                builder.appendMinutes().appendSuffix(" " + context.getString(R.string.ago_minute));
            else if (period.getMinutes() > 1)
                builder.appendMinutes().appendSuffix(" " + context.getString(R.string.ago_minutes));
            else if (period.getSeconds() > 29)
                builder.appendSeconds().appendSuffix(" " + context.getString(R.string.ago_seconds));
            else return context.getString(R.string.ago_now);
            PeriodFormatter formatter = builder.printZeroNever().toFormatter();
            return formatter.print(period);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
