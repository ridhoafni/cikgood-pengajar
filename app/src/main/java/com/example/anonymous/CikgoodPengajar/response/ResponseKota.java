package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.Kota;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseKota {
    @SerializedName("master")
    @Expose
    private List<Kota> master = null;

    public List<Kota> getMaster() {
        return master;
    }

    public void setMaster(List<Kota> master) {
        this.master = master;
    }
}
