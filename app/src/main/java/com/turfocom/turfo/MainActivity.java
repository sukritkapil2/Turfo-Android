package com.turfocom.turfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.turfocom.turfo.API.EndPoints;
import com.turfocom.turfo.Models.User;
import com.turfocom.turfo.Models.UserLogin;
import com.turfocom.turfo.Retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// This activity checks if the user is logged in or not -> if logged in -> redirects to home activity
// else redirects to Login Activity
public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences, appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait ...");
        progressDialog.show();

        sharedPreferences = getApplicationContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        appConfig = getApplicationContext().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("userToken", "");
        String id = sharedPreferences.getString("userId", "");
        boolean firstTime = appConfig.getBoolean("firstTime", true);

        // If user is logged in
        if(token != null && token.length() != 0 && id != null && id.length() != 0) {
            getUserStatus(id, token);
        } else {
            if(firstTime) {
                Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);
                startActivity(intent);
            } else {
                String city = appConfig.getString("city", "");

                if(city.length() == 0) {
                    Intent intent = new Intent(MainActivity.this, CitySelectActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    private void getUserStatus(String id, String token) {

        EndPoints service = RetrofitClientInstance.getRetrofitInstance().create(EndPoints.class);
        Call<User> call = service.getUserDetails(id, "Bearer " + token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                progressDialog.dismiss();
                if(!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Error : " + response.code(), Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                    sharedPreferences.edit().clear().apply();
                    Intent intent = new Intent(MainActivity.this, ChoiceActivity.class);
                    startActivity(intent);
                } else {
                    if(response.body().isSeller()) {
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                        sharedPreferences.edit().putBoolean("seller", true).apply();
                        sharedPreferences.edit().putString("name", response.body().getName()).apply();
                        sharedPreferences.edit().putString("email", response.body().getEmail()).apply();
                    } else {
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                        sharedPreferences.edit().putBoolean("seller", false).apply();
                        sharedPreferences.edit().putString("name", response.body().getName()).apply();
                        sharedPreferences.edit().putString("email", response.body().getEmail()).apply();
                    }

                    String city = appConfig.getString("city", "");
                    if(city.length() == 0) {
                        Intent intent = new Intent(MainActivity.this, CitySelectActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}