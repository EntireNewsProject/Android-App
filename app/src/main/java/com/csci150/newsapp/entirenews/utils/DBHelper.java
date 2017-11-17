package com.csci150.newsapp.entirenews.utils;

import android.content.Context;

import com.csci150.newsapp.entirenews.NewsItem;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import nl.elastique.poetry.database.DatabaseConfiguration;
import nl.elastique.poetry.database.DatabaseHelper;

/**
 * Created by Shifatul Islam (Denocyte) on 11/16/2017 11:44 PM.
 * A listing app, where you can find everything in one place.
 */

public class DBHelper extends DatabaseHelper {

    public final static DatabaseConfiguration sConfiguration =
            new DatabaseConfiguration(1, new Class<?>[]{
                    NewsItem.class
            });

    public DBHelper(Context context) {
        super(context);
    }

    public static DatabaseHelper getHelper(Context context) {
        return OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }
}
