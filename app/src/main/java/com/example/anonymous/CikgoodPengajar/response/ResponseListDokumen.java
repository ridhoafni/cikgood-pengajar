package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.Dokumen;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseListDokumen {
    @SerializedName("master")
    @Expose
    private List<Dokumen> master = null;

    public List<Dokumen> getMaster() {
        return master;
    }

    public void setMaster(List<Dokumen> master) {
        this.master = master;
    }
}
