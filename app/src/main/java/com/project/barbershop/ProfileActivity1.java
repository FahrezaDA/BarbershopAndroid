package com.project.barbershop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.barbershop.servis.SharedPreferenceManager;

public class ProfileActivity1 extends AppCompatActivity {
    private TextView tvUsername;
    private TextView tvEmail;
    private TextView tvAlamat;
    private TextView tvNoTelepon;
    private Button btnUpdate;

    private SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_booking);

        tvUsername = findViewById(R.id.profile_nama);
        tvEmail = findViewById(R.id.profile_email);
        tvAlamat = findViewById(R.id.profile_alamat);
        tvNoTelepon = findViewById(R.id.profile_noTelepon);
        btnUpdate = findViewById(R.id.btnUpdate);

        sharedPreferenceManager = new SharedPreferenceManager(this);

        String username = sharedPreferenceManager.getUsername();
        String email = sharedPreferenceManager.getEmail();
        String alamat = sharedPreferenceManager.getAlamat();
        String noTelepon = sharedPreferenceManager.getNoTelepon();

        tvUsername.setText("  " + username);
        tvEmail.setText("  "+email);
        tvAlamat.setText("  "+alamat);
        tvNoTelepon.setText(" " + noTelepon);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
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



    private void updateProfile() {
        String newUsername = tvUsername.getText().toString().trim();
        String newEmail = tvEmail.getText().toString().trim();
        String newAlamat = tvAlamat.getText().toString().trim();
        String newNoTelepon = tvNoTelepon.getText().toString().trim();

        // Lakukan pembaruan data di SharedPreference atau penyimpanan data lainnya
        sharedPreferenceManager.setUsername(newUsername);
        sharedPreferenceManager.setEmail(newEmail);
        sharedPreferenceManager.setAlamat(newAlamat);
        sharedPreferenceManager.setNoTelepon(newNoTelepon);

        // Tampilkan pesan atau aksi setelah pembaruan data
        Toast.makeText(ProfileActivity1.this, "Update berhasil", Toast.LENGTH_SHORT).show();
        // ...

        // Contoh: Refresh tampilan dengan data yang baru saja diperbarui
        tvUsername.setText("  "+newUsername);
        tvEmail.setText("  "+ newEmail);
        tvAlamat.setText("  "+newAlamat);
        tvNoTelepon.setText("  "+newNoTelepon);
    }
}
