package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.Kabupaten;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelKabupaten {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("kabupatens")
    @Expose
    private List<Kabupaten> kabupatens = null;

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

    public List<Kabupaten> getKabupatens() {
        return kabupatens;
    }

    public void setKabupatens(List<Kabupaten> kabupatens) {
        this.kabupatens = kabupatens;
    }
}
