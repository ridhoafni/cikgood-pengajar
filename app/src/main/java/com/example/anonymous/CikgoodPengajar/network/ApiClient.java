package com.example.anonymous.CikgoodPengajar.network;

import com.example.anonymous.CikgoodPengajar.LoggingInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    //192.168.100.95
    //192.168.100.15
    public static final String URL = "http://192.168.100.95/android_maps/";
    public static Retrofit RETROFIT = null;

    public static Retrofit getClient(){

        if (RETROFIT==null){
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new LoggingInterceptor())
                    .build();

            RETROFIT = new Retrofit.Builder()
                    .baseUrl(URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return RETROFIT;
    }
}
