package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.DataMatpel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDataMatpel {
    @SerializedName("master")
    @Expose
    private List<DataMatpel> master = null;

    public List<DataMatpel> getMaster() {
        return master;
    }

    public void setMaster(List<DataMatpel> master) {
        this.master = master;
    }
}
