package com.csci150.newsapp.entirenews;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by Shifatul Islam (Denocyte) on 9/24/2017 9:46 PM.
 * A news app, where you can find everything in one place.
 */

@DatabaseTable
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
    @DatabaseField(id = true)
    private String id;
    @SerializedName("title")
    @Expose
    @DatabaseField
    private String title;
    @SerializedName("subtitle")
    @Expose
    @DatabaseField
    private String subtitle;
    @SerializedName("source")
    @Expose
    @DatabaseField
    private String source;
    @SerializedName("cover")
    @Expose
    @DatabaseField
    private String cover;
    @SerializedName("article")
    @Expose
    @DatabaseField
    private String article;
    @SerializedName("slug")
    @Expose
    @DatabaseField
    private String slug;
    @SerializedName("saves")
    @Expose
    @DatabaseField
    private int saves;
    @SerializedName("views")
    @Expose
    @DatabaseField
    private int views;
    @SerializedName("date")
    @Expose
    @DatabaseField
    private String date;
    @SerializedName("keywords")
    @Expose
    //@DatabaseField
    private List<String> keywords;
    @SerializedName("tags")
    @Expose
    //@DatabaseField
    private List<String> tags;
    @SerializedName("createdAt")
    @Expose
    @DatabaseField
    private String createdAt;

    private boolean saved;

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
        createdAt = in.readString();
        saved = in.readByte() != 0;
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
        parcel.writeString(createdAt);
        parcel.writeByte((byte) (saved ? 1 : 0));
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
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

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSubtitle() {
        if (!TextUtils.isEmpty(subtitle)) return subtitle;
        else if (!TextUtils.isEmpty(article))
            return article.substring(0, Math.min(article.length(), 100))
                    .replace("\n", "")
                    .replace("\r", "");
        else return "Subtitle";
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

    public void addView() {
        this.views++;
    }

    public void addSave() {
        this.saves++;
    }

    public void subSave() {
        this.saves--;
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

