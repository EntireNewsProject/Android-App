package com.csci150.newsapp.entirenews;

/**
 * Created by Shifatul Islam (Denocyte) on 4/25/2018 1:41 PM.
 * A listing app, where you can find everything in one place.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("token")
    @Expose
    public String token;
}

