package com.project.barbershop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.barbershop.apiConfig.apiConfig;
import com.project.barbershop.servis.SharedPreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    private TextView tvUsername;
    private TextView tvEmail;
    private TextView tvAlamat;
    private TextView tvNoTelepon;
    private Button btnUpdate;

    private SharedPreferenceManager sharedPreferenceManager;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUsername = findViewById(R.id.profile_nama);
        tvEmail = findViewById(R.id.profile_email);
        tvAlamat = findViewById(R.id.profile_alamat);
        tvNoTelepon = findViewById(R.id.profile_noTelepon);
        btnUpdate = findViewById(R.id.btnUpdate);

        sharedPreferenceManager = new SharedPreferenceManager(this);

        // Mendapatkan email pengguna dari SharedPreferenceManager atau sumber lainnya
        email = sharedPreferenceManager.getEmail();

        fetchProfile();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                    return true;
                case R.id.bottom_booking:
                    startActivity(new Intent(getApplicationContext(), BookingActivity1.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                    return true;

                case R.id.bottom_order:
                    startActivity(new Intent(getApplicationContext(), BookingActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                    return true;
                case R.id.bottom_profile:
                    return true;

            }
            return false;
        });
    }

    private void fetchProfile() {
        String url = apiConfig.URL_API + "/profile?email=" + email;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String username = response.getString("nama");
                            String email = response.getString("email");
                            String alamat = response.getString("alamat");
                            String noTelepon = response.getString("no_telepon");

                            tvUsername.setText("  " + username);
                            tvEmail.setText("  " + email);
                            tvAlamat.setText("  " + alamat);
                            tvNoTelepon.setText(" " + noTelepon);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ProfileActivity.this, "Failed to parse profile data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(ProfileActivity.this, "Failed to fetch profile", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(request);
    }

    private void updateProfile() {
        String url = apiConfig.URL_API + "/update_profile";

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("email", email);
            // Mendapatkan data profil yang ingin diperbarui dari input pengguna
            String updatedUsername = tvUsername.getText().toString().trim();
            String updatedAlamat = tvAlamat.getText().toString().trim();
            String updatedNoTelepon = tvNoTelepon.getText().toString().trim();
            requestObject.put("nama", updatedUsername);
            requestObject.put("alamat", updatedAlamat);
            requestObject.put("no_telepon", updatedNoTelepon);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ProfileActivity.this, "Failed to create update request", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, requestObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ProfileActivity.this, "Update berhasil", Toast.LENGTH_SHORT).show();
                        fetchProfile(); // Refresh tampilan dengan data yang baru saja diperbarui
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(ProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(request);
    }
}
