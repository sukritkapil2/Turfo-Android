package com.turfocom.turfo.Models;

import com.google.gson.annotations.SerializedName;

public class Owner {

    @SerializedName("name")
    String name;

    @SerializedName("pic")
    String pic;

    @SerializedName("phone")
    String phone;

    @SerializedName("email")
    String email;

    public Owner(String name, String pic, String phone, String email) {
        this.name = name;
        this.pic = pic;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
