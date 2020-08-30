package com.turfocom.turfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.turfocom.turfo.API.EndPoints;
import com.turfocom.turfo.Adapters.CategoryProductsAdapter;
import com.turfocom.turfo.Models.Category;
import com.turfocom.turfo.Models.Product;
import com.turfocom.turfo.Retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryProductsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private MaterialToolbar topAppBar;
    private RecyclerView recyclerView;
    private CategoryProductsAdapter adapter;

    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);

        progressBar = findViewById(R.id.productLoading);
        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setTitle(getIntent().getStringExtra("category"));

        SharedPreferences appConfig = getApplicationContext().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);

        Location location = new Location("");
        location.setLatitude(getDouble(appConfig, "latitude", 360));
        location.setLongitude(getDouble(appConfig, "longitude", 360));

        getCategoryProducts(location);
    }

    private void getCategoryProducts(Location location) {
        EndPoints service = RetrofitClientInstance.getRetrofitInstance().create(EndPoints.class);
        Call<List<Product>> call  = service.getCategoryProduct(getIntent().getStringExtra("category").toLowerCase(), location.getLatitude(), location.getLongitude(), 6.0);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                progressBar.setVisibility(View.GONE);
                generateCategoryView(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CategoryProductsActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void generateCategoryView(List<Product> products) {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new CategoryProductsAdapter(this, products);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CategoryProductsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}