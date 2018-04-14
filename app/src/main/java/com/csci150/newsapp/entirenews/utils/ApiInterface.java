package com.csci150.newsapp.entirenews.utils;

import android.support.annotation.NonNull;

import com.csci150.newsapp.entirenews.NewsItem;
import com.csci150.newsapp.entirenews.Ping;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
            @NonNull @Path("id") String id,
            @Query("savecheck") boolean save);

    @FormUrlEncoded
    @POST("login/token")
    Call<Ping> login(
            @NonNull @Field("username") String username,
            @NonNull @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<Ping> register(
            @NonNull @Field("email") RequestBody email,
            @NonNull @Field("password") RequestBody password,
            @NonNull @Field("username") RequestBody username,
            @NonNull @Field("fullName") RequestBody firstName);

}
