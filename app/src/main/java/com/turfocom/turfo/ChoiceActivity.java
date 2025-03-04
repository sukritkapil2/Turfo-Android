package com.turfocom.turfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChoiceActivity extends AppCompatActivity {

    private Button login, register, skip;
    private SharedPreferences appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        login = findViewById(R.id.button);
        register = findViewById(R.id.button2);
        skip = findViewById(R.id.button3);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoiceActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                appConfig = getApplicationContext().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);

                String city = appConfig.getString("city", "");
                if(city.length() == 0) {
                    Intent intent = new Intent(ChoiceActivity.this, CitySelectActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(ChoiceActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}