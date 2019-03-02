package com.example.anonymous.CikgoodPengajar.rests;

import com.example.anonymous.CikgoodPengajar.models.SemuaProvinsi;
import com.example.anonymous.CikgoodPengajar.response.ModelKabupaten;
import com.example.anonymous.CikgoodPengajar.response.ModelKecamatan;
import com.example.anonymous.CikgoodPengajar.response.ModelProvinsi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProvInterface {
   @GET("provinsi")
   Call<ModelProvinsi> getProvinsi();
   @GET("provinsi/{idprovinsi}/kabupaten")
   Call<ModelKabupaten> getKabupaten(@Path("idprovinsi") String idProvinsi);
   @GET("provinsi/kabupaten/{idkabupaten}/kecamatan")
   Call<ModelKecamatan> getKecamatan(@Path("idkabupaten") String idKabupaten);
}
