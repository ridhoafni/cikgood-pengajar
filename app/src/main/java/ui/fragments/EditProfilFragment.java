package ui.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.config.ServerConfig;
import com.example.anonymous.CikgoodPengajar.models.Kecamatan;
import com.example.anonymous.CikgoodPengajar.models.Kota;
import com.example.anonymous.CikgoodPengajar.models.Provinsi;
import com.example.anonymous.CikgoodPengajar.response.ResponseKecamatan;
import com.example.anonymous.CikgoodPengajar.response.ResponseKota;
import com.example.anonymous.CikgoodPengajar.response.ResponseProvinsi;
import com.example.anonymous.CikgoodPengajar.rests.ApiClient;
import com.example.anonymous.CikgoodPengajar.rests.ApiInterface;
import com.example.anonymous.CikgoodPengajar.utils.SessionManager;
import com.smarteist.autoimageslider.SliderLayout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfilFragment extends Fragment {
    Context context;
    Button GetImageFromGalleryButton, UploadImageOnServerButton;
    ImageView ShowSelectedImage;
    EditText etGetNama, etGetNoHp, etGetEmail, etGetPassword, etGetTglLahir, etGetJk,
            etGetProvinsiKtp, etGetKotaKtp, etGetKecamatanKtp, etGetAlamatKtp,
            etGetProvinsiDomisili, etGetKotaDomisili, etGetKecamatanDomisili, etGetAlamatDomisili,
            etGetBiodata;
    Bitmap FixBitmap;
    SliderLayout sliderLayout;
    SessionManager sessionManager;

//    private AutoCompleteTextView
//            autoCompleteProvKtp, autoCompleteKotaKtp, autoCompleteKecamatanKtp,
//            autoCompleteProvDomisili, autoCompleteKotaDomisili, autoCompleteKecamatanDomisili;

    ApiInterface apiService;

    String ID_GURU = "id_guru" ;
    String NAMA = "nama" ;
    String PHOTO_PROFILE = "photo_profile" ;
    String EMAIL = "email" ;
    String NO_HP = "no_hp" ;
    String PASSWORD = "password" ;
    String TGL_LAHIR = "tgl_lahir" ;
    String JK = "jk" ;
    String PROVINSI_KTP = "provinsi_ktp" ;
    String KOTA_KTP = "kota_ktp" ;
    String KECAMATAN_KTP = "kecamatan_ktp" ;
    String ALAMAT_KTP = "alamat_ktp" ;
    String PROVINSI_DOMISILI = "provinsi_domisili" ;
    String KOTA_DOMISILI = "kota_domisili" ;
    String KECAMATAN_DOMISILI = "kecamatan_domisili" ;
    String ALAMAT_DOMISILI = "alamat_domisili" ;
    String BIODATA = "biodata" ;

    String ServerUploadPath = ServerConfig.UPLOAD_FOTO_PROFILE_GURU_ENDPOINT;

    ProgressDialog progressDialog ;

    ByteArrayOutputStream byteArrayOutputStream ;

    byte[] byteArray ;

    String ConvertImage ;

    String  GetNameFromEditText, GetNoHpFromEditText, GetEmailFromEditText, GetPasswordFromEditText,
            GetTglLahirFromEditText, GetAlamatKtpFromEditText,GetJkFromEditText, GetJkFromRadioButton,
            GetProvinsiKtpFromEditText, GetKotaKtpFromEditText, GetKecamatanKtpFromEditText, GetAlamatDomisiliFromEditText,
            GetProvinsiDomisiliFromEditText, GetKotaDomisiliFromEditText, GetKecamatanDomisiliFromEditText, GetBiodataFromEditText;

    HttpURLConnection httpURLConnection ;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter ;

    int RC ;

    BufferedReader bufferedReader ;

    StringBuilder stringBuilder;

    boolean check = true;

//    private RadioButton radioSelJK, GetPrRadioButton, GetLkRadioButton;

//    private RadioGroup GetJkRadioGroup;

    int GetIdGuru;

    private ProgressDialog progress;
    private static final String KEY_ID_GURU             = "id_guru";
    private static final String KEY_NAMA                = "nama";
    private static final String KEY_EMAIL               = "email";
    private static final String KEY_NO_HP               = "no_hp";
    private static final String KEY_PASSWORD            = "password";
    private static final String KEY_TGL_LAHIR           = "password";
    private static final String KEY_JK                  = "jk";
    private static final String KEY_PROVINSI_KTP        = "provinsi_ktp";
    private static final String KEY_KOTA_KTP            = "kota_ktp";
    private static final String KEY_KECAMATAN_KTP       = "kecamatan_ktp";
    private static final String KEY_ALAMAT_KTP          = "alamat_ktp";
    private static final String KEY_PROVINSI_DOMISILI   = "provinsi_domisili";
    private static final String KEY_KOTA_DOMISILI       = "kota_domisili";
    private static final String KEY_KECAMATAN_DOMISILI  = "kecamatan_domisili";
    private static final String KEY_ALAMAT_DOMISILI     = "alamat_domisili";
    private static final String KEY_BIODATA             = "biodata";
    private static final String KEY_PHOTO_PROFILE       = "photo_profile";

    private int SESSION_ID_MURID;
    private String SESSION_NAMA;
    private String SESSION_EMAIL;
    private String SESSION_NO_HP;
    private String SESSION_PASSWORD;
    private String SESSION_TGL_LAHIR;
    private String SESSION_JK;
    private String SESSION_PROVINSI_KTP;
    private String SESSION_KOTA_KTP;
    private String SESSION_KECAMATAN_KTP;
    private String SESSION_ALAMAT_KTP;
    private String SESSION_PROVINSI_DOMISILI;
    private String SESSION_KOTA_DOMISILI;
    private String SESSION_KECAMATAN_DOMISILI;
    private String SESSION_ALAMAT_DOMISILI;
    private String SESSION_BIODATA;
    private String SESSION_PHOTO_PROFILE;


    public EditProfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_profil_edit, container, false);

        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

        GetImageFromGalleryButton = (Button) view.findViewById(R.id.button);

        UploadImageOnServerButton = (Button) view.findViewById(R.id.button2);

        ShowSelectedImage = (ImageView) view.findViewById(R.id.imageView);

        etGetNama = (EditText)view.findViewById(R.id.editTextNama);
        etGetEmail = (EditText)view.findViewById(R.id.editTextEmail);
        etGetNoHp = (EditText)view.findViewById(R.id.editTextHP);
        etGetPassword = (EditText)view.findViewById(R.id.editTextPass);
        etGetTglLahir = (EditText)view.findViewById(R.id.editTextTglLahir);
        etGetJk = (EditText) view.findViewById(R.id.editTextJk);
        etGetProvinsiKtp = (EditText) view.findViewById(R.id.editTextProvinsiKtp);
        etGetKotaKtp = (EditText) view.findViewById(R.id.editTextKotaKtp);
        etGetKecamatanKtp = (EditText) view.findViewById(R.id.editTextKecamatanKtp);
        etGetAlamatKtp = (EditText) view.findViewById(R.id.editTextAlamatKtp);
        etGetProvinsiDomisili = (EditText) view.findViewById(R.id.editTextProvinsiDomisili);
        etGetKotaDomisili = (EditText) view.findViewById(R.id.editTextKotaDomisili);
        etGetKecamatanDomisili = (EditText) view.findViewById(R.id.editTextKecamatanDomisili);
        etGetAlamatDomisili = (EditText) view.findViewById(R.id.editTextAlamatDomisli);
        etGetBiodata = (EditText)view.findViewById(R.id.editTextBiodata);

        byteArrayOutputStream = new ByteArrayOutputStream();

        GetImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });

        UploadImageOnServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetIdGuru                       = SESSION_ID_MURID;
                GetNameFromEditText             = etGetNama.getText().toString();
                GetNoHpFromEditText             = etGetNoHp.getText().toString();
                GetEmailFromEditText            = etGetEmail.getText().toString();
                GetPasswordFromEditText         = etGetPassword.getText().toString();
                GetJkFromEditText               = etGetJk.getText().toString();
                GetProvinsiKtpFromEditText      = etGetProvinsiKtp.getText().toString();
                GetKotaKtpFromEditText          = etGetKotaKtp.getText().toString();
                GetKecamatanKtpFromEditText     = etGetKecamatanKtp.getText().toString();
                GetAlamatKtpFromEditText        = etGetAlamatKtp.getText().toString();
                GetProvinsiDomisiliFromEditText = etGetProvinsiDomisili.getText().toString();
                GetKotaDomisiliFromEditText     = etGetKotaDomisili.getText().toString();
                GetKecamatanDomisiliFromEditText= etGetKecamatanDomisili.getText().toString();
                GetAlamatDomisiliFromEditText   = etGetAlamatDomisili.getText().toString();
                GetBiodataFromEditText          = etGetBiodata.getText().toString();

                System.out.println("ID:"+GetIdGuru);
                System.out.println("Nama:"+GetNameFromEditText);
                System.out.println("NoHp:"+GetNoHpFromEditText);
                System.out.println("Pwd:"+GetPasswordFromEditText);
                System.out.println("JK:"+GetJkFromEditText);
                System.out.println("Email:"+GetEmailFromEditText);
                System.out.println("ProvinsiKtp:"+GetProvinsiKtpFromEditText);
                System.out.println("KotaKtp:"+GetKotaKtpFromEditText);
                System.out.println("KecamatanKtp:"+GetKecamatanKtpFromEditText);
                System.out.println("AlamatKtp:"+GetAlamatKtpFromEditText);
                System.out.println("ProvinsiDomisili:"+GetProvinsiDomisiliFromEditText);
                System.out.println("KotaDomisili:"+GetKotaDomisiliFromEditText);
                System.out.println("KecamatanDomisili:"+GetKecamatanDomisiliFromEditText);
                System.out.println("AlamatDomisili:"+GetAlamatDomisiliFromEditText);
                UploadImageToServer();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                FixBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                ShowSelectedImage.setImageBitmap(FixBitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public void UploadImageToServer(){

        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();

        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(getActivity(),"Image is Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                progressDialog.dismiss();

                Toast.makeText(getActivity(),string1,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(ID_GURU, String.valueOf(GetIdGuru));
                HashMapParams.put(NAMA, GetNameFromEditText);
                HashMapParams.put(PHOTO_PROFILE, ConvertImage);
                HashMapParams.put(EMAIL, GetEmailFromEditText);
                HashMapParams.put(NO_HP, GetNoHpFromEditText);
                HashMapParams.put(PASSWORD, GetPasswordFromEditText);
                HashMapParams.put(TGL_LAHIR, GetTglLahirFromEditText);
                HashMapParams.put(JK, GetJkFromEditText);
                HashMapParams.put(PROVINSI_KTP, GetProvinsiKtpFromEditText);
                HashMapParams.put(KOTA_KTP, GetKotaKtpFromEditText);
                HashMapParams.put(KECAMATAN_KTP, GetKecamatanKtpFromEditText);
                HashMapParams.put(ALAMAT_KTP, GetAlamatKtpFromEditText);
                HashMapParams.put(PROVINSI_DOMISILI, GetProvinsiDomisiliFromEditText);
                HashMapParams.put(KOTA_DOMISILI, GetKotaDomisiliFromEditText);
                HashMapParams.put(KECAMATAN_DOMISILI, GetKecamatanDomisiliFromEditText);
                HashMapParams.put(ALAMAT_DOMISILI, GetAlamatDomisiliFromEditText);
                HashMapParams.put(BIODATA, GetBiodataFromEditText);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {
                url = new URL(requestURL);

                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(20000);

                httpURLConnection.setConnectTimeout(20000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(

                new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(bufferedWriterDataFN(PData));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();

                RC = httpURLConnection.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReader.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.getLocalizedMessage();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            stringBuilder = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilder.append("&");

                    stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                    stringBuilder.append("=");

                    stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilder.toString();
        }

    }

//    private void initAutoComplateProvinsiKtp(){
//        apiService.provinsiFindAll().enqueue(new Callback<ResponseProvinsi>() {
//            @Override
//            public void onResponse(Call<ResponseProvinsi> call, Response<ResponseProvinsi> response) {
//                if (response.isSuccessful()){
//                    List<Provinsi> provinsis = response.body().getMaster();
//                List<String> listAutoComplete = new ArrayList<String>();
//                for (int i = 0; i < provinsis.size(); i++) {
//                    listAutoComplete.add(provinsis.get(i).getName());
//                }
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, listAutoComplete);
//                    autoCompleteProvKtp.setThreshold(0);
//                    autoCompleteProvKtp.setAdapter(adapter);
//                    autoCompleteProvKtp.setTextColor(Color.RED);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseProvinsi> call, Throwable t) {
//
//            }
//        });
//    }

//    private void initAutoComplateKotaKtp(){
//        apiService.kotaFindAll().enqueue(new Callback<ResponseKota>() {
//            @Override
//            public void onResponse(Call<ResponseKota> call, Response<ResponseKota> response) {
//                if (response.isSuccessful()){
//                    List<Kota> kotas = response.body().getMaster();
//                    List<String> listAutoComplete = new ArrayList<String>();
//                    for (int i = 0; i < kotas.size(); i++) {
//                        listAutoComplete.add(kotas.get(i).getName());
//                    }
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, listAutoComplete);
//                    autoCompleteKotaKtp.setThreshold(0);
//                    autoCompleteKotaKtp.setAdapter(adapter);
//                    autoCompleteKotaKtp.setTextColor(Color.RED);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseKota> call, Throwable t) {
//
//            }
//        });
//    }

//    private void initAutoComplateKecamatanKtp(){
//        apiService.kecamatanFindAll().enqueue(new Callback<ResponseKecamatan>() {
//            @Override
//            public void onResponse(Call<ResponseKecamatan> call, Response<ResponseKecamatan> response) {
//                if (response.isSuccessful()){
//                    List<Kecamatan> kecamatans = response.body().getMaster();
//                    List<String> listAutoComplete = new ArrayList<String>();
//                    for (int i = 0; i < kecamatans.size(); i++) {
//                        listAutoComplete.add(kecamatans.get(i).getName());
//                    }
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, listAutoComplete);
//                    autoCompleteKecamatanKtp.setThreshold(0);
//                    autoCompleteKecamatanKtp.setAdapter(adapter);
//                    autoCompleteKecamatanKtp.setTextColor(Color.RED);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseKecamatan> call, Throwable t) {
//
//            }
//        });
//    }

//    private void initAutoComplateProvinsiDomisili(){
//        apiService.provinsiFindAll().enqueue(new Callback<ResponseProvinsi>() {
//            @Override
//            public void onResponse(Call<ResponseProvinsi> call, Response<ResponseProvinsi> response) {
//                if (response.isSuccessful()){
//                    List<Provinsi> provinsis = response.body().getMaster();
//                    List<String> listAutoComplete = new ArrayList<String>();
//                    for (int i = 0; i < provinsis.size(); i++) {
//                        listAutoComplete.add(provinsis.get(i).getName());
//                    }
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, listAutoComplete);
//                    autoCompleteProvDomisili.setThreshold(0);
//                    autoCompleteProvDomisili.setAdapter(adapter);
//                    autoCompleteProvDomisili.setTextColor(Color.RED);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseProvinsi> call, Throwable t) {
//
//            }
//        });
//    }

//    private void initAutoComplateKotaDomisili(){
//        apiService.kotaFindAll().enqueue(new Callback<ResponseKota>() {
//            @Override
//            public void onResponse(Call<ResponseKota> call, Response<ResponseKota> response) {
//                if (response.isSuccessful()){
//                    List<Kota> kotas = response.body().getMaster();
//                    List<String> listAutoComplete = new ArrayList<String>();
//                    for (int i = 0; i < kotas.size(); i++) {
//                        listAutoComplete.add(kotas.get(i).getName());
//                    }
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, listAutoComplete);
//                    autoCompleteKotaDomisili.setThreshold(0);
//                    autoCompleteKotaDomisili.setAdapter(adapter);
//                    autoCompleteKotaDomisili.setTextColor(Color.RED);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseKota> call, Throwable t) {
//
//            }
//        });
//    }

//    private void initAutoComplateKecamatanDomisili(){
//        apiService.kecamatanFindAll().enqueue(new Callback<ResponseKecamatan>() {
//            @Override
//            public void onResponse(Call<ResponseKecamatan> call, Response<ResponseKecamatan> response) {
//                if (response.isSuccessful()){
//                    List<Kecamatan> kecamatans = response.body().getMaster();
//                    List<String> listAutoComplete = new ArrayList<String>();
//                    for (int i = 0; i < kecamatans.size(); i++) {
//                        listAutoComplete.add(kecamatans.get(i).getName());
//                    }
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, listAutoComplete);
//                    autoCompleteKecamatanDomisili.setThreshold(0);
//                    autoCompleteKecamatanDomisili.setAdapter(adapter);
//                    autoCompleteKecamatanDomisili.setTextColor(Color.RED);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseKecamatan> call, Throwable t) {
//
//            }
//        });
//    }

}
