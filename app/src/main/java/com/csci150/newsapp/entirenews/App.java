package com.csci150.newsapp.entirenews;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Shifatul Islam (Denocyte) on 10/22/2017 11:33 PM.
 * A listing app, where you can find everything in one place.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
        //        .setDefaultFontPath(getString(R.string.font_name))
        //       .setFontAttrId(R.attr.fontPath)
        //        .build());

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);

        JodaTimeAndroid.init(this);
    }
}
