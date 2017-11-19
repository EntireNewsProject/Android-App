package com.csci150.newsapp.entirenews.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import com.csci150.newsapp.entirenews.NewsItem;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Shifatul Islam (Denocyte) on 11/18/2017 7:25 PM.
 * A listing app, where you can find everything in one place.
 */

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {
        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {
        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {
        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {
        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from NewsItem.class
    public void clearAll() {
        realm.beginTransaction();
        realm.clear(NewsItem.class);
        realm.commitTransaction();
    }

    //find all objects in the NewsItem.class
    public RealmResults<NewsItem> getNewsItems() {
        return realm.where(NewsItem.class).findAll();
    }

    //query a single item with the given id
    public NewsItem getNewsItem(String _id) {
        return realm.where(NewsItem.class).equalTo("_id", _id).findFirst();
    }

    //check if NewsItem.class is empty
    public boolean hasNewsItems() {
        return !realm.allObjects(NewsItem.class).isEmpty();
    }

    //query example
    public RealmResults<NewsItem> queryedNewsItems() {
        return realm.where(NewsItem.class)
                .contains("_id", "XXX")
                .or()
                .contains("title", "XXX")
                .findAll();

    }
}
