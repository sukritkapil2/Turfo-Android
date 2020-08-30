package com.turfocom.turfo.Models;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("name")
    String name;

    @SerializedName("pic")
    String pic;

    public Category(String name, String pic) {
        this.name = name;
        this.pic = pic;
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
}
