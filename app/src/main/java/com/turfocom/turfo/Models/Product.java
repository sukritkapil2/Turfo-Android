package com.turfocom.turfo.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class Product {

    @SerializedName("_id")
    String _id;

    @SerializedName("name")
    String name;

    @SerializedName("price")
    Integer price;

    @SerializedName("unit")
    String unit;

    @SerializedName("rating")
    Integer rating;

    @SerializedName("thumbnail")
    String thumbnail;

    @SerializedName("details")
    String details;

    @SerializedName("images")
    ArrayList<String> images;

    @SerializedName("lastUpdated")
    Date lastUpdated;

    @SerializedName("shopId")
    String shopId;

    @SerializedName("category")
    String category;

    @SerializedName("deliveryPrice")
    Integer deliveryPrice;

    @SerializedName("complaints")
    ArrayList<Review> complaints;

    @SerializedName("city")
    String city;

    @SerializedName("purchases")
    Integer purchases;

    public Product(String _id, String name, Integer price, String unit, Integer rating, String thumbnail, String details, ArrayList<String> images, Date lastUpdated, String shopId, String category, Integer deliveryPrice, ArrayList<Review> complaints, String city, Integer purchases) {
        this._id = _id;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.rating = rating;
        this.thumbnail = thumbnail;
        this.details = details;
        this.images = images;
        this.lastUpdated = lastUpdated;
        this.shopId = shopId;
        this.category = category;
        this.deliveryPrice = deliveryPrice;
        this.complaints = complaints;
        this.city = city;
        this.purchases = purchases;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Integer deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public ArrayList<Review> getComplaints() {
        return complaints;
    }

    public void setComplaints(ArrayList<Review> complaints) {
        this.complaints = complaints;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPurchases() {
        return purchases;
    }

    public void setPurchases(Integer purchases) {
        this.purchases = purchases;
    }
}
