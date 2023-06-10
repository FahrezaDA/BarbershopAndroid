package com.project.barbershop;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {
    Button btnSatu, btnDua, btnTiga;

    private Button btnTglBookings, btnSearch;
    private TextView tvStatus1, tvStatus2, tvStatus3;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private RequestQueue requestQueue;
    private String apiUrl = "http://192.168.1.20/barbershopLaravel/public/api/searchBooking";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        btnSatu = (Button) findViewById(R.id.btnSatu);
        btnDua = (Button) findViewById(R.id.btnDua);
        btnTiga = (Button) findViewById(R.id.btnTiga);

        btnSatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BukaActSatu = new Intent(getApplicationContext(), BookingActivity1.class);
                startActivity(BukaActSatu);
            }
        });

        btnDua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BukaActDua = new Intent(getApplicationContext(), BookingActivity1.class);
                startActivity(BukaActDua);
            }
        });

        btnTiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BukaActTiga = new Intent(getApplicationContext(), BookingActivity1.class);
                startActivity(BukaActTiga);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_booking);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        requestQueue = Volley.newRequestQueue(this);

        btnTglBookings = findViewById(R.id.btnTglBookings);
        btnSearch = findViewById(R.id.btnSearch);
        tvStatus1 = findViewById(R.id.tv_status1);
        tvStatus2 = findViewById(R.id.tv_status2);
        tvStatus3 = findViewById(R.id.tv_status3);

        btnTglBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_order:
                    startActivity(new Intent(getApplicationContext(), BookingActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_booking:
                    return true;
                case R.id.bottom_profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity1.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;


            }
            return false;
        });

    }


    private void showDatePicker() {
        final Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(year, monthOfYear, dayOfMonth);
                String selectedDate = dateFormatter.format(selectedCalendar.getTime());
                btnTglBookings.setText(selectedDate);
            }
        }, newCalendar.get(Calendar.DAY_OF_MONTH), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.YEAR));

        datePickerDialog.show();
    }

    private void performSearch() {
        String selectedDate = btnTglBookings.getText().toString();

        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = apiUrl + "?tanggal_booking=" + selectedDate;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Ambil status dari respons JSON
                            String status1 = response.optString("status1");
                            String status2 = response.optString("status2");
                            String status3 = response.optString("status3");

                            // Tampilkan status pada TextView yang sesuai
                            tvStatus1.setText(status1);
                            tvStatus2.setText(status2);
                            tvStatus3.setText(status3);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BookingActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
    }
}
