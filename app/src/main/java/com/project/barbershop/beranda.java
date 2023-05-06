package com.project.barbershop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class beranda extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_beranda);

    final SharedPreferences sharedPreferences = getSharedPreferences("session_login", MODE_PRIVATE);

    TextView welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText("Halo Selamat"+ sharedPreferences.getString("nama",null));
    Button logout = (Button) findViewById(R.id.logout);



        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }

            public void onCLick (View v){
        sharedPreferences.edit().clear();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "anda telah logout", Toast.LENGTH_SHORT).show();
    }
    });
}
}
