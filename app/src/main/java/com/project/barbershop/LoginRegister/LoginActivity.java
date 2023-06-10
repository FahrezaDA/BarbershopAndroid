package com.project.barbershop.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.barbershop.BookingActivity1;
import com.project.barbershop.MainActivity;
import com.project.barbershop.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String url = "http://192.168.1.20/barbershopLaravel/public/api/login";
    private  Button btnRegister;
   private  Button btnLogin;
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnRegister=findViewById(R.id.btnRegister);
        btnLogin=findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, BookingActivity1.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loginUser() {

        final String email = etEmail.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();


        // Buat request POST ke URL REGISTER_URL
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Tanggapan dari server jika pendaftaran berhasil
                        Toast.makeText(LoginActivity.this,"Login berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Tanggapan dari server jika terjadi kesalahan
                        Toast.makeText(LoginActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        // Buat antrian permintaan Volley dan tambahkan permintaan ke antrian
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
