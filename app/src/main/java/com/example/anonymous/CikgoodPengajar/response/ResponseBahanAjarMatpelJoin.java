package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.BahanAjarMatpelJoin;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBahanAjarMatpelJoin {

    @SerializedName("master")
    @Expose
    private List<BahanAjarMatpelJoin> master = null;

    public List<BahanAjarMatpelJoin> getMaster() {
        return master;
    }

    public void setMaster(List<BahanAjarMatpelJoin> master) {
        this.master = master;
    }

}
