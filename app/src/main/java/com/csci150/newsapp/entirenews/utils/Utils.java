package com.csci150.newsapp.entirenews.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.csci150.newsapp.entirenews.R;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    public static void print(final String title, final int message) {
        print(title, String.valueOf(message), Log.DEBUG);
    }

    public static void print(final String title, final String message) {
        print(title, message, Log.DEBUG);
    }

    public static void showSnackbar(final CoordinatorLayout layout, final Context context, final String message) {
        Snackbar snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.buttonSnackbar));
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.bgSnackbar));
        ((TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static void showSnackbar(final CoordinatorLayout layout, final Context context, final String message, final boolean isError) {
        Snackbar snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.buttonSnackbar));
        View snackbarView = snackbar.getView();
        if (isError)
            snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.bgSnackbarErr));
        else
            snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.bgSnackbar));
        ((TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static void showSnackbar(final CoordinatorLayout layout, final Context context, final int message) {
        Snackbar snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.buttonSnackbar));
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.bgSnackbar));
        ((TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static void showSnackbar(final CoordinatorLayout layout, final Context context, final int message, final boolean isError) {
        Snackbar snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.buttonSnackbar));
        View snackbarView = snackbar.getView();
        if (isError)
            snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.bgSnackbarErr));
        else
            snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.bgSnackbar));
        ((TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static void showSnackbar(final CoordinatorLayout layout, final Context context, final String message, final int action) {
        Snackbar snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_SHORT);
        snackbar.setAction(action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).setActionTextColor(ContextCompat.getColor(context, R.color.buttonSnackbar));
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.bgSnackbar));
        ((TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static void showSnackbar(final CoordinatorLayout layout, final Context context, final int message, final int action) {
        Snackbar snackbar = Snackbar.make(layout, message, Snackbar.LENGTH_SHORT);
        snackbar.setAction(action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).setActionTextColor(ContextCompat.getColor(context, R.color.buttonSnackbar));
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.bgSnackbar));
        ((TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static String createSlug(final String slug) {
        return slug.replaceAll("[^\\w\\s]", "").trim().toLowerCase().replaceAll("\\W+", "-");
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
        //print("HELLO",isoDate);
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

    // check device connected to or not internet
    public static boolean isInternetConnected(final Context context) {
        final NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private static boolean hasNavigationBarBackup(final Resources resources) {
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        return id > 0 && resources.getBoolean(id);
    }

    @SuppressLint("PrivateApi")
    public static Boolean hasNavigationBar(final String TAG, final Resources resources) {
        try {
            Class<?> serviceManager = Class.forName("android.os.ServiceManager");
            IBinder serviceBinder = (IBinder) serviceManager.getMethod("getService", String.class).invoke(serviceManager, "window");
            Class<?> stub = Class.forName("android.view.IWindowManager$Stub");
            Object windowManagerService = stub.getMethod("asInterface", IBinder.class).invoke(stub, serviceBinder);
            Method hasNavigationBar = windowManagerService.getClass().getMethod("hasNavigationBar");
            return (boolean) hasNavigationBar.invoke(windowManagerService);
        } catch (ClassNotFoundException | ClassCastException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            print(TAG, "Couldn't determine whether the device has a navigation bar");
            return hasNavigationBarBackup(resources);
        }
    }

    public static void share(final String TAG, final Context context, final String name, final String title, final String url) {
        print(TAG, "Share()");
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, title);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, url);
        sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(sharingIntent, name));
    }
}
