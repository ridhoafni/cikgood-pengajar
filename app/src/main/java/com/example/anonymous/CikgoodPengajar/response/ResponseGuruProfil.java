package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.GuruProfil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGuruProfil {
    @SerializedName("master")
    @Expose
    private List<GuruProfil> master = null;

    public List<GuruProfil> getMaster() {
        return master;
    }

    public void setMaster(List<GuruProfil> master) {
        this.master = master;
    }
}
