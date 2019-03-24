package com.example.anonymous.CikgoodPengajar.rests;

import com.example.anonymous.CikgoodPengajar.response.ResponGuruDetail;
import com.example.anonymous.CikgoodPengajar.response.ResponseCreateBahanAjarMatpel;
import com.example.anonymous.CikgoodPengajar.response.ResponseCreateGuru;
import com.example.anonymous.CikgoodPengajar.response.ResponseCreateJadwal;
import com.example.anonymous.CikgoodPengajar.response.ResponseCreatePengalamanKerja;
import com.example.anonymous.CikgoodPengajar.response.ResponseDaftarBahanAjarJoin;
import com.example.anonymous.CikgoodPengajar.response.ResponseDataMatpel;
import com.example.anonymous.CikgoodPengajar.response.ResponseDataPengalamanMengajar;
import com.example.anonymous.CikgoodPengajar.response.ResponseDeleteDokumen;
import com.example.anonymous.CikgoodPengajar.response.ResponseDeletePengalamanMengajar;
import com.example.anonymous.CikgoodPengajar.response.ResponseDeleteRiwayatPendidikan;
import com.example.anonymous.CikgoodPengajar.response.ResponseFindLokasiById;
import com.example.anonymous.CikgoodPengajar.response.ResponseGuruJadwal;
import com.example.anonymous.CikgoodPengajar.response.ResponseGuruProfil;
import com.example.anonymous.CikgoodPengajar.response.ResponseIdentitas;
import com.example.anonymous.CikgoodPengajar.response.ResponseKecamatan;
import com.example.anonymous.CikgoodPengajar.response.ResponseKota;
import com.example.anonymous.CikgoodPengajar.response.ResponseListDokumen;
import com.example.anonymous.CikgoodPengajar.response.ResponseLogin;
import com.example.anonymous.CikgoodPengajar.response.ResponseLokasi;
import com.example.anonymous.CikgoodPengajar.response.ResponseMasterMatpel;
import com.example.anonymous.CikgoodPengajar.response.ResponseMatpel;
import com.example.anonymous.CikgoodPengajar.response.ResponseCreateMurid;
import com.example.anonymous.CikgoodPengajar.response.ResponsePengalamanKerja;
import com.example.anonymous.CikgoodPengajar.response.ResponsePengalamanMengajar;
import com.example.anonymous.CikgoodPengajar.response.ResponseProvinsi;
import com.example.anonymous.CikgoodPengajar.response.ResponseRiwayatPendidikan;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("provinsi/find-all")
    Call<ResponseProvinsi> provinsiFindAll();

    @GET("kota/find-all")
    Call<ResponseKota> kotaFindAll();

    @GET("kota/find-by-id")
    Call<ResponseKota> kotaFindById(@Query("id") int id);

    @GET("kecamatan/find-all")
    Call<ResponseKecamatan> kecamatanFindAll();

    @GET("matpel/find-all")
    Call<ResponseMatpel> matpelFindAll();

    @GET("guru/find-all")
    Call<ResponseGuruProfil> guruFindAll();

    @GET("guru/find-by-id")
    Call<ResponGuruDetail> guruFindById(@Query("id") int id);

    @FormUrlEncoded
    @POST("guru/register")
    Call<ResponseCreateGuru> registerDataGuru(@Field("nama") String nama,
                                              @Field("email") String email,
                                              @Field("no_hp") String no_hp,
                                              @Field("password") String password);

    @FormUrlEncoded
    @POST("mengajar/create")
    Call<ResponsePengalamanMengajar> createPengalamanMengajar(@Field("guru_id") int guru_id,
                                                      @Field("pengalaman") String pengalaman,
                                                      @Field("tgl_masuk") String tgl_masuk,
                                                      @Field("tgl_selesai") String tgl_selesai);

    @GET("mengajar/find-by-id")
    Call<ResponseDataPengalamanMengajar> getDataPengalamanMengajarByGuruId(@Query("id") int guru_id);

    @DELETE("mengajar/delete")
    Call<ResponseDeletePengalamanMengajar> deletePengalamanMengajar(@Query("id") int id_pengalaman_mengajar);


    @FormUrlEncoded
    @POST("kerja/create")
    Call<ResponseCreatePengalamanKerja> createPengalamankerja( @Field("guru_id") int guru_id,
                                                               @Field("jabatan") String jabatan,
                                                               @Field("perusahaan") String perusahaan,
                                                               @Field("tgl_masuk") String tgl_masuk,
                                                               @Field("tgl_selesai") String tgl_selesai);

    @FormUrlEncoded
    @POST("kerja/update")
    Call<ResponseCreatePengalamanKerja> updatePengalamanKerja( @Field("id_pengalaman_kerja") Integer id_pengalaman_kerja,
                                                               @Field("guru_id") Integer guru_id,
                                                               @Field("jabatan") String jabatan,
                                                               @Field("perusahaan") String perusahaan,
                                                               @Field("tgl_masuk") String tgl_masuk,
                                                               @Field("tgl_selesai") String tgl_selesai);
    @GET("kerja/find-by-id")
    Call<ResponsePengalamanKerja> getDataPengalamanKerjaByGuruId(@Query("id") int guru_id);

    @DELETE("pendidikan/delete")
    Call<ResponseDeleteRiwayatPendidikan> deleteRiwayatPendidikan(@Query("id") int id_riwayat_pendidikan);

    @GET("pendidikan/find-by-id")
    Call<ResponseRiwayatPendidikan> getDataRiwayatPendidikanByGuruId(@Query("id") int guru_id);

    @GET("dokumen/find-by-id")
    Call<ResponseListDokumen> getDataDokumenByGuruId(@Query("id") int guru_id);

    @DELETE("dokumen/delete")
    Call<ResponseDeleteDokumen> deleteDokumen(@Query("id") int id_dokumen);

    @DELETE("kerja/delete")
    Call<ResponseCreatePengalamanKerja> deletePengalamanKerja(@Query("id") int id_pengalaman_kerja);

    @FormUrlEncoded
    @POST("murid/update")
    Call<ResponseCreateMurid> updateMurid(@Field("id") Integer id,
                                          @Field("nama") String nama,
                                          @Field("no_hp") String no_hp,
                                          @Field("email") String email,
                                          @Field("alamat") String alamat,
                                          @Field("jk") String jk,
                                          @Field("nisn") String nisn,
                                          @Field("kelas") String kelas,
                                          @Field("nama_sekolah") String nama_sekolah,
                                          @Field("photo") String photo);
    @FormUrlEncoded
    @POST("lokasi/create")
    Call<ResponseLokasi> createBahanAjarLokasi(@Field("guru_id") int guru_id,
                                               @Field("provinsi") String provinsi,
                                               @Field("kota") String kota,
                                               @Field("kecamatan") String kecamatan);

    @FormUrlEncoded
    @POST("lokasi/update")
    Call<ResponseLokasi> updateBahanAjarLokasi(
                                               @Field("id_bahan_ajar_lokasi") int id_bahan_ajar_lokasi,
                                               @Field("guru_id") int guru_id,
                                               @Field("provinsi") String provinsi,
                                               @Field("kota") String kota,
                                               @Field("kecamatan") String kecamatan);

    @GET("lokasi/find-by-id")
    Call<ResponseFindLokasiById> getDataLokasiByGuruId(@Query("id") int guru_id);

    @GET("data-matpel/find-by-id")
    Call<ResponseDataMatpel> getDataMatpelByMatpelId(@Query("id") int matpel);

    @DELETE("lokasi/delete")
    Call<ResponseLokasi> deleteLokasi(@Query("id") int id_bahan_ajar_lokasi);

    @GET("master-matpel/find-all")
    Call<ResponseMasterMatpel> masterMatpelFindAll();

    @FormUrlEncoded
    @POST("bahan-ajar-matpel/register")
    Call<ResponseCreateBahanAjarMatpel> createBahanAjarMatpel(@Field("guru_id") int guru_id,
                                                              @Field("matpel_id") String matpel_id,
                                                              @Field("tarif") int tarif);

    @FormUrlEncoded
    @POST("login/guru")
    Call<ResponseLogin> guruLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("bahan-ajar-matpel/find-by-id")
    Call<ResponseDaftarBahanAjarJoin> getBahanAjarMatpelJoin(@Query("id") int guru_id);

    @FormUrlEncoded
    @POST("guru-jadwal/register")
    Call<ResponseCreateJadwal> createJadwal(@Field("guru_id") int guru_id,
                                                     @Field("hari") String hari,
                                                     @Field("jam") String jam);

    @GET("guru-jadwal/find-by-id")
    Call<ResponseGuruJadwal> getDataJadwalByGuruId(@Query("id") int guru_id);


    @DELETE("bahan-ajar-matpel/delete")
    Call<ResponseLokasi> deleteMatpel(@Query("id") int id_bahan_ajar_matpel);

    @DELETE("guru-jadwal/delete")
    Call<ResponseLokasi> deleteJadwal(@Query("id") int id_jadwal);

    @GET("identitas/find-by-id")
    Call<ResponseIdentitas> getDataIdentitasByGuruId(@Query("id") int guru_id);

}
