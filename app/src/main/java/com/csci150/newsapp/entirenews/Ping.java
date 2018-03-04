package com.csci150.newsapp.entirenews;

/**
 * Created by kishanshah on 3/3/18.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ping {

    @SerializedName("message")
    @Expose
    public String message;

}

