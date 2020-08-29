package com.turfocom.turfo.Models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("seller")
    boolean seller;

    @SerializedName("name")
    String name;

    @SerializedName("email")
    String email;

    public User(boolean seller, String name, String email) {
        this.seller = seller;
        this.name = name;
        this.email = email;
    }

    public boolean isSeller() {
        return seller;
    }

    public void setSeller(boolean seller) {
        this.seller = seller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
