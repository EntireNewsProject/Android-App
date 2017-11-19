package com.csci150.newsapp.entirenews;

import org.parceler.Parcel;

import io.realm.NewsItemRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Shifatul Islam (Denocyte) on 9/24/2017 9:46 PM.
 * A news app, where you can find everything in one place.
 */
@Parcel(implementations = {NewsItemRealmProxy.class},
        value = Parcel.Serialization.FIELD,
        analyze = {NewsItem.class})
public class NewsItem extends RealmObject {

    public NewsItem() {
    }

    @PrimaryKey
    private String _id;
    private String title;
    private String subtitle;
    private String source;
    private String cover;
    private String article;
    private String slug;
    private int saves;
    private int views;
    /*
    @SerializedName("keywords")
    @Expose
    private RealmList<RealmString> keywords;
    @SerializedName("tags")
    @Expose
    private RealmList<RealmString> tags;*/
    private String createdAt;

    private boolean saved;

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
        return subtitle;
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
    /*
    public RealmList<RealmString> getKeywords() {
        return keywords;
    }

    public void setKeywords(RealmList<RealmString> keywords) {
        this.keywords = keywords;
    }

    public RealmList<RealmString> getTags() {
        return tags;
    }

    public void setTags(RealmList<RealmString> tags) {
        this.tags = tags;
    }*/

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

