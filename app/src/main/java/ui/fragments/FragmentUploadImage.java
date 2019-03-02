package ui.fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.adapters.CACKabupatenAdapter;
import com.example.anonymous.CikgoodPengajar.adapters.CACKecamatanAdapter;
import com.example.anonymous.CikgoodPengajar.adapters.CAProvinsiAdapter;
import com.example.anonymous.CikgoodPengajar.config.ServerConfig;
import com.example.anonymous.CikgoodPengajar.models.Kabupaten;
import com.example.anonymous.CikgoodPengajar.models.Kecamatan;
import com.example.anonymous.CikgoodPengajar.models.Kota;
import com.example.anonymous.CikgoodPengajar.models.Provinsi;
import com.example.anonymous.CikgoodPengajar.models.SemuaProvinsi;
import com.example.anonymous.CikgoodPengajar.response.ModelKabupaten;
import com.example.anonymous.CikgoodPengajar.response.ModelKecamatan;
import com.example.anonymous.CikgoodPengajar.response.ModelProvinsi;
import com.example.anonymous.CikgoodPengajar.response.ResponseKota;
import com.example.anonymous.CikgoodPengajar.response.ResponseProvinsi;
import com.example.anonymous.CikgoodPengajar.rests.ApiClient;
import com.example.anonymous.CikgoodPengajar.rests.ApiInterface;
import com.example.anonymous.CikgoodPengajar.rests.CombineApi;
import com.example.anonymous.CikgoodPengajar.rests.ProvInterface;
import com.example.anonymous.CikgoodPengajar.utils.SessionManager;
import com.squareup.picasso.Picasso;

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
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUploadImage extends Fragment {


    public FragmentUploadImage() {
        // Required empty public constructor
    }

    private static final String TAG = "Fragment";

    Button GetImageFromGalleryButton, UploadImageOnServerButton;

    Context context;

    SessionManager sessionManager;

    RelativeLayout frame_container;

    ImageView ShowSelectedImage;

    EditText etGetNama, etGetNoHp, etGetEmail, etGetPassword, etGetTglLahir, etGetJk,
            etGetProvinsiKtp, etGetKotaKtp, etGetKecamatanKtp, etGetAlamatKtp,
            etGetProvinsiDomisili, etGetKotaDomisili, etGetKecamatanDomisili, etGetAlamatDomisili,
            etGetBiodata;

    AutoCompleteTextView autoCompleteProvKtp, autoCompleteKotaKtp,autoCompleteKecKtp, autoCompleteProvinsiDomisili, autoCompleteKotaDomisili, autoCompleteKecamatanDomisili;

    List<Provinsi> provinsis;

    Bitmap FixBitmap;

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

    String ServerUploadPath = ServerConfig.UPLOAD_FOTO_PROFILE_ENDPOINT;

    ProgressDialog progressDialog ;

    ByteArrayOutputStream byteArrayOutputStream ;

    byte[] byteArray ;

    String ConvertImage ;

    String GetImageNameFromEditText;

    int GetIdGuru;

    String GetIdProvinsi;
    String GetNamaProvinsi;

    ApiInterface apiService;

    String  GetNameFromEditText, GetNoHpFromEditText, GetEmailFromEditText, GetPasswordFromEditText,
            GetTglLahirFromEditText, GetAlamatKtpFromEditText,GetJkFromEditText, GetJkFromRadioButton,
            GetProvinsiKtpFromEditText, GetKotaKtpFromEditText, GetKecamatanKtpFromEditText, GetAlamatDomisiliFromEditText,
            GetProvinsiDomisiliFromEditText, GetKotaDomisiliFromEditText, GetKecamatanDomisiliFromEditText, GetBiodataFromEditText;

    HttpURLConnection httpURLConnection ;

    ProvInterface provInterface;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter ;

    int RC ;

    BufferedReader bufferedReader ;

    StringBuilder stringBuilder;

    boolean check = true;

    int selectedName;

    ArrayList<String> arrayList;

    List<SemuaProvinsi> provinsiArrayList = new ArrayList<>();

    List<Kabupaten> kabupatenArrayList = new ArrayList<>();

    List<Kecamatan> kecamatanArrayList = new ArrayList<>();


    HashMap<String, String> hmProvinsi;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat;

    List<String> listAutoComplete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_upload_image, container, false);

        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

        arrayList = new ArrayList<>();

        hmProvinsi = new HashMap<>();

        listAutoComplete = new ArrayList<String>();

        initAutoComplateProvinsiKtp();
        initAutoComplateProvinsiDomisili();

        GetImageFromGalleryButton = (Button)view.findViewById(R.id.button_upload_profil);

        UploadImageOnServerButton = (Button)view.findViewById(R.id.button2);

        ShowSelectedImage = (ImageView)view.findViewById(R.id.imageView);

        frame_container = (RelativeLayout) view.findViewById(R.id.frame_container);
        etGetNama = (EditText)view.findViewById(R.id.editTextNama);
        etGetEmail = (EditText)view.findViewById(R.id.editTextEmail);
        etGetNoHp = (EditText)view.findViewById(R.id.editTextHP);
        etGetPassword = (EditText)view.findViewById(R.id.editTextPass);
        etGetTglLahir = (EditText)view.findViewById(R.id.editTextTglLahir);
        etGetJk = (EditText) view.findViewById(R.id.editTextJk);
