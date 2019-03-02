package com.example.anonymous.CikgoodPengajar.utils;

import com.example.anonymous.CikgoodPengajar.config.ServerConfig;
import com.example.anonymous.CikgoodPengajar.rests.ApiClient;
import com.example.anonymous.CikgoodPengajar.rests.ApiInterface;

public class UtilsApi {
//    public static final String BASE_URL_API = "http://10.0.2.2/mahasiswa/";
    private static final String BASE_URL = ServerConfig.API_ENDPOINT;

    // Mendeklarasikan Interface BaseApiService
    public static ApiInterface getAPIService(){
        return ApiClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
