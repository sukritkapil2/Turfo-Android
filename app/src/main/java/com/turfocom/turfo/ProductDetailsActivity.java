package com.turfocom.turfo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
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
import com.turfocom.turfo.Models.Product;
import com.turfocom.turfo.Retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ImageView productImage;
    private TextView productName, productDetails, productPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        progressBar = findViewById(R.id.productLoading);
        productImage = findViewById(R.id.image);
        productName = findViewById(R.id.productDetails);
        productDetails = findViewById(R.id.desc);
        productPrice = findViewById(R.id.itemPrice);

        getProductDetails();
    }

    private void getProductDetails() {
        EndPoints service = RetrofitClientInstance.getRetrofitInstance().create(EndPoints.class);
        Call<Product> call = service.getSpecificProduct(getIntent().getStringExtra("productId"));

        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful()) {
                    productName.setText(response.body().getName());
                    productDetails.setText(response.body().getDetails());
                    productPrice.setText("₹ " + response.body().getPrice() + " /-");

                    Glide.with(getApplicationContext())
                            .asBitmap()
                            .load(response.body().getThumbnail())
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                            .into(new BitmapImageViewTarget(productImage) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    //Play with bitmap
                                    super.setResource(resource);
                                }
                            });
                } else {
                    Toast.makeText(ProductDetailsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}