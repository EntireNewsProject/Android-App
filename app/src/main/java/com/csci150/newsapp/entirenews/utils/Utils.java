package com.csci150.newsapp.entirenews.utils;

import android.util.Log;

/**
 * Created by Shifatul Islam (Denocyte) on 10/13/2017 9:29 PM.
 * A listing app, where you can find everything in one place.
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
}
