package com.csci150.newsapp.entirenews;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Shifatul Islam (Denocyte) on 9/24/2017 9:46 PM.
 * A listing app, where you can find everything in one place.
 */

public class NewsItem implements Parcelable {
    public static final Creator<NewsItem> CREATOR = new Creator<NewsItem>() {
        @Override
        public NewsItem createFromParcel(Parcel in) {
            return new NewsItem(in);
        }

        @Override
        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };
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

    public NewsItem(String title, String article, String cover) {
        this.title = title;
        this.subtitle = article.substring(0, Math.min(article.length(), 100));
        this.article = article;
        this.cover = cover;
    }

    protected NewsItem(Parcel in) {
        id = in.readString();
        title = in.readString();
        subtitle = in.readString();
        source = in.readString();
        cover = in.readString();
        article = in.readString();
        slug = in.readString();
        saves = in.readInt();
        views = in.readInt();
        date = in.readString();
        keywords = in.createStringArrayList();
        tags = in.createStringArrayList();
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(subtitle);
        parcel.writeString(source);
        parcel.writeString(cover);
        parcel.writeString(article);
        parcel.writeString(slug);
        parcel.writeInt(saves);
        parcel.writeInt(views);
        parcel.writeString(date);
        parcel.writeStringList(keywords);
        parcel.writeStringList(tags);
    }
}
