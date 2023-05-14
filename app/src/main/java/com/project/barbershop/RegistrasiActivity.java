package com.project.barbershop;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.project.barbershop.servis.ServerRequestRegistrasi;

public class RegistrasiActivity extends AppCompatActivity {

    private EditText etUsername, etPassword,etEmail,etAlamat;
    private Button btnSubmit;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        etUsername = findViewById(R.id.r_username);
        etEmail = findViewById(R.id.r_email);
        etPassword = findViewById(R.id.r_password);

        etAlamat= findViewById(R.id.r_alamat);

        btnSubmit= findViewById(R.id.btnSubmit);




        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil nilai dari edit text username dan password
                String name = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String alamat = etAlamat.getText().toString();

                // Lakukan request ke server dengan menggunakan Volley
               ServerRequestRegistrasi serverRequest = new ServerRequestRegistrasi(RegistrasiActivity.this);
                serverRequest.register(name,email, password,alamat, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Response berhasil diterima, lakukan aksi yang diperlukan
                        Toast.makeText(RegistrasiActivity.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Terjadi error saat melakukan request ke server
                        Toast.makeText(RegistrasiActivity.this, "Terjadi kesalahan: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}