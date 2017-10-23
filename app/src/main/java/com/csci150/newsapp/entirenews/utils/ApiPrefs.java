package com.csci150.newsapp.entirenews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shifatul Islam (Denocyte) on 10/22/2017 10:28 PM.
 * A listing app, where you can find everything in one place.
 */

public class ApiPrefs {
    private static final String APP_PREF = "ENTIRENEWS_PREF";
    private static final String URL_BASE = "http://entirenews.tk:3000/";
    private static final String URL_API = URL_BASE + "api/";

    private static volatile ApiPrefs singleton;
    private final SharedPreferences prefs;
    private ApiInterface api;

    private ApiPrefs(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(APP_PREF, Context
                .MODE_PRIVATE);
    }

    public static ApiPrefs get(Context context) {
        if (singleton == null)
            synchronized (ApiPrefs.class) {
                singleton = new ApiPrefs(context);
            }
        return singleton;
    }

    public ApiInterface getApi() {
        if (api == null) createApi();
        return api;
    }

    private void createApi() {
        api = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create((ApiInterface.class));
    }

}
