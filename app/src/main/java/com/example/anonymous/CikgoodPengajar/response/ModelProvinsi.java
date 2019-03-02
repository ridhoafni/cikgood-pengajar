package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.SemuaProvinsi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelProvinsi {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("semuaprovinsi")
    @Expose
    private List<SemuaProvinsi> semuaprovinsi = null;

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

    public List<SemuaProvinsi> getSemuaprovinsi() {
        return semuaprovinsi;
    }

    public void setSemuaprovinsi(List<SemuaProvinsi> semuaprovinsi) {
        this.semuaprovinsi = semuaprovinsi;
    }
}
