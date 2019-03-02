package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.Kecamatan;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelKecamatan {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("kecamatans")
    @Expose
    private List<Kecamatan> kecamatans = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Kecamatan> getKecamatans() {
        return kecamatans;
    }

    public void setKecamatans(List<Kecamatan> kecamatans) {
        this.kecamatans = kecamatans;
    }
}
