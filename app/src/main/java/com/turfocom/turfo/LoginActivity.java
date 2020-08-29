package com.turfocom.turfo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.turfocom.turfo.API.EndPoints;
import com.turfocom.turfo.Models.LoginResponse;
import com.turfocom.turfo.Models.UserLogin;
import com.turfocom.turfo.Retrofit.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private String email, password;
    private EditText emailT, passwordT;
    private Button login;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        emailT = findViewById(R.id.email);
        passwordT = findViewById(R.id.password);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Verifying Details ...");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailT.getText().toString();
                password = passwordT.getText().toString();

                if(email.length() == 0 || password.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Please Enter All Credentials", Toast.LENGTH_SHORT);
                }
                else {
                    progressDialog.show();
                    loginUser();
                }
            }
        });
    }

    private void loginUser() {
        EndPoints service = RetrofitClientInstance.getRetrofitInstance().create(EndPoints.class);
        UserLogin userLogin = new UserLogin(email, password);

        Call<LoginResponse> call = service.loginUser(userLogin);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();
                if(!response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Error : " + response.code(), Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("userToken", response.body().getToken()).apply();
                    sharedPreferences.edit().putString("userId", response.body().getId()).apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}