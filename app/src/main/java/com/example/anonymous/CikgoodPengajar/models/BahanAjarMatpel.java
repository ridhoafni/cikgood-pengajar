package com.example.anonymous.CikgoodPengajar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BahanAjarMatpel {

    @SerializedName("id_guru_bahan_ajar_matpel")
    @Expose
    private Integer idGuruBahanAjarMatpel;
    @SerializedName("guru_id")
    @Expose
    private Integer guruId;
    @SerializedName("matpel_id")
    @Expose
    private Integer matpelId;
    @SerializedName("tarif")
    @Expose
    private Integer tarif;

    public Integer getIdGuruBahanAjarMatpel() {
        return idGuruBahanAjarMatpel;
    }

    public void setIdGuruBahanAjarMatpel(Integer idGuruBahanAjarMatpel) {
        this.idGuruBahanAjarMatpel = idGuruBahanAjarMatpel;
    }

    public Integer getGuruId() {
        return guruId;
    }

    public void setGuruId(Integer guruId) {
        this.guruId = guruId;
    }

    public Integer getMatpelId() {
        return matpelId;
    }

    public void setMatpelId(Integer matpelId) {
        this.matpelId = matpelId;
    }

    public Integer getTarif() {
        return tarif;
    }

    public void setTarif(Integer tarif) {
        this.tarif = tarif;
    }
}
