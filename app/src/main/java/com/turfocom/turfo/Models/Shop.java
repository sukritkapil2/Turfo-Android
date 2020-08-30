package com.turfocom.turfo.Models;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

class Owner {
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

public class Shop {
    @SerializedName("name")
    String name;

    @SerializedName("address")
    String address;

    @SerializedName("owner")
    Owner owner;

    @SerializedName("category")
    String category;

    @SerializedName("thumbnail")
    String thumbnail;

    @SerializedName("gallery")
    ArrayList<String> gallery;

    @SerializedName("shopPhone")
    String shopPhone;

    @SerializedName("rating")
    Integer rating;

    @SerializedName("location")
    Location location;

    @SerializedName("defaultDeliveryPrice")
    Integer defaultDeliveryPrice;

    @SerializedName("dateAdded")
    Date dateAdded;

    @SerializedName("verified")
    boolean verified;

    @SerializedName("reviews")
    ArrayList<Review> reviews;

    public Shop(String name, String address, Owner owner, String category, String thumbnail, ArrayList<String> gallery, String shopPhone, Integer rating, Location location, Integer defaultDeliveryPrice, Date dateAdded, boolean verified, ArrayList<Review> reviews) {
        this.name = name;
        this.address = address;
        this.owner = owner;
        this.category = category;
        this.thumbnail = thumbnail;
        this.gallery = gallery;
        this.shopPhone = shopPhone;
        this.rating = rating;
        this.location = location;
        this.defaultDeliveryPrice = defaultDeliveryPrice;
        this.dateAdded = dateAdded;
        this.verified = verified;
        this.reviews = reviews;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ArrayList<String> getGallery() {
        return gallery;
    }

    public void setGallery(ArrayList<String> gallery) {
        this.gallery = gallery;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getDefaultDeliveryPrice() {
        return defaultDeliveryPrice;
    }

    public void setDefaultDeliveryPrice(Integer defaultDeliveryPrice) {
        this.defaultDeliveryPrice = defaultDeliveryPrice;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Review review) {
        this.reviews = reviews;
    }
}
