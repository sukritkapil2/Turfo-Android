package com.turfocom.turfo.API;

import com.turfocom.turfo.Models.Category;
import com.turfocom.turfo.Models.LoginResponse;
import com.turfocom.turfo.Models.Product;
import com.turfocom.turfo.Models.Shop;
import com.turfocom.turfo.Models.User;
import com.turfocom.turfo.Models.UserLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EndPoints {
    @POST("user/login")
    Call<LoginResponse> loginUser(@Body UserLogin userLogin);

    @GET("user/{id}")
    Call<User> getUserDetails(@Path("id") String id, @Header("Authorization") String token);

    @GET("shops")
    Call<List<Shop>> getNearbyShops(@Query("city") String city);

    @GET("products/categories")
    Call<List<Category>> getCategories();

    @GET("products/categories/{category}")
    Call<List<Product>> getCategoryProduct(@Path("category") String category, @Query("city") String city);
}
