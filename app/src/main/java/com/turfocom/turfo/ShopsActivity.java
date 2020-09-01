package com.turfocom.turfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.turfocom.turfo.API.EndPoints;
import com.turfocom.turfo.Adapters.HomeShopsAdapter;
import com.turfocom.turfo.Models.Shop;
import com.turfocom.turfo.Retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HomeShopsAdapter adapter;
    private ProgressBar progressBar;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        progressBar = findViewById(R.id.shopsLoading);
        SharedPreferences appConfig = getApplicationContext().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        city = appConfig.getString("city", "talwara");

        fetchShopsNearby();
    }

    private void fetchShopsNearby() {
        EndPoints service = RetrofitClientInstance.getRetrofitInstance().create(EndPoints.class);
        Call<List<Shop>> call = service.getNearbyShops(city);

        call.enqueue(new Callback<List<Shop>>() {
            @Override
            public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()) {
                    generateShops(response.body());
                } else {
                    Toast.makeText(ShopsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Shop>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ShopsActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void generateShops(List<Shop> shopsList) {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new HomeShopsAdapter(this, shopsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ShopsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}