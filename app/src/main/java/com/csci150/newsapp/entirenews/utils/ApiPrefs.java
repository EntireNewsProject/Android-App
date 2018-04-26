package com.csci150.newsapp.entirenews.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.csci150.newsapp.entirenews.User;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.RealmObject;
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

    private static final String KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN";
    private static final String KEY_USER_FULL_NAME = "KEY_USER_FULL_NAME";
    private static final String KEY_USER_USERNAME = "KEY_USER_USERNAME";
    private static final String KEY_USER_EMAIL = "KEY_USER_EMAIL";
    private static final String KEY_USER_TYPE = "KEY_USER_TYPE";

    private static volatile ApiPrefs singleton;
    private final SharedPreferences prefs;
    private ApiInterface api;

    private boolean isLoggedIn = false;
    private String accessToken;
    private String userFullName;
    private String userUsername;
    private String userEmail;
    private int userType;

    private ApiPrefs(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
        accessToken = prefs.getString(KEY_ACCESS_TOKEN, null);
        Utils.print("-----------",accessToken+"");
        isLoggedIn = !TextUtils.isEmpty(accessToken);
        if (isLoggedIn) {
            userFullName = prefs.getString(KEY_USER_FULL_NAME, null);
            userUsername = prefs.getString(KEY_USER_USERNAME, null);
            userEmail = prefs.getString(KEY_USER_EMAIL, null);
            userType = prefs.getInt(KEY_USER_TYPE, 0);
        }
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

    public String getAccessToken() {
        return !TextUtils.isEmpty(accessToken) ? accessToken : null;
    }

    public void setAccessToken(String token) {
        if (!TextUtils.isEmpty(token)) {
            accessToken = token;
            isLoggedIn = true;
            prefs.edit().putString(KEY_ACCESS_TOKEN, accessToken).apply();
        }
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    @SuppressLint("ApplySharedPref")
    public void login(User user, String token) {
        if (!TextUtils.isEmpty(token)) {
            accessToken = token;
            isLoggedIn = true;
            userFullName = user.getFullName();
            userUsername = user.getUsername();
            userType = user.getType();
            userEmail = user.getEmail();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_ACCESS_TOKEN, accessToken);
            editor.putString(KEY_USER_FULL_NAME, userFullName);
            editor.putString(KEY_USER_USERNAME, userUsername);
            editor.putString(KEY_USER_EMAIL, userEmail);
            editor.putInt(KEY_USER_TYPE, userType);
            editor.commit();
        }
    }

    public boolean logout() {
        if (isLoggedIn) {
            isLoggedIn = false;
            accessToken = null;
            userFullName = null;
            userUsername = null;
            userType = 0;
            userEmail = null;

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_ACCESS_TOKEN, accessToken);
            editor.putString(KEY_USER_FULL_NAME, userFullName);
            editor.putString(KEY_USER_USERNAME, userUsername);
            editor.putString(KEY_USER_EMAIL, userEmail);
            editor.putInt(KEY_USER_TYPE, userType);
            editor.apply();
            return true;
        }
        return false;
    }

    private void createApi() {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes attributes) {
                        return attributes.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        api = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create((ApiInterface.class));
    }

    public String getUserFullName() {
        if (isLoggedIn) return userFullName;
        return null;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getUserType() {
        return userType;
    }
}
