package com.project.barbershop.servis;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.barbershop.profile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ServerRequestRegistrasi {
    private static final String TAG = "ServerRequestRegistrasi";
    private final Context context;
    private final RequestQueue requestQueue;

    public ServerRequestRegistrasi(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void register(String name, String email, String password,String alamat,  Response.Listener<String> successListener, Response.ErrorListener errorListener) {
        // URL endpoint untuk registrasi
        String url = "http://10.10.5.169/barbershop/public/api/register";

        // membuat objek StringRequest untuk melakukan request POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean error = jsonResponse.getBoolean("success");
                            if (error) {
                                // response dari server jika registrasi berhasil
                                Intent intent = new Intent(context, LoginActivity.class);
                                context.startActivity(intent);
                            } else {
                                // response dari server jika registrasi gagal
                                Toast.makeText(context, "Registrasi gagal", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // response dari server jika terjadi kesalahan pada request atau response dari server
                        Toast.makeText(context, "Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("alamat", alamat);
                params.put("password", password);
                return params;
            }
        };

        // menambahkan request ke dalam queue
        requestQueue.add(stringRequest);
    }
}