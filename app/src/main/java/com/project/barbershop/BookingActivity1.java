package com.project.barbershop;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BookingActivity1 extends AppCompatActivity {

    private static final String url = "http://192.168.1.5/barbershopLaravel/public/api/postBooking";

    private EditText etName, etNoTelpon, etJenisPelayanan, etHarga;
    private Button btnSubmit;
    private Button btnTglBooking;

    private Button choose;
    private Spinner spinner;
    private Spinner spinner2;
    String encodeImageString;
    private Calendar calendar;

    Bitmap bitmap;
    ImageView img;

    private Uri selectedImageUri;

    int PICK_IMAGE_REQUEST = 111;
    String imageString = ".jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_test);

        spinner = findViewById(R.id.jam_booking);


        // Buat array opsi jam
        String[] jamOptions = {"Jam 1", "Jam 2", "Jam 3"};



        //spinner jenis pelayanan
        Spinner spinnerJenisPelayanan = findViewById(R.id.spinner_jenis_pelayanan);
        ArrayAdapter<CharSequence> adapterJenisPelayanan = ArrayAdapter.createFromResource(this,
                R.array.jenis_pelayanan_options, android.R.layout.simple_spinner_item);
        adapterJenisPelayanan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenisPelayanan.setAdapter(adapterJenisPelayanan);

        spinnerJenisPelayanan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pilihan = parent.getItemAtPosition(position).toString();
                String harga = "";

                switch (pilihan) {
                    case "Gentlemen Cut":
                        harga = "Rp100.000";
                        break;
                    case "Smoothing":
                        harga = "Rp200.000";
                        break;
                    case "Black Hair Coloring":
                        harga = "Rp300.000";
                        break;
                    case "Grooming and Hair Tattoo":
                        harga = "Rp400.000";
                        break;
                    case "Hair Coloring":
                        harga = "Rp500.000";
                        break;
                }

                etHarga.setText(harga);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Tidak ada yang dipilih, tidak ada tindakan yang diambil.
            }
        });






        // Buat adapter untuk Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jamOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Atur listener untuk item yang dipilih
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedJam = jamOptions[position];
                // Lakukan tindakan sesuai dengan jam yang dipilih
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Tidak ada jam yang dipilih
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_booking);

        etName = findViewById(R.id.username);
        etNoTelpon = findViewById(R.id.no_telpon);
        etHarga = findViewById(R.id.harga);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnTglBooking = findViewById(R.id.btnTglBooking);
        choose = findViewById(R.id.choose);
        ProgressDialog progressDialog;

        btnTglBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingUser();
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
                    return true;
                case R.id.bottom_order:
                    startActivity(new Intent(getApplicationContext(), ListPemesananActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });
    }

    private void showDatePicker() {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity1.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        calendar.set(selectedYear, selectedMonth, selectedDayOfMonth);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String selectedDate = sdf.format(calendar.getTime());
                        btnTglBooking.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                encodeBitmapImage(bitmap);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImageString = Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void bookingUser() {
        final String nama = etName.getText().toString().trim();
        final String no_telpon = etNoTelpon.getText().toString().trim();
        final String jenis_pelayanan = spinner2.getSelectedItem().toString().trim();
        final String harga = etHarga.getText().toString().trim();
        final String tanggal_booking = btnTglBooking.getText().toString().trim();
        final String jam_booking = spinner.getSelectedItem().toString().trim();

        // Buat request POST ke URL
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Tanggapan dari server jika pendaftaran berhasil
                        Toast.makeText(BookingActivity1.this, "Booking Berhasil", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Tanggapan dari server jika terjadi kesalahan
                        Toast.makeText(BookingActivity1.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("nama", nama);
                params.put("no_telpon", no_telpon);
                params.put("jenis_pelayanan", jenis_pelayanan);
                params.put("harga", harga);
                params.put("tanggal_booking", tanggal_booking);
                params.put("jam_booking", jam_booking);
                params.put("upload", encodeImageString);

                return params;
            }
        };

        // Buat antrian permintaan Volley dan tambahkan permintaan ke antrian
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
