package com.example.anonymous.CikgoodPengajar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RiwayatPendidikan {
    @SerializedName("id_riwayat_pendidikan")
    @Expose
    private Integer idRiwayatPendidikan;
    @SerializedName("guru_id")
    @Expose
    private Integer guruId;
    @SerializedName("gelar")
    @Expose
    private String gelar;
    @SerializedName("jenis_institusi")
    @Expose
    private String jenisInstitusi;
    @SerializedName("nama_institusi")
    @Expose
    private String namaInstitusi;
    @SerializedName("jurusan")
    @Expose
    private String jurusan;
    @SerializedName("tahun_masuk")
    @Expose
    private String tahunMasuk;
    @SerializedName("tahun_selesai")
    @Expose
    private String tahunSelesai;
    @SerializedName("photo_ijazah")
    @Expose
    private String photoIjazah;

    public Integer getIdRiwayatPendidikan() {
        return idRiwayatPendidikan;
    }

    public void setIdRiwayatPendidikan(Integer idRiwayatPendidikan) {
        this.idRiwayatPendidikan = idRiwayatPendidikan;
    }

    public Integer getGuruId() {
        return guruId;
    }

    public void setGuruId(Integer guruId) {
        this.guruId = guruId;
    }

    public String getGelar() {
        return gelar;
    }

    public void setGelar(String gelar) {
        this.gelar = gelar;
    }

    public String getJenisInstitusi() {
        return jenisInstitusi;
    }

    public void setJenisInstitusi(String jenisInstitusi) {
        this.jenisInstitusi = jenisInstitusi;
    }

    public String getNamaInstitusi() {
        return namaInstitusi;
    }

    public void setNamaInstitusi(String namaInstitusi) {
        this.namaInstitusi = namaInstitusi;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getTahunMasuk() {
        return tahunMasuk;
    }

    public void setTahunMasuk(String tahunMasuk) {
        this.tahunMasuk = tahunMasuk;
    }

    public String getTahunSelesai() {
        return tahunSelesai;
    }

    public void setTahunSelesai(String tahunSelesai) {
        this.tahunSelesai = tahunSelesai;
    }

    public String getPhotoIjazah() {
        return photoIjazah;
    }

    public void setPhotoIjazah(String photoIjazah) {
        this.photoIjazah = photoIjazah;
    }
}
