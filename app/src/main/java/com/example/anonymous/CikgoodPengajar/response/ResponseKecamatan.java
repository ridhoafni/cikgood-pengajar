package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.Kecamatan;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseKecamatan {
    @SerializedName("master")
    @Expose
    private List<Kecamatan> master = null;

    public List<Kecamatan> getMaster() {
        return master;
    }

    public void setMaster(List<Kecamatan> master) {
        this.master = master;
    }
}
