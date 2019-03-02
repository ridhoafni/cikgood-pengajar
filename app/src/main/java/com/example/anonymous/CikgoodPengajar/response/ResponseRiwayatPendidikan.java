package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.RiwayatPendidikan;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseRiwayatPendidikan {
    @SerializedName("master")
    @Expose
    private List<RiwayatPendidikan> master = null;

    public List<RiwayatPendidikan> getMaster() {
        return master;
    }

    public void setMaster(List<RiwayatPendidikan> master) {
        this.master = master;
    }
}
