package com.project.barbershop.registrasiSatu.interfaces;


import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Field;


import com.project.barbershop.registrasiSatu.Response.R_registrasi;
public interface I_registrasi {
    @POST("authentication/register")
    @FormUrlEncoded
    Call<R_registrasi> registrasi(@Field("username") String username,
                                  @Field("password") String password);

    Call register(String username, String password);
}
