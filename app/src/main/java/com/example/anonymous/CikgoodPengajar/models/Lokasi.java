package com.example.anonymous.CikgoodPengajar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lokasi {
    @SerializedName("id_bahan_ajar_lokasi")
    @Expose
    private Integer idBahanAjarLokasi;
    @SerializedName("guru_id")
    @Expose
    private Integer guruId;
    @SerializedName("provinsi")
    @Expose
    private String provinsi;
    @SerializedName("kota")
    @Expose
    private String kota;
    @SerializedName("kecamatan")
    @Expose
    private String kecamatan;

    public Integer getIdBahanAjarLokasi() {
        return idBahanAjarLokasi;
    }

    public void setIdBahanAjarLokasi(Integer idBahanAjarLokasi) {
        this.idBahanAjarLokasi = idBahanAjarLokasi;
    }

    public Integer getGuruId() {
        return guruId;
    }

    public void setGuruId(Integer guruId) {
        this.guruId = guruId;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }
}
