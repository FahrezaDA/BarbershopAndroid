package com.project.barbershop.listBooking;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.barbershop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PesananActivity extends AppCompatActivity {

    private ListView listViewPesanan;
    private PesananAdapter pesananAdapter;
    private List<Pesanan> pesananList;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pemesanan);

        listViewPesanan = findViewById(R.id.listPesanan);
        pesananList = new ArrayList<>();
        pesananAdapter = new PesananAdapter(this, pesananList);
        listViewPesanan.setAdapter(pesananAdapter);

        fetchDataFromDatabase();
    }

    private void fetchDataFromDatabase() {
        String url = "http://10.10.5.56/barbershopLaravel/routes/api/getBooking"; // Ganti dengan URL PHP di server Anda

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String id = jsonObject.getString("id_booking");
                                String pelayanan = jsonObject.getString("jenis_pelayanan");
                                String tanggal = jsonObject.getString("tanggal_booking");
                                String jam = jsonObject.getString("jam_booking");
                                String status = jsonObject.getString("stats");

                                Pesanan pesanan = new Pesanan(id, pelayanan, tanggal, jam, status);
                                pesananList.add(pesanan);
                            }

                            pesananAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PesananActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
