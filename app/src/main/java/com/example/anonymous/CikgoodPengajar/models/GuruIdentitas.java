package com.example.anonymous.CikgoodPengajar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuruIdentitas {
    @SerializedName("id_identitas")
    @Expose
    private Integer idIdentitas;
    @SerializedName("guru_id")
    @Expose
    private Integer guruId;
    @SerializedName("nomor_ktp")
    @Expose
    private String nomorKtp;
    @SerializedName("photo_ktp")
    @Expose
    private String photoKtp;
    @SerializedName("npwp")
    @Expose
    private String npwp;
    @SerializedName("photo_npwp")
    @Expose
    private String photoNpwp;
    @SerializedName("nama_bank")
    @Expose
    private String namaBank;
    @SerializedName("nomor_rekening")
    @Expose
    private String nomorRekening;
    @SerializedName("nama_pemilik_rekening")
    @Expose
    private String namaPemilikRekening;

    public Integer getIdIdentitas() {
        return idIdentitas;
    }

    public void setIdIdentitas(Integer idIdentitas) {
        this.idIdentitas = idIdentitas;
    }

    public Integer getGuruId() {
        return guruId;
    }

    public void setGuruId(Integer guruId) {
        this.guruId = guruId;
    }

    public String getNomorKtp() {
        return nomorKtp;
    }

    public void setNomorKtp(String nomorKtp) {
        this.nomorKtp = nomorKtp;
    }

    public String getPhotoKtp() {
        return photoKtp;
    }

    public void setPhotoKtp(String photoKtp) {
        this.photoKtp = photoKtp;
    }

    public String getNpwp() {
        return npwp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    public String getPhotoNpwp() {
        return photoNpwp;
    }

    public void setPhotoNpwp(String photoNpwp) {
        this.photoNpwp = photoNpwp;
    }

    public String getNamaBank() {
        return namaBank;
    }

    public void setNamaBank(String namaBank) {
        this.namaBank = namaBank;
    }

    public String getNomorRekening() {
        return nomorRekening;
    }

    public void setNomorRekening(String nomorRekening) {
        this.nomorRekening = nomorRekening;
    }

    public String getNamaPemilikRekening() {
        return namaPemilikRekening;
    }

    public void setNamaPemilikRekening(String namaPemilikRekening) {
        this.namaPemilikRekening = namaPemilikRekening;
    }

}
