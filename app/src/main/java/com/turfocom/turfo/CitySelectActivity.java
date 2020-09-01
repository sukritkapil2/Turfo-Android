package com.turfocom.turfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CitySelectActivity extends AppCompatActivity {

    private Button selectCity;
    private SharedPreferences appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);

        selectCity = findViewById(R.id.select);
        appConfig = getApplicationContext().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);

        selectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appConfig.edit().putString("city", "talwara").apply();
                Intent intent = new Intent(CitySelectActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}