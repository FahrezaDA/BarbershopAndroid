package com.project.barbershop.registrasi.Servis;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class servis {
    public static String linkApi ="";
    public static Retrofit retro;
    public  static Retrofit koneksiServer(){
        if(retro == null){
            retro = new Retrofit.Builder()
                    .baseUrl(linkApi)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retro;
    }
}
