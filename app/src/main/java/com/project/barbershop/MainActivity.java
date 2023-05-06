package com.project.barbershop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button login;
    SharedPreferences session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = getSharedPreferences("session_login", MODE_PRIVATE);

        if(session.contains("username")){
            finish();
            Intent intent=new Intent(getApplicationContext(), beranda.class);
            startActivity(intent);
        }

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // panggil method cek_form untuk setiap EditText
        cek_form(username);
        cek_form(password);
    }

    void login() {
        if(username.getText().length() < 1) {
            username.setBackgroundResource(R.drawable.bg_gradasi);
        }
        if(password.getText().length() < 1) {
            password.setBackgroundResource(R.drawable.bg_gradasi);
        }
        else {
            kirim_data();
        }
    }

    void kirim_data() {
        String url = "";
        StringRequest respon = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if(status.equals("sukses")) {
                                String nama = jsonObject.getString("nama");
                                SharedPreferences.Editor editor = session.edit();
                                editor.putString("username", username.getText().toString());
                                editor.putString("password", password.getText().toString());
                                editor.putString("nama", nama);
                                editor.apply();
                                Intent intent = new Intent(getApplicationContext(), beranda.class);
                                startActivity(intent);
                            }
                            else if(status.equals("gagal")) {
                                Toast.makeText(getApplicationContext(), "username atau password tidak benar", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(respon);
    }

    void cek_form(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // hapus background jika terdapat input
                if(editable.length() > 0) {
                    editText.setBackgroundResource(R.drawable.bg_gradasi);
                }
            }
        });
    }
}
