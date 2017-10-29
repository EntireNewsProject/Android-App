package com.csci150.newsapp.entirenews;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Shifatul Islam (Denocyte) on 9/24/2017 9:46 PM.
 * A news app, where you can find everything in one place.
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
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subtitle")
    @Expose
    private String subtitle;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("article")
    @Expose
    private String article;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("saves")
    @Expose
    private int saves;
    @SerializedName("views")
    @Expose
    private int views;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("keywords")
    @Expose
    private List<String> keywords;
    @SerializedName("tags")
    @Expose
    private List<String> tags;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    /*public NewsItem(String title, String article, String cover) {
        this.title = title;
        this.subtitle = article.substring(0, Math.min(article.length(), 100));
        this.article = article;
        this.cover = cover;
    }*/

    protected NewsItem(Parcel in) {
        id = in.readString();
        title = in.readString();
        source = in.readString();
        cover = in.readString();
        article = in.readString();
        slug = in.readString();
        saves = in.readInt();
        views = in.readInt();
        date = in.readString();
        keywords = in.createStringArrayList();
        tags = in.createStringArrayList();
        createdAt = in.readString();
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
        parcel.writeString(source);
        parcel.writeString(cover);
        parcel.writeString(article);
        parcel.writeString(slug);
        parcel.writeInt(saves);
        parcel.writeInt(views);
        parcel.writeString(date);
        parcel.writeStringList(keywords);
        parcel.writeStringList(tags);
        parcel.writeString(createdAt);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        /*return article.substring(0, Math.min(article.length(), 100))
                .replace("\n", "")
                .replace("\r", "");*/
        return "Subtitle";
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getSaves() {
        return saves;
    }

    public void setSaves(int saves) {
        this.saves = saves;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

