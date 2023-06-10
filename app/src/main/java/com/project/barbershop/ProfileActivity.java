package com.project.barbershop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {


    private static final String TAG = ProfileActivity.class.getSimpleName();
    private EditText profileEmail, profileNama, profileNoTelpon, profileAlamat;
    private Button btnSearch, btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_booking);

        profileEmail = findViewById(R.id.profile_email);
        profileNama = findViewById(R.id.profile_nama);
        profileNoTelpon = findViewById(R.id.profile_noTelepon);
        profileAlamat = findViewById(R.id.profile_alamat);

        btnUpdate = findViewById(R.id.btnUpdate);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = profileEmail.getText().toString().trim();
                getDataFromDatabase(email);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = profileEmail.getText().toString().trim();
                String nama = profileNama.getText().toString().trim();
                String noTelpon = profileNoTelpon.getText().toString().trim();
                String alamat = profileAlamat.getText().toString().trim();
                updateData(email, nama, noTelpon, alamat);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_booking:
                    startActivity(new Intent(getApplicationContext(), BookingActivity1.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;

                case R.id.bottom_order:
                    startActivity(new Intent(getApplicationContext(), booking_test.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_profile:
                    return true;

            }
            return false;
        });
    }

    private void getDataFromDatabase(String email) {
        // URL endpoint untuk mengambil data dari database
        String url = "http://192.168.1.20/barbershopLaravel/public/api/search?email=" + email;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                JSONArray data = response.getJSONArray("data");
                                if (data.length() > 0) {
                                    JSONObject userData = data.getJSONObject(0);
                                    // Mendapatkan data dari response JSON
                                    String nama = userData.getString("nama");
                                    String noTelpon = userData.getString("no_telepon");
                                    String alamat = userData.getString("alamat");

                                    // Menampilkan data pada masing-masing EditText
                                    profileNama.setText(nama);
                                    profileNoTelpon.setText(noTelpon);
                                    profileAlamat.setText(alamat);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Data not found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                String message = response.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Menambahkan request ke antrian request Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


    private void updateData(String email, String nama, String noTelpon, String alamat) {
        // URL endpoint untuk mengupdate data ke database
        String url = "http://192.168.1.20/barbershopLaravel/public/api/updateUser";

        // Membuat objek JSON untuk mengirim data
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("email", email);
            requestData.put("nama", nama);
            requestData.put("no_telepon", noTelpon);
            requestData.put("alamat", alamat);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            String message = response.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(), "Error updating data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Menambahkan request ke antrian request Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


}
