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
    @Override
    protected void onCreate(Bundle savedInstanceState){
        SharedPreferences session=getSharedPreferences("session_login", MODE_PRIVATE);

        if(session.contains("username")){
            finish();
            intent intent=new Intent(getApplicationContext(), Beranda.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

        login=(Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View v){
                login();

            }
        });
    }
void login(){
        if(username.getText().length()<1)
        {
            username.setBackgroundResource(R.drawable.bg_gradasi);
        }
        if(password.getText().length()<1){
            password.setBackgroundResource(R.drawable.bg_gradasi);
        }
        else
        {
        kirim_data();
        }
}
void kirim_data(){
        String url="";
        StringRequest respon=new StringRequest{
            Request.Method.POST,url,
        new Response.Listener<String>(){
                @Override
            public void onResponse(String response){
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        String status=jsonObject.getString("status");
                        if(status.equals("sukses")){
                            String nama=jsonObject.getString("nama");
                            session.putString("username", username.getText().toString());
                            session.putString("password", password.getText().toString());
                            session.putString("nama", nama);
                            Intent intent=new Intent(getApplicationContext(), Beranda.class);
                            startActivity(intent);
                        }
                        else if(status.equals("gagal")){
                            Toast.makeText(getApplicationContext(),"username atau password" + "tidak benar",Toast.LENGTH_SHORT).show()
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }

                }
        },
        new Response.ErrorListener(){
                @Override
            public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_SHORT).show();
                }
        }
    };
    RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
    requestQueue.add()
}
void cek_form(final EditText editText){
   editText.addTextChangedListener(new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
           
       }

       @Override
       public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

       }

       @Override
       public void afterTextChanged(Editable editable) {

       }
   });
}
}

