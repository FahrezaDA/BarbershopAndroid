package com.project.barbershop.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.barbershop.ProfileActivity1;
import com.project.barbershop.R;
import com.project.barbershop.SharedPreferenceManager;

public class LoginActivity1 extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;

    private SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferenceManager = new SharedPreferenceManager(this);

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity1.this, "Mohon lengkapi email dan password", Toast.LENGTH_SHORT).show();
                } else {
                    if (sharedPreferenceManager.checkCredentials(email, password)) {
                        Toast.makeText(LoginActivity1.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity1.this, ProfileActivity1.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity1.this, "Email atau password salah", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity1.this, RegisterActivity1.class));
            }
        });
    }
}
