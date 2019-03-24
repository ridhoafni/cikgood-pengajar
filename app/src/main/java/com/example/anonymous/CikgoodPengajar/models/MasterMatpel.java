package com.example.anonymous.CikgoodPengajar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterMatpel {

    @SerializedName("id_master_matpel")
    @Expose
    private Integer idMasterMatpel;
    @SerializedName("nama")
    @Expose
    private String nama;

    public Integer getIdMasterMatpel() {
        return idMasterMatpel;
    }

    public void setIdMasterMatpel(Integer idMasterMatpel) {
        this.idMasterMatpel = idMasterMatpel;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
