package com.example.anonymous.CikgoodPengajar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PengalamanMengajar {
    @SerializedName("id_pengalaman_mengajar")
    @Expose
    private Integer idPengalamanMengajar;
    @SerializedName("guru_id")
    @Expose
    private Integer guruId;
    @SerializedName("pengalaman")
    @Expose
    private String pengalaman;
    @SerializedName("tgl_masuk")
    @Expose
    private String tglMasuk;
    @SerializedName("tgl_selesai")
    @Expose
    private String tglSelesai;

    public Integer getIdPengalamanMengajar() {
        return idPengalamanMengajar;
    }

    public void setIdPengalamanMengajar(Integer idPengalamanMengajar) {
        this.idPengalamanMengajar = idPengalamanMengajar;
    }

    public Integer getGuruId() {
        return guruId;
    }

    public void setGuruId(Integer guruId) {
        this.guruId = guruId;
    }

    public String getPengalaman() {
        return pengalaman;
    }

    public void setPengalaman(String pengalaman) {
        this.pengalaman = pengalaman;
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
