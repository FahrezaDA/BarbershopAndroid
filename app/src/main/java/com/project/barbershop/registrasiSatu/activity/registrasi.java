package com.project.barbershop.registrasiSatu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.project.barbershop.R;
import com.project.barbershop.registrasiSatu.Response.R_registrasi;
import com.project.barbershop.registrasiSatu.interfaces.I_registrasi;
import com.project.barbershop.registrasiSatu.Servis.servis;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class registrasi extends AppCompatActivity {
    private EditText r_username,r_password;
    private Button btnRegistrasi;
    String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        r_username=findViewById(R.id.r_username);
        r_password=findViewById(R.id.r_password);
        btnRegistrasi = findViewById(R.id.btnRegistrasi);

        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view){
                username = r_username.getText().toString().trim();
                password = r_password.getText().toString().trim();

                if(username.equals("")){
                    r_username.setError("username tidak boleh kosong");
                }
                else if(password.equals("")){
                    r_password.setError("password tidak boleh kosong");
                }
                else{
                    acess_registrasi();
                    Toast.makeText(getApplicationContext(),"berhasil", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    private void acess_registrasi() {
        I_registrasi Ires_regis = servis.koneksiServer().create(I_registrasi.class);
        retrofit2.Call<R_registrasi> callResRegis = Ires_regis.registrasi(username,password);
        callResRegis.enqueue(new Callback<R_registrasi>(){
            @Override
            public void onResponse(retrofit2.Call<R_registrasi> call, Response<R_registrasi> response){
                String msg = response.body().getMessage();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<R_registrasi> call, Throwable t){
                Toast.makeText(getApplicationContext(), "server bermasalah", Toast.LENGTH_SHORT).show();
            }

        });
    }
}





