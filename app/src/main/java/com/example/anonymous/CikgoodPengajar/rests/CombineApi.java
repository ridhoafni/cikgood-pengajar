package com.example.anonymous.CikgoodPengajar.rests;

public class CombineApi {
    public static final String BASE_URL_PROV = "https://dev.farizdotid.com/api/daerahindonesia/";

    public static ProvInterface getApiProv(){
        return ApiClient.getApiProv(BASE_URL_PROV).create(ProvInterface.class);
    }
}
