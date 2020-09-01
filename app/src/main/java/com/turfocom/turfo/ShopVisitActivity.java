package com.turfocom.turfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.turfocom.turfo.API.EndPoints;
import com.turfocom.turfo.Adapters.CategoryProductsAdapter;
import com.turfocom.turfo.Models.Owner;
import com.turfocom.turfo.Models.Product;
import com.turfocom.turfo.Models.Shop;
import com.turfocom.turfo.Retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopVisitActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ImageView shopImage;
    private TextView shopName, shopOwner, shopAddress;
    private RecyclerView recyclerView;
    private CategoryProductsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_visit);

        progressBar = findViewById(R.id.shopsLoading);
        shopImage = findViewById(R.id.shopImage);
        shopName = findViewById(R.id.shopName);
        shopAddress = findViewById(R.id.shopAddress);
        shopOwner = findViewById(R.id.shopOwner);

        getShopDetails();
        getShopProducts();
    }

    private void getShopProducts() {
        EndPoints service = RetrofitClientInstance.getRetrofitInstance().create(EndPoints.class);
        Call<List<Product>> call = service.getShopProducts(getIntent().getStringExtra("shopId"));

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()) {
                    setRecyclerView(response.body());
                } else {
                    Toast.makeText(ShopVisitActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(ShopVisitActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecyclerView(List<Product> dataList) {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new CategoryProductsAdapter(this, dataList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ShopVisitActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getShopDetails() {
        EndPoints service = RetrofitClientInstance.getRetrofitInstance().create(EndPoints.class);
        Call<Shop> call = service.getSpecificShop(getIntent().getStringExtra("shopId"));

        call.enqueue(new Callback<Shop>() {
            @Override
            public void onResponse(Call<Shop> call, Response<Shop> response) {
                if(response.isSuccessful()) {

                    Owner owner = response.body().getOwner();

                    shopName.setText(response.body().getName());
                    shopAddress.setText(response.body().getAddress());
                    shopOwner.setText(owner.getName() + ", " + owner.getPhone());

                    Glide.with(getApplicationContext())
                            .asBitmap()
                            .load(response.body().getThumbnail())
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                            .into(new BitmapImageViewTarget(shopImage) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    //Play with bitmap
                                    super.setResource(resource);
                                }
                            });
                } else {
                    Toast.makeText(ShopVisitActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Shop> call, Throwable t) {
                Toast.makeText(ShopVisitActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}