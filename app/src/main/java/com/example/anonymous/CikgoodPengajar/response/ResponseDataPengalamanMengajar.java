package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.PengalamanMengajar;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDataPengalamanMengajar {
    @SerializedName("master")
    @Expose
    private List<PengalamanMengajar> master = null;

    public List<PengalamanMengajar> getMaster() {
        return master;
    }

    public void setMaster(List<PengalamanMengajar> master) {
        this.master = master;
    }
}
