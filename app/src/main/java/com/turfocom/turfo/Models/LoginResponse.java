package com.turfocom.turfo.Models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("message")
    String message;

    @SerializedName("token")
    String token;

    @SerializedName("id")
    String id;

    public LoginResponse(String message, String token, String id) {
        this.message = message;
        this.token = token;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
