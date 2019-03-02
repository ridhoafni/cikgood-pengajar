package com.example.anonymous.CikgoodPengajar.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context _context;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String ID_GURU = "id_guru";
    public static final String NAMA = "nama";
    public static final String PHOTO_PROFILE = "photo_profile";
    public static final String EMAIL = "email";
    public static final String NO_HP = "no_hp";
    public static final String PASSWORD = "password";
    public static final String TGL_LAHIR = "tgl_lahir";
    public static final String JK = "jk";
    public static final String PROVINSI_KTP = "provinsi_ktp";
    public static final String KOTA_KTP = "kota_ktp";
    public static final String KECAMATAN_KTP = "kecamatan_ktp";
    public static final String ALAMAT_KTP = "alamat_ktp";
    public static final String PROVINSI_DOMISILI = "provinsi_domisili";
    public static final String KOTA_DOMISILI = "kota_domisili";
    public static final String KECAMATAN_DOMISILI = "kecamatan_domisili";
    public static final String ALAMAT_DOMISILI = "alamat_domisili";
    public static final String BIODATA = "biodata";
    public static final String STATUS = "status";
    public static final String CREATE_AT = "create_at";
    public static final String UPDATE_AT = "update_at";

    public Context get_context(){
        return _context;
    }

    //constructor
    public SessionManager(Context context){
        this._context       = context;
        sharedPreferences   = PreferenceManager.getDefaultSharedPreferences(context);
        editor              = sharedPreferences.edit();
    }

    public void createLoginSession(Integer id_guru, String nama, String photo_profile, String email, String no_hp,
                                   String password, String tgl_lahir, String jk, String provinsi_ktp,
                                   String kota_ktp, String kecamatan_ktp, String alamat_ktp, String provinsi_domisili,
                                   String kota_domisili, String kecamatan_domisili, String alamat_domisili,
                                   String biodata, String status, String create_at, String update_at){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putInt(ID_GURU, id_guru);
        editor.putString(NAMA, nama);
        editor.putString(PHOTO_PROFILE, photo_profile);
        editor.putString(EMAIL, email);
        editor.putString(NO_HP, no_hp);
        editor.putString(PASSWORD, password);
        editor.putString(TGL_LAHIR, tgl_lahir);
        editor.putString(JK, jk);
        editor.putString(PROVINSI_KTP, provinsi_ktp);
        editor.putString(KOTA_KTP, kota_ktp);
        editor.putString(KECAMATAN_KTP, kecamatan_ktp);
        editor.putString(ALAMAT_KTP, alamat_ktp);
        editor.putString(PROVINSI_DOMISILI, provinsi_domisili);
        editor.putString(KOTA_DOMISILI, kota_domisili);
        editor.putString(KECAMATAN_DOMISILI, kecamatan_domisili);
        editor.putString(ALAMAT_DOMISILI, alamat_domisili);
        editor.putString(BIODATA, biodata);
        editor.putString(STATUS, status);
        editor.putString(CREATE_AT, create_at);
        editor.putString(UPDATE_AT, update_at);
        editor.apply();
    }

    public HashMap<String, String> getGuruProfile(){
        HashMap<String,String> murid = new HashMap<>();
        murid.put(ID_GURU, String.valueOf(sharedPreferences.getInt(ID_GURU,0)));
        murid.put(NAMA, sharedPreferences.getString(NAMA,null));
        murid.put(PHOTO_PROFILE, sharedPreferences.getString(PHOTO_PROFILE,null));
        murid.put(NO_HP, sharedPreferences.getString(NO_HP,null));
        murid.put(EMAIL, sharedPreferences.getString(EMAIL,null));
        murid.put(PASSWORD, sharedPreferences.getString(PASSWORD,null));
        murid.put(TGL_LAHIR, sharedPreferences.getString(TGL_LAHIR,null));
        murid.put(JK, sharedPreferences.getString(JK,null));
        murid.put(PROVINSI_KTP, sharedPreferences.getString(PROVINSI_KTP,null));
        murid.put(KOTA_KTP, sharedPreferences.getString(KOTA_KTP,null));
        murid.put(KECAMATAN_KTP, sharedPreferences.getString(KECAMATAN_KTP,null));
        murid.put(ALAMAT_KTP, sharedPreferences.getString(ALAMAT_KTP,null));
        murid.put(PROVINSI_DOMISILI, sharedPreferences.getString(PROVINSI_DOMISILI,null));
        murid.put(KOTA_DOMISILI, sharedPreferences.getString(KOTA_DOMISILI,null));
        murid.put(KECAMATAN_DOMISILI, sharedPreferences.getString(KECAMATAN_DOMISILI,null));
        murid.put(ALAMAT_DOMISILI, sharedPreferences.getString(ALAMAT_DOMISILI,null));
        murid.put(BIODATA, sharedPreferences.getString(BIODATA,null));
        murid.put(STATUS, sharedPreferences.getString(STATUS,null));
        murid.put(CREATE_AT, sharedPreferences.getString(CREATE_AT,null));
        murid.put(UPDATE_AT, sharedPreferences.getString(UPDATE_AT,null));
        return murid;
    }

    public void logoutGuru(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

}
