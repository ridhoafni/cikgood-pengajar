package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.MasterMatpel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMasterMatpel {
    @SerializedName("master")
    @Expose
    private List<MasterMatpel> master = null;

    public List<MasterMatpel> getMaster() {
        return master;
    }

    public void setMaster(List<MasterMatpel> master) {
        this.master = master;
    }
}
