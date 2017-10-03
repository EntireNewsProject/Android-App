package com.csci150.newsapp.entirenews;

/**
 * Created by Shifatul Islam (Denocyte) on 9/24/2017 9:46 PM.
 * A listing app, where you can find everything in one place.
 */

public class NewsItem {
    public final String id;
    public final String content;
    public final String details;

    public NewsItem(String id, String content, String details) {
        this.id = id;
        this.content = content;
        this.details = details;
    }

    @Override
    public String toString() {
        return content;
    }
}
