package com.csci150.newsapp.entirenews.utils;

import android.support.annotation.NonNull;

import com.csci150.newsapp.entirenews.Login;
import com.csci150.newsapp.entirenews.NewsItem;
import com.csci150.newsapp.entirenews.Ping;
import com.csci150.newsapp.entirenews.Register;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Shifatul Islam (Denocyte) on 10/22/2017 10:24 PM.
 * A listing app, where you can find everything in one place.
 */

public interface ApiInterface {

    @GET("user/ping")
    Call<Ping> ping(
            @Header("Authorization") String token);

    @GET("news/{type}")
    Call<List<NewsItem>> getRec(
            @Header("Authorization") String token,
            @NonNull @Path("type") String type);

    @GET("news")
    Call<List<NewsItem>> getNews(
            @NonNull @Query("source") String source,
            @Query("page") int page);

    @GET("news/{id}")
    Call<NewsItem> getNews(
            @Header("Authorization") String token,
            @NonNull @Path("id") String id);

    @GET("news/{id}/save")
    Call<NewsItem> saveNews(
            @NonNull @Path("id") String id,
            @Query("savecheck") boolean save);

    @FormUrlEncoded
    @POST("user/login")
    Call<Login> login(
            @NonNull @Field("username") String username,
            @NonNull @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Call<Register> register(
            @NonNull @Field("email") String email,
            @NonNull @Field("password") String password,
            @NonNull @Field("username") String username,
            @NonNull @Field("fullName") String firstName);

}
