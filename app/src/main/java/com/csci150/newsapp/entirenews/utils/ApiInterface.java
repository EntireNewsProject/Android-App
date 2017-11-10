package com.csci150.newsapp.entirenews.utils;

import android.support.annotation.NonNull;

import com.csci150.newsapp.entirenews.NewsItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Shifatul Islam (Denocyte) on 10/22/2017 10:24 PM.
 * A listing app, where you can find everything in one place.
 */

public interface ApiInterface {

    @GET("news")
    Call<List<NewsItem>> getNews(
            @NonNull @Query("source") String source,
            @Query("page") int page);

    @GET("news/{id}")
    Call<NewsItem> getNews(
            @NonNull @Path("id") String id);

    @GET("news/{id}/save")
    Call<NewsItem> saveNews(
            @NonNull @Path("id") String id);
}
