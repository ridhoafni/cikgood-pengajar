package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.GuruJadwal;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGuruJadwal {
    @SerializedName("master")
    @Expose
    private List<GuruJadwal> master = null;

    public List<GuruJadwal> getMaster() {
        return master;
    }

    public void setMaster(List<GuruJadwal> master) {
        this.master = master;
    }
}
