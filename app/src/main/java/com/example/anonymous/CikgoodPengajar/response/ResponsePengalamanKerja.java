package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.PengalamanKerja;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePengalamanKerja {
    @SerializedName("master")
    @Expose
    private List<PengalamanKerja> master = null;

    public List<PengalamanKerja> getMaster() {
        return master;
    }

    public void setMaster(List<PengalamanKerja> master) {
        this.master = master;
    }
}
