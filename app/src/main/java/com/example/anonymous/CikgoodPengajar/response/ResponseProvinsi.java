package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.Provinsi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseProvinsi {
    @SerializedName("master")
    @Expose
    private List<Provinsi> master = null;

    public List<Provinsi> getMaster() {
        return master;
    }

    public void setMaster(List<Provinsi> master) {
        this.master = master;
    }
}
