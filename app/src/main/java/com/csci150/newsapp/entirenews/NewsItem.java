package com.csci150.newsapp.entirenews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Shifatul Islam (Denocyte) on 9/24/2017 9:46 PM.
 * A listing app, where you can find everything in one place.
 */

public class NewsItem {
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("subtitle")
    @Expose
    public String subtitle;
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("cover")
    @Expose
    public String cover;
    @SerializedName("article")
    @Expose
    public String article;
    @SerializedName("slug")
    @Expose
    public String slug;
    @SerializedName("saves")
    @Expose
    public int saves;
    @SerializedName("views")
    @Expose
    public int views;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("keywords")
    @Expose
    public List<String> keywords;
    @SerializedName("tags")
    @Expose
    public List<String> tags;

    public NewsItem(String id, String title, String article) {
        this.id = id;
        this.title = title;
        this.subtitle = article.substring(0, Math.min(article.length(), 100));
        this.article = article;
    }

    @Override
    public String toString() {
        return title;
    }
}
