package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.BahanAjarMatpel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBahanAjarMatpel {
    @SerializedName("master")
    @Expose
    private List<BahanAjarMatpel> master = null;

    public List<BahanAjarMatpel> getMaster() {
        return master;
    }

    public void setMaster(List<BahanAjarMatpel> master) {
        this.master = master;
    }
}
