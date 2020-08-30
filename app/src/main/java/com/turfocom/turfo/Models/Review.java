package com.turfocom.turfo.Models;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("name")
    String name;

    @SerializedName("content")
    String content;

    public Review(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
