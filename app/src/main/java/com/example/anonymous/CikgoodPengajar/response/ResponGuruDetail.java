package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.GuruProfil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponGuruDetail {
    @SerializedName("master")
    @Expose
    private GuruProfil master;

    public GuruProfil getMaster() {
        return master;
    }

    public void setMaster(GuruProfil master) {
        this.master = master;
    }
}
