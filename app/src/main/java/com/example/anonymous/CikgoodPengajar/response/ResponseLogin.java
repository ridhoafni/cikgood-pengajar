package com.example.anonymous.CikgoodPengajar.response;

import com.example.anonymous.CikgoodPengajar.models.GuruProfil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private GuruProfil data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GuruProfil getData() {
        return data;
    }

    public void setData(GuruProfil data) {
        this.data = data;
    }
}
