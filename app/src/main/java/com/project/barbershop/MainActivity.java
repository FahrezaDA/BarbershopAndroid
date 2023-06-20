package com.project.barbershop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.barbershop.servis.SharedPreferenceManager;

public class MainActivity extends AppCompatActivity {
    private TextView tvMain;
    private SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi SharedPreferenceManager
        sharedPreferenceManager = new SharedPreferenceManager(getApplicationContext());

        // Mendapatkan nama dari SharedPreferenceManager
        String nama = sharedPreferenceManager.getUsername();

        // Inisialisasi TextView dan setel nilai nama
        tvMain = findViewById(R.id.tvmain);
        tvMain.setText(nama);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    return true;
                case R.id.bottom_booking:
                    startActivity(new Intent(getApplicationContext(), BookingActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_order:
                    startActivity(new Intent(getApplicationContext(), BookingActivity1.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
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
}
