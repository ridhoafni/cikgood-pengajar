package com.example.anonymous.CikgoodPengajar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataMatpel {
    @SerializedName("id_matpel")
    @Expose
    private Integer idMatpel;
    @SerializedName("tingkatan")
    @Expose
    private String tingkatan;
    @SerializedName("matpel")
    @Expose
    private String matpel;
    @SerializedName("matpel_detail")
    @Expose
    private String matpelDetail;

    public Integer getIdMatpel() {
        return idMatpel;
    }

    public void setIdMatpel(Integer idMatpel) {
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
