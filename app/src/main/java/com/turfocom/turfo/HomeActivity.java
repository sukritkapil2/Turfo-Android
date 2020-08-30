package com.turfocom.turfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.turfocom.turfo.API.EndPoints;
import com.turfocom.turfo.Adapters.HomeCategoryAdapter;
import com.turfocom.turfo.Adapters.HomeShopsAdapter;
import com.turfocom.turfo.Models.Category;
import com.turfocom.turfo.Models.Shop;
import com.turfocom.turfo.Retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LocationListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private HomeShopsAdapter adapter;
    private HomeCategoryAdapter adapter2;
    private RecyclerView recyclerView, recyclerView2;
    private ProgressBar progressBar, progressBar2;
    private LocationManager locationManager;
    private MaterialToolbar topAppBar;
    private TextView locationPerm;
    private Button goToSettings;

    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

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
        progressBar2 = findViewById(R.id.categoryLoading);
        topAppBar = findViewById(R.id.topAppBar);
        locationPerm = findViewById(R.id.locationperm);
        locationPerm.setVisibility(View.GONE);
        goToSettings = findViewById(R.id.goToSettings);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, topAppBar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        TextView textView = navigationView.getHeaderView(0).findViewById(R.id.textHeader);
        textView.setText("Welcome " + sharedPreferences.getString("name", "Sign In"));
        TextView email = navigationView.getHeaderView(0).findViewById(R.id.emailHeader);
        email.setText(sharedPreferences.getString("email", "Sign In & Get Started"));

        goToSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });


        goToSettings.setVisibility(View.GONE);
        navigationView.setNavigationItemSelectedListener(this);

        if(getDouble(appConfig, "latitude", 360) == 360 || getDouble(appConfig, "longitude", 360) == 360) {
            Toast.makeText(HomeActivity.this, "Fetching New Location", Toast.LENGTH_SHORT).show();
            getLocation();
        }
        else {
            Toast.makeText(HomeActivity.this, "Using Previous Location", Toast.LENGTH_SHORT).show();
            Location location = new Location("");
            location.setLatitude(getDouble(appConfig, "latitude", 360));
            location.setLongitude(getDouble(appConfig, "longitude", 360));
            fetchShopsNearby(location);
        }

    }

    private void fetchShopsNearby(Location location) {
        EndPoints service = RetrofitClientInstance.getRetrofitInstance().create(EndPoints.class);
        Call<List<Shop>> call = service.getNearbyShops(location.getLatitude(), location.getLongitude(), 6.0);

        call.enqueue(new Callback<List<Shop>>() {
            @Override
            public void onResponse(Call<List<Shop>> call, Response<List<Shop>> response) {
                progressBar.setVisibility(View.GONE);
                if(response.body().size() > 4) {
                    generateFourShops(response.body().subList(0, 4));
                }
                else generateFourShops(response.body());
                Call<List<Category>> call2 = service.getCategories();

                call2.enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        progressBar2.setVisibility(View.GONE);
                        generateCategories(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        progressBar2.setVisibility(View.GONE);
                        Toast.makeText(HomeActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Shop>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(HomeActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //If location access has not been provided
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Toast.makeText(HomeActivity.this, "Please Provide Location Access 1", Toast.LENGTH_LONG).show();
            } else {
                locationPerm.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                goToSettings.setVisibility(View.VISIBLE);
                Toast.makeText(HomeActivity.this, "Please Provide Location Access 2", Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 500, this);
        }
    }

    private void generateFourShops(List<Shop> shopsList) {
        recyclerView = findViewById(R.id.recyclerViewShops);
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

    @Override
    public void onLocationChanged(@NonNull Location location) {
        SharedPreferences appConfig = getApplicationContext().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        putDouble(appConfig.edit(), "latitude", location.getLatitude()).apply();
        putDouble(appConfig.edit(), "longitude", location.getLongitude()).apply();
        fetchShopsNearby(location);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}