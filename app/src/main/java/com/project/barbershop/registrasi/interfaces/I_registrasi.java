package com.project.barbershop.registrasi.interfaces;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Field;
import androidx.fragment.app.Fragment;



import com.project.barbershop.registrasi.Response.R_registrasi;


public interface I_registrasi {
    @POST("authentication/register")
    @FormUrlEncoded
    Call<R_registrasi> registrasi(@Field("username") String username,
                                  @Field("password") String password);

    Call register(String username, String password);
}
