package com.example.anonymous.CikgoodPengajar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuruJadwal {

    @SerializedName("id_jadwal")
    @Expose
    private Integer idJadwal;
    @SerializedName("guru_id")
    @Expose
    private Integer guruId;
    @SerializedName("hari")
    @Expose
    private String hari;
    @SerializedName("jam")
    @Expose
    private String jam;

    public Integer getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(Integer idJadwal) {
        this.idJadwal = idJadwal;
    }

    public Integer getGuruId() {
        return guruId;
    }

    public void setGuruId(Integer guruId) {
        this.guruId = guruId;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }
}
