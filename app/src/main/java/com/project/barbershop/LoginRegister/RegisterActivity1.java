package com.project.barbershop.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.barbershop.R;
import com.project.barbershop.SharedPreferenceManager;

public class RegisterActivity1 extends AppCompatActivity {
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etAlamat;
    private EditText etNoTelepon;
    private Button btnSubmit;

    private SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        sharedPreferenceManager = new SharedPreferenceManager(this);

        etUsername = findViewById(R.id.r_username);
        etEmail = findViewById(R.id.r_email);
        etPassword = findViewById(R.id.r_password);
        etAlamat = findViewById(R.id.r_alamat);
        etNoTelepon = findViewById(R.id.r_noTelepon);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String alamat = etAlamat.getText().toString().trim();
                String noTelepon = etNoTelepon.getText().toString().trim();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || alamat.isEmpty() || noTelepon.isEmpty()) {
                    Toast.makeText(RegisterActivity1.this, "Mohon lengkapi semua field", Toast.LENGTH_SHORT).show();
                } else {
                    sharedPreferenceManager.saveCredentials(username, email, password, alamat, noTelepon);
                    Toast.makeText(RegisterActivity1.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity1.this, LoginActivity1.class));
                    finish();
                }
            }
        });
    }
}
