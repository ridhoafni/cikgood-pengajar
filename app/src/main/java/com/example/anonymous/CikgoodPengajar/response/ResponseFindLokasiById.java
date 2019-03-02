package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.Lokasi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFindLokasiById {
    @SerializedName("master")
    @Expose
    private List<Lokasi> master = null;

    public List<Lokasi> getMaster() {
        return master;
    }

    public void setMaster(List<Lokasi> master) {
        this.master = master;
    }
}
