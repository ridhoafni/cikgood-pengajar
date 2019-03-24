package com.example.anonymous.CikgoodPengajar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuruBahanAjarMatpel {
    @SerializedName("id_guru_bahan_ajar_matpel")
    @Expose
    private String idGuruBahanAjarMatpel;
    @SerializedName("guru_id")
    @Expose
    private String guruId;
    @SerializedName("matpel_id")
    @Expose
    private String matpelId;
    @SerializedName("tarif")
    @Expose
    private String tarif;
    @SerializedName("id_matpel")
    @Expose
    private String idMatpel;
    @SerializedName("tingkatan")
    @Expose
    private String tingkatan;
    @SerializedName("matpel")
    @Expose
    private String matpel;
    @SerializedName("matpel_detail")
    @Expose
    private String matpelDetail;

    public String getIdGuruBahanAjarMatpel() {
        return idGuruBahanAjarMatpel;
    }

    public void setIdGuruBahanAjarMatpel(String idGuruBahanAjarMatpel) {
        this.idGuruBahanAjarMatpel = idGuruBahanAjarMatpel;
    }

    public String getGuruId() {
        return guruId;
    }

    public void setGuruId(String guruId) {
        this.guruId = guruId;
    }

    public String getMatpelId() {
        return matpelId;
    }

    public void setMatpelId(String matpelId) {
        this.matpelId = matpelId;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public String getIdMatpel() {
        return idMatpel;
    }

    public void setIdMatpel(String idMatpel) {
        this.idMatpel = idMatpel;
    }

    public String getTingkatan() {
        return tingkatan;
    }

    public void setTingkatan(String tingkatan) {
        this.tingkatan = tingkatan;
    }

    public String getMatpel() {
        return matpel;
    }

    public void setMatpel(String matpel) {
        this.matpel = matpel;
    }

    public String getMatpelDetail() {
        return matpelDetail;
    }

    public void setMatpelDetail(String matpelDetail) {
        this.matpelDetail = matpelDetail;
    }
}
