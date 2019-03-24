package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.GuruIdentitas;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseIdentitas {
    @SerializedName("master")
    @Expose
    private GuruIdentitas master;

    public GuruIdentitas getMaster() {
        return master;
    }

    public void setMaster(GuruIdentitas master) {
        this.master = master;
    }
}
