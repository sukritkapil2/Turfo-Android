package com.turfocom.turfo.Models;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("latitude")
    Double latitude;

    @SerializedName("longitude")
    Double longitude;

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}