package com.example.anonymous.CikgoodPengajar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dokumen {
    @SerializedName("id_dokumen")
    @Expose
    private Integer idDokumen;
    @SerializedName("guru_id")
    @Expose
    private Integer guruId;
    @SerializedName("jenis_dokumen")
    @Expose
    private String jenisDokumen;
    @SerializedName("nama_dokumen")
    @Expose
    private String namaDokumen;
    @SerializedName("tahun")
    @Expose
    private String tahun;
    @SerializedName("photo_dokumen")
    @Expose
    private String photoDokumen;

    public Integer getIdDokumen() {
        return idDokumen;
    }

    public void setIdDokumen(Integer idDokumen) {
        this.idDokumen = idDokumen;
    }

    public Integer getGuruId() {
        return guruId;
    }

    public void setGuruId(Integer guruId) {
        this.guruId = guruId;
    }

    public String getJenisDokumen() {
        return jenisDokumen;
    }

    public void setJenisDokumen(String jenisDokumen) {
        this.jenisDokumen = jenisDokumen;
    }

    public String getNamaDokumen() {
        return namaDokumen;
    }

    public void setNamaDokumen(String namaDokumen) {
        this.namaDokumen = namaDokumen;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getPhotoDokumen() {
        return photoDokumen;
    }

    public void setPhotoDokumen(String photoDokumen) {
        this.photoDokumen = photoDokumen;
    }
}
