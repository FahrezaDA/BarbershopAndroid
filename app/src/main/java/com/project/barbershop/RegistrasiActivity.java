package com.project.barbershop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistrasiActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText, emailEditText, alamatEditText;
    private Button registrasiButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        // Inisialisasi tampilan
        usernameEditText = findViewById(R.id.r_username);
        passwordEditText = findViewById(R.id.r_password);
        emailEditText = findViewById(R.id.no_telpon);
        alamatEditText = findViewById(R.id.alamat);
        registrasiButton = findViewById(R.id.btnRegistrasi);

        // Set klik listener untuk button registrasi
        registrasiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil nilai dari form
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String alamat = alamatEditText.getText().toString().trim();

                // Validasi input
                if (username.isEmpty() || password.isEmpty() || email.isEmpty() || alamat.isEmpty()) {
                    Toast.makeText(RegistrasiActivity.this, "Mohon isi semua kolom", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kirim permintaan POST ke server
                JSONObject requestBody = new JSONObject();
                try {
                    requestBody.put("username", username);
                    requestBody.put("password", password);
                    requestBody.put("email", email);
                    requestBody.put("alamat", alamat);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = "https://example.com/api/register";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("RegistrasiActivity", "response: " + response);
                                Toast.makeText(RegistrasiActivity.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("RegistrasiActivity", "error: " + error);
                                Toast.makeText(RegistrasiActivity.this, "Terjadi kesalahan saat registrasi", Toast.LENGTH_SHORT).show();
                            }
                        });

                // Tambahkan permintaan ke antrian Volley
                Volley.newRequestQueue(RegistrasiActivity.this).add(jsonObjectRequest);
            }
        });
    }
}

