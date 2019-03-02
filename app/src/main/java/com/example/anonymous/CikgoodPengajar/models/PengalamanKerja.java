package com.example.anonymous.CikgoodPengajar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PengalamanKerja {
    @SerializedName("id_pengalaman_kerja")
    @Expose
    private Integer idPengalamanKerja;
    @SerializedName("guru_id")
    @Expose
    private Integer guruId;
    @SerializedName("jabatan")
    @Expose
    private String jabatan;
    @SerializedName("perusahaan")
    @Expose
    private String perusahaan;
    @SerializedName("tgl_masuk")
    @Expose
    private String tglMasuk;
    @SerializedName("tgl_selesai")
    @Expose
    private String tglSelesai;

    public Integer getIdPengalamanKerja() {
        return idPengalamanKerja;
    }

    public void setIdPengalamanKerja(Integer idPengalamanKerja) {
        this.idPengalamanKerja = idPengalamanKerja;
    }

    public Integer getGuruId() {
        return guruId;
    }

    public void setGuruId(Integer guruId) {
        this.guruId = guruId;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
    }

    public String getTglMasuk() {
        return tglMasuk;
    }

    public void setTglMasuk(String tglMasuk) {
        this.tglMasuk = tglMasuk;
    }

    public String getTglSelesai() {
        return tglSelesai;
    }

    public void setTglSelesai(String tglSelesai) {
        this.tglSelesai = tglSelesai;
    }

}
