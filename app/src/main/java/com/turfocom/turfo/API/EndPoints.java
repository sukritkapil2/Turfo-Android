package com.turfocom.turfo.API;

import com.turfocom.turfo.Models.LoginResponse;
import com.turfocom.turfo.Models.User;
import com.turfocom.turfo.Models.UserLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EndPoints {
    @POST("user/login")
    Call<LoginResponse> loginUser(@Body UserLogin userLogin);

    @GET("user/{id}")
    Call<User> getUserDetails(@Path("id") String id, @Header("Authorization") String token);
}
