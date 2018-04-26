package com.csci150.newsapp.entirenews;

/**
 * Created by Shifatul Islam (Denocyte) on 4/25/2018 1:43 PM.
 * A listing app, where you can find everything in one place.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefaultMsg {

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

