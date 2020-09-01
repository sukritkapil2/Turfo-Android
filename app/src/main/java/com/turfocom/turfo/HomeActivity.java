package com.turfocom.turfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.turfocom.turfo.API.EndPoints;
import com.turfocom.turfo.Adapters.CategoryProductsAdapter;
import com.turfocom.turfo.Adapters.HomeCategoryAdapter;
import com.turfocom.turfo.Adapters.HomeShopsAdapter;
import com.turfocom.turfo.Adapters.TrendingProductsAdapter;
import com.turfocom.turfo.Models.Category;
import com.turfocom.turfo.Models.Product;
import com.turfocom.turfo.Models.Shop;
import com.turfocom.turfo.Retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private HomeShopsAdapter adapter;
    private HomeCategoryAdapter adapter2;
    private TrendingProductsAdapter adapter3;
    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private ProgressBar progressBar;
    private MaterialToolbar topAppBar;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Shared Preferences Setup
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences appConfig = getApplicationContext().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        appConfig.edit().putBoolean("firstTime", false).apply();

        //Assigning Views
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation);
        progressBar = findViewById(R.id.shopsLoading);
        topAppBar = findViewById(R.id.topAppBar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, topAppBar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        TextView textView = navigationView.getHeaderView(0).findViewById(R.id.textHeader);
        textView.setText("Welcome " + sharedPreferences.getString("name", "Sign In"));
        TextView email = navigationView.getHeaderView(0).findViewById(R.id.emailHeader);
        email.setText(sharedPreferences.getString("email", "Sign In & Get Started"));
        city = appConfig.getString("city", "talwara");

        navigationView.setNavigationItemSelectedListener(this);
        fetchShopsNearby();
        fetchCategories();
        fetchTrendingProducts();
    }

    private void fetchShopsNearby() {
        EndPoints service = RetrofitClientInstance.getRetrofitInstance().create(EndPoints.class);
        Call<List<Shop>> call = service.getNearbyShops(city);

        call.enqueue(new Callback<List<Shop>>() {
            @Override
            public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()) {
                    if(response.body().size() > 4) {
                        generateFourShops(response.body().subList(0, 4));
                    }
                    else generateFourShops(response.body());
                } else {
                    Toast.makeText(HomeActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Shop>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(HomeActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchCategories() {
        EndPoints service = RetrofitClientInstance.getRetrofitInstance().create(EndPoints.class);
        Call<List<Category>> call = service.getCategories();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()) {
                    generateCategories(response.body());
                }
                else {
                    Toast.makeText(HomeActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchTrendingProducts() {
        EndPoints service = RetrofitClientInstance.getRetrofitInstance().create(EndPoints.class);
        Call<List<Product>> call = service.getTrendingProducts(city);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()) {
                    generateTrending(response.body());
                }
                else {
                    Toast.makeText(HomeActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void generateTrending(List<Product> productList) {
        recyclerView3 = findViewById(R.id.recyclerViewTrending);
        recyclerView3.setNestedScrollingEnabled(false);
        adapter3 = new TrendingProductsAdapter(this, productList);
        recyclerView3.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView3.setAdapter(adapter3);
    }

    private void generateFourShops(List<Shop> shopsList) {
        recyclerView = findViewById(R.id.recyclerViewShops);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new HomeShopsAdapter(this, shopsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void generateCategories(List<Category> categoryList) {
        recyclerView2 = findViewById(R.id.recyclerViewCategory);
        adapter2 = new HomeCategoryAdapter(this, categoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager);
        recyclerView2.setAdapter(adapter2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account:
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("userToken", "");

                if(token == null || token.length() == 0) {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HomeActivity.this, AccountActivity.class);
                    startActivity(intent);
                }
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}