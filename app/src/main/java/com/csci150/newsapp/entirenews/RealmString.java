package com.csci150.newsapp.entirenews;

import io.realm.RealmObject;

/**
 * Created by Shifatul Islam (Denocyte) on 11/17/2017 12:20 AM.
 * A listing app, where you can find everything in one place.
 */

public class RealmString extends RealmObject {

    public RealmString() {
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