//        etGetProvinsiKtp = (EditText) view.findViewById(R.id.editTextProvinsiKtp);
        autoCompleteProvKtp = (AutoCompleteTextView) view.findViewById(R.id.autoComplateProvinsiKtp);
        autoCompleteKotaKtp = (AutoCompleteTextView) view.findViewById(R.id.autoComplateKotaKtp);
        autoCompleteKecKtp = (AutoCompleteTextView) view.findViewById(R.id.autoComplateKecamatanKtp);
        etGetAlamatKtp = (EditText) view.findViewById(R.id.editTextAlamatKtp);
        autoCompleteProvinsiDomisili = (AutoCompleteTextView) view.findViewById(R.id.autoComplateProvinsiDomisili);
        autoCompleteKotaDomisili = (AutoCompleteTextView) view.findViewById(R.id.autoComplateKotaDomisili);
        autoCompleteKecamatanDomisili = (AutoCompleteTextView) view.findViewById(R.id.autoComplateKecamatanDomisili);
        etGetKecamatanDomisili = (EditText) view.findViewById(R.id.editTextKecamatanDomisili);
        etGetAlamatDomisili = (EditText) view.findViewById(R.id.editTextAlamatDomisli);
        etGetBiodata = (EditText)view.findViewById(R.id.editTextBiodata);

        etGetTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }

        });

        sessionManager = new SessionManager(getActivity());

        etGetNama.setText(sessionManager.getGuruProfile().get("nama"));
        etGetEmail.setText(sessionManager.getGuruProfile().get("email"));
        etGetNoHp.setText(sessionManager.getGuruProfile().get("no_hp"));
        etGetTglLahir.setText(sessionManager.getGuruProfile().get("tgl_lahir"));
        etGetJk.setText(sessionManager.getGuruProfile().get("jk"));
        autoCompleteProvKtp.setText(sessionManager.getGuruProfile().get("provinsi_ktp"));
        autoCompleteKotaKtp.setText(sessionManager.getGuruProfile().get("kota_ktp"));
        autoCompleteKecKtp.setText(sessionManager.getGuruProfile().get("kecamatan_ktp"));
        etGetAlamatKtp.setText(sessionManager.getGuruProfile().get("alamat_ktp"));
        autoCompleteProvinsiDomisili.setText(sessionManager.getGuruProfile().get("provinsi_domisili"));
        autoCompleteKotaDomisili.setText(sessionManager.getGuruProfile().get("kota_domisili"));
        autoCompleteKecamatanDomisili.setText(sessionManager.getGuruProfile().get("kecamatan_domisili"));
        etGetAlamatDomisili.setText(sessionManager.getGuruProfile().get("alamat_domisili"));
        etGetBiodata.setText(sessionManager.getGuruProfile().get("biodata"));

        System.out.println("Kota Domisili :" +sessionManager.getGuruProfile().get("kota_domisili"));

        Picasso.get().load(ServerConfig.GURU_PATH+sessionManager.getGuruProfile().get("photo_profile")).into(ShowSelectedImage);

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

                GetIdGuru                       = Integer.parseInt(sessionManager.getGuruProfile().get("id_guru"));
                GetNameFromEditText             = etGetNama.getText().toString();
                GetNoHpFromEditText             = etGetNoHp.getText().toString();
                GetEmailFromEditText            = etGetEmail.getText().toString();
                GetPasswordFromEditText         = etGetPassword.getText().toString();
                GetTglLahirFromEditText         = etGetTglLahir.getText().toString();
                GetJkFromEditText               = etGetJk.getText().toString();
                GetProvinsiKtpFromEditText      = autoCompleteProvKtp.getText().toString();
                GetKotaKtpFromEditText          = autoCompleteKotaKtp.getText().toString();
                GetKecamatanKtpFromEditText     = autoCompleteKecKtp.getText().toString();
                GetAlamatKtpFromEditText        = etGetAlamatKtp.getText().toString();
                GetProvinsiDomisiliFromEditText = autoCompleteProvinsiDomisili.getText().toString();
                GetKotaDomisiliFromEditText     = autoCompleteKotaDomisili.getText().toString();
                GetKecamatanDomisiliFromEditText= autoCompleteKecamatanDomisili.getText().toString();
                GetAlamatDomisiliFromEditText   = etGetAlamatDomisili.getText().toString();
                GetBiodataFromEditText          = etGetBiodata.getText().toString();

                System.out.println("ID:"+GetIdGuru);
                System.out.println("Nama:"+GetNameFromEditText);
                System.out.println("NoHp:"+GetNoHpFromEditText);
                System.out.println("Pwd:"+GetPasswordFromEditText);
                System.out.println("JK:"+GetJkFromEditText);
                System.out.println("TglLahir:"+GetTglLahirFromEditText);
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

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(Calendar.YEAR, year);
                newDate.set(Calendar.MONTH, monthOfYear);
                newDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String formatTanggal = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                etGetTglLahir.setText(sdf.format(newDate.getTime()));
            }
        },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();;
    }

    public void initAutoComplateProvinsiKtp(){

        provInterface = CombineApi.getApiProv();
        Call<ModelProvinsi> getProvinsi = provInterface.getProvinsi();
        getProvinsi.enqueue(new Callback<ModelProvinsi>() {
            @Override
            public void onResponse(Call<ModelProvinsi> call, Response<ModelProvinsi> response) {
                provinsiArrayList = response.body().getSemuaprovinsi();
                CAProvinsiAdapter adapter = new CAProvinsiAdapter(getActivity(), android.R.layout.select_dialog_item, provinsiArrayList);
                autoCompleteProvKtp.setAdapter(adapter);
                autoCompleteProvKtp.setThreshold(0);
                autoCompleteProvKtp.dismissDropDown();
                autoCompleteProvKtp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (SemuaProvinsi semuaprovinsi : provinsiArrayList){
                            if (semuaprovinsi.getNama().equals(autoCompleteProvKtp.getText().toString())){
                                loadKabupatenKtp(semuaprovinsi.getId());
                            }
                        }

                    }
                });
            }

            @Override
            public void onFailure(Call<ModelProvinsi> call, Throwable t) {

            }
        });
    }

    public void initAutoComplateProvinsiDomisili(){

        provInterface = CombineApi.getApiProv();
        Call<ModelProvinsi> getProvinsi = provInterface.getProvinsi();
        getProvinsi.enqueue(new Callback<ModelProvinsi>() {
            @Override
            public void onResponse(Call<ModelProvinsi> call, Response<ModelProvinsi> response) {
                provinsiArrayList = response.body().getSemuaprovinsi();
                CAProvinsiAdapter adapter = new CAProvinsiAdapter(getActivity(), android.R.layout.select_dialog_item, provinsiArrayList);
                autoCompleteProvinsiDomisili.setAdapter(adapter);
                autoCompleteProvinsiDomisili.setThreshold(0);
                autoCompleteProvinsiDomisili.dismissDropDown();
                autoCompleteProvinsiDomisili.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (SemuaProvinsi semuaprovinsi : provinsiArrayList){
                            if (semuaprovinsi.getNama().equals(autoCompleteProvinsiDomisili.getText().toString())){
                                loadKabupaten(semuaprovinsi.getId());
                            }
                        }

                    }
                });
            }

            @Override
            public void onFailure(Call<ModelProvinsi> call, Throwable t) {

            }
        });
    }

    private void loadKabupatenKtp(String id) {
        provInterface = CombineApi.getApiProv();
        Call<ModelKabupaten> getKabupaten = provInterface.getKabupaten(id);
        getKabupaten.enqueue(new Callback<ModelKabupaten>() {
            @Override
            public void onResponse(Call<ModelKabupaten> call, Response<ModelKabupaten> response) {
                kabupatenArrayList = response.body().getKabupatens();
                CACKabupatenAdapter adapter = new CACKabupatenAdapter(getActivity() ,android.R.layout.select_dialog_item, kabupatenArrayList);
                autoCompleteKotaKtp.setAdapter(adapter);
                autoCompleteKotaKtp.setThreshold(0);
                autoCompleteKotaKtp.dismissDropDown();
                HashMap<String,String> a = new HashMap<String,String>();
                autoCompleteKotaKtp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (Kabupaten kabupaten : kabupatenArrayList){
                            if (kabupaten.getNama().equals(autoCompleteKotaKtp.getText().toString())){
                                loadKecamatanKtp(kabupaten.getId());
                                break;
                            }
                        }

                    }
                });


            }
            @Override
            public void onFailure(Call<ModelKabupaten> call, Throwable t) {

            }
        });
    }

    private void loadKabupaten(String id) {
        provInterface = CombineApi.getApiProv();
        Call<ModelKabupaten> getKabupaten = provInterface.getKabupaten(id);
        getKabupaten.enqueue(new Callback<ModelKabupaten>() {
            @Override
            public void onResponse(Call<ModelKabupaten> call, Response<ModelKabupaten> response) {
                kabupatenArrayList = response.body().getKabupatens();
                CACKabupatenAdapter adapter = new CACKabupatenAdapter(getActivity() ,android.R.layout.select_dialog_item, kabupatenArrayList);
                autoCompleteKotaDomisili.setAdapter(adapter);
                autoCompleteKotaDomisili.setThreshold(0);
                autoCompleteKotaDomisili.dismissDropDown();
                HashMap<String,String> a = new HashMap<String,String>();
                autoCompleteKotaDomisili.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (Kabupaten kabupaten : kabupatenArrayList){
                            if (kabupaten.getNama().equals(autoCompleteKotaDomisili.getText().toString())){
                                loadKecamatan(kabupaten.getId());
                                break;
                            }
                        }

                    }
                });


            }
            @Override
            public void onFailure(Call<ModelKabupaten> call, Throwable t) {

            }
        });
    }

    private void loadKecamatan(String id) {
        provInterface = CombineApi.getApiProv();
        Call<ModelKecamatan> getKecamatan = provInterface.getKecamatan(id);
        getKecamatan.enqueue(new Callback<ModelKecamatan>() {
            @Override
            public void onResponse(Call<ModelKecamatan> call, Response<ModelKecamatan> response) {
                kecamatanArrayList = response.body().getKecamatans();
                CACKecamatanAdapter adapter = new CACKecamatanAdapter(getActivity(), android.R.layout.select_dialog_item, kecamatanArrayList);
                autoCompleteKecamatanDomisili.setAdapter(adapter);
                HashMap<String,String> a = new HashMap<String,String>();
                autoCompleteKecamatanDomisili.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (Kecamatan kecamatan : kecamatanArrayList){
                            if (kecamatan.getNama().equals(autoCompleteKecamatanDomisili.getText().toString())){
//                                loadDesa(kecamatan.getId());
                                break;
                            }
                        }

                    }
                });


            }
            @Override
            public void onFailure(Call<ModelKecamatan> call, Throwable t) {

            }
        });
    }

    private void loadKecamatanKtp(String id) {
        provInterface = CombineApi.getApiProv();
        Call<ModelKecamatan> getKecamatan = provInterface.getKecamatan(id);
        getKecamatan.enqueue(new Callback<ModelKecamatan>() {
            @Override
            public void onResponse(Call<ModelKecamatan> call, Response<ModelKecamatan> response) {
                kecamatanArrayList = response.body().getKecamatans();
                CACKecamatanAdapter adapter = new CACKecamatanAdapter(getActivity(), android.R.layout.select_dialog_item, kecamatanArrayList);
                autoCompleteKecKtp.setAdapter(adapter);
                HashMap<String,String> a = new HashMap<String,String>();
                autoCompleteKecKtp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (Kecamatan kecamatan : kecamatanArrayList){
                            if (kecamatan.getNama().equals(autoCompleteKecKtp.getText().toString())){
//                                loadDesa(kecamatan.getId());
                                break;
                            }
                        }

                    }
                });


            }
            @Override
            public void onFailure(Call<ModelKecamatan> call, Throwable t) {

            }
        });
    }


    private void initAutoComplateKotaKtp(){
      String id = hmProvinsi.get(0);
        apiService.kotaFindById(14).enqueue(new Callback<ResponseKota>() {
            @Override
            public void onResponse(Call<ResponseKota> call, Response<ResponseKota> response) {
                if (response.isSuccessful()){
                    List<Kota> kotas = response.body().getMaster();
                    List<String> listAutoComplete = new ArrayList<String>();
                    for (int i = 0; i < kotas.size(); i++) {
                        listAutoComplete.add(kotas.get(i).getName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, listAutoComplete);
                    autoCompleteKotaKtp.setThreshold(0);
                    autoCompleteKotaKtp.setAdapter(adapter);
                    autoCompleteKotaKtp.setTextColor(Color.RED);
                }

            }

            @Override
            public void onFailure(Call<ResponseKota> call, Throwable t) {

            }
        });
    }


    @Override
    public void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 1 && RQC == Activity.RESULT_OK && I != null && I.getData() != null) {

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

//                HashMapParams.put(ImageTag, GetImageNameFromEditText);

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


    public class ImageProcessClass {

        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {

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

                    while ((RC2 = bufferedReader.readLine()) != null) {

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
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


}
