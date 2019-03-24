package ui.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.config.ServerConfig;
import com.example.anonymous.CikgoodPengajar.models.GuruIdentitas;
import com.example.anonymous.CikgoodPengajar.models.GuruProfil;
import com.example.anonymous.CikgoodPengajar.models.Matpel;
import com.example.anonymous.CikgoodPengajar.network.ApiServices;
import com.example.anonymous.CikgoodPengajar.response.ResponseIdentitas;
import com.example.anonymous.CikgoodPengajar.response.ResponseMatpel;
import com.example.anonymous.CikgoodPengajar.rests.ApiClient;
import com.example.anonymous.CikgoodPengajar.rests.ApiInterface;
import com.example.anonymous.CikgoodPengajar.utils.SessionManager;
import com.example.anonymous.CikgoodPengajar.utils.UtilsApi;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.activities.GuruActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdentitasFragment extends Fragment {

    Button GetImageFromGalleryButtonKtp, GetImageFromGalleryButtonNpwp, UploadImageOnServerButton;

    ImageView ShowSelectedImageKtp, ShowSelectedImageNpwp;

    EditText GetImageName;

    Bitmap FixBitmap, FixBitmap2;
    String GURU_ID               = "guru_id";
    String NO_KTP                = "nomor_ktp";
    String PHOTO_KTP             = "photo_ktp";
    String NO_NPWP               = "npwp";
    String PHOTO_NPWP            = "photo_npwp";
    String NAMA_BANK             = "nama_bank";
    String NO_REK                = "nomor_rekening" ;
    String NAMA_PEMILIK_REKENING = "nama_pemilik_rekening" ;

    String ServerUploadPath = ServerConfig.UPLOAD_FOTO_IDENTITAS_ENDPOINT;

    ProgressDialog progressDialog ;

    ByteArrayOutputStream byteArrayOutputStream ;

    byte[] byteArray ;

    String ConvertImageKtp ;
    String ConvertImageNpwp ;

    String nomor_ktp, nomor_npwp, nama_bank, nomor_rekening, nama_pemilik_bank ;

    String GetImageNameFromEditText;

    HttpURLConnection httpURLConnection ;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter ;

    int RC, getGuruId ;

    BufferedReader bufferedReader ;

    StringBuilder stringBuilder;

    boolean check = true;

    ImageView ImgPaud, ImgSd, ImgSmp, ImgSma;
    private Context mContext;
    EditText etGuruId, etKtp, etNpwp, etNamaBank, etNomorRekening, etNamaPemilikRekening;
    LayoutInflater inflater;
    SwipeRefreshLayout swipeRefreshLayout;
    View dialogView;
    AlertDialog.Builder dialog;
    Spinner spinnerNamaBank;
    public String tingkatan;
    Context context;
    ApiInterface mApiInterface;
    ProgressDialog loading;
    SessionManager sessionManager;
    ApiInterface apiServices;

    String[] array_nama_bank        = {"Bank Central Asia Tbk", "Bank Mandiri Tbk", "Bank Negara Indonesia Tbk",
    "Bank Rakyat Indonesia Tbk", "Bank Muamalat Indonesia Tbk", "Bank Mega Tbk", "Bank CIMB Niaga Tbk", "Bank NISP Tbk",
    "Bank BNI Syariah", "Bank BRI Syariah", "Bank Mega Syariah", "Bank BCA Syariah", "Bank Mandiri Syariah", "Bank Tabungan Negara", "Bank Bukopin",
    "Bank Danamon Indonesia", "Bank Riau Kepri", "Bank Riau Kepri Syariah"};

    public static final String EXTRA_TINGKATAN = "extra_jenjang";

    @Override
    public void onAttach(final Activity activity){
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public IdentitasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view;
        view = inflater.inflate(R.layout.fragment_identitas, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {

        GetImageFromGalleryButtonKtp = (Button)view.findViewById(R.id.button_ktp);

        GetImageFromGalleryButtonNpwp = (Button)view.findViewById(R.id.button_npwp);

        UploadImageOnServerButton = (Button)view.findViewById(R.id.btn_identitas);

        ShowSelectedImageKtp = (ImageView)view.findViewById(R.id.imageViewKtp);

        ShowSelectedImageNpwp = (ImageView)view.findViewById(R.id.imageViewNpwp);

        swipeRefreshLayout = view.findViewById(R.id.swipe_identitas);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent);

        refresh();

        etGuruId               = (EditText)view.findViewById(R.id.etFormGuruIdentitas);
        etKtp                  = (EditText)view.findViewById(R.id.etFormNomorKtp);
        etNpwp                 = (EditText)view.findViewById(R.id.etFormNomorNPWP);
        etNomorRekening        = (EditText)view.findViewById(R.id.etFormNoRek);
        etNamaPemilikRekening  = (EditText)view.findViewById(R.id.etFormNamaPemilikRek);

        spinnerNamaBank        = (Spinner)view.findViewById(R.id.spinnerNamaBank);

        spinnerNamaBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = parent.getItemAtPosition(position).toString();
//                Toast.makeText(context, "Kamu memilih " + selectedName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapterJI = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, array_nama_bank);
        adapterJI.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNamaBank.setPrompt("Pilih Nama Bank");
        spinnerNamaBank.setAdapter( adapterJI);

        sessionManager = new SessionManager(getActivity());

        getGuruId = Integer.parseInt(sessionManager.getGuruProfile().get("id_guru"));

        etKtp.setText(sessionManager.getGuruProfile().get("nomor_ktp"));
        etNpwp.setText(sessionManager.getGuruProfile().get("npwp"));
        etNomorRekening.setText(sessionManager.getGuruProfile().get("nomor_rekening"));
        etNamaPemilikRekening.setText(sessionManager.getGuruProfile().get("nama_pemilik_rekening"));
        spinnerNamaBank.setTag(sessionManager.getGuruProfile().get("nama_bank"));

        byteArrayOutputStream = new ByteArrayOutputStream();

        mApiInterface = UtilsApi.getAPIService();

        apiServices  = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);


        GetImageFromGalleryButtonKtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });

        GetImageFromGalleryButtonNpwp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent();

                intent2.setType("image/*");

                intent2.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent2, "Select Image From Gallery"), 2);

            }
        });

        UploadImageOnServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getGuruId = Integer.parseInt(sessionManager.getGuruProfile().get("id_guru"));
                nomor_ktp   = etKtp.getText().toString();
                nomor_npwp  = etNpwp.getText().toString();
                nama_bank   = spinnerNamaBank.getSelectedItem().toString();
                nomor_rekening  = etNomorRekening.getText().toString();
                nama_pemilik_bank = etNamaPemilikRekening.getText().toString();
                UploadImageToServer();

            }
        });

    }

    private void refresh() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        loadDataIdentitasGuruId();
                    }
                }, 3000);
            }
        });
    }

    private void loadDataIdentitasGuruId() {
        int id_guru = Integer.parseInt(sessionManager.getGuruProfile().get("id_guru"));
        apiServices.getDataIdentitasByGuruId(id_guru).enqueue(new Callback<ResponseIdentitas>() {
            @Override
            public void onResponse(Call<ResponseIdentitas> call, Response<ResponseIdentitas> response) {
                System.out.println("Responnya :"+response);

                if (response.isSuccessful()){
                        ArrayList<GuruIdentitas> guruIdentitas = new ArrayList<>();
                        guruIdentitas.add(response.body().getMaster());
                        GuruIdentitas guruIdentitas1 = guruIdentitas.get(0);
                        etKtp.setText(guruIdentitas1.getNomorKtp());
                        Picasso.get().load(ServerConfig.GURU_IDENTITAS_PATH+guruIdentitas1.getPhotoKtp()).into(ShowSelectedImageKtp);
                        etNpwp.setText(guruIdentitas1.getNpwp());
                        Picasso.get().load(ServerConfig.GURU_IDENTITAS_PATH+guruIdentitas1.getPhotoNpwp()).into(ShowSelectedImageNpwp);
                        spinnerNamaBank.setTag(guruIdentitas1.getNamaBank());
                        etNamaPemilikRekening.setText(guruIdentitas1.getNamaPemilikRekening());
                        etNomorRekening.setText(guruIdentitas1.getNomorRekening());

                        System.out.println("KTP :"+guruIdentitas1.getNomorKtp());
                }
            }

            @Override
            public void onFailure(Call<ResponseIdentitas> call, Throwable t) {

            }
        });
    }

    public void onActivityResult(int RC, int RQC, Intent I){
        switch(RC){
            case 1: // Do your stuff here...
                Uri uri = I.getData();
                try {

                    FixBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                    ShowSelectedImageKtp.setImageBitmap(FixBitmap);

                } catch (IOException e) {

                    e.printStackTrace();
                }
                break;
            case 2: // Do your other stuff here...
                Uri uri2 = I.getData();
                try {

                    FixBitmap2 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri2);

                    ShowSelectedImageNpwp.setImageBitmap(FixBitmap2);

                } catch (IOException e) {

                    e.printStackTrace();
                }
                break;
        }
    }

    public void UploadImageToServer(){

        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();

        ConvertImageKtp     = Base64.encodeToString(byteArray, Base64.DEFAULT);
        ConvertImageNpwp    = Base64.encodeToString(byteArray, Base64.DEFAULT);

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

                HashMapParams.put(GURU_ID, String.valueOf(getGuruId));
                HashMapParams.put(NO_KTP, nomor_ktp);
                HashMapParams.put(PHOTO_KTP, ConvertImageKtp);
                HashMapParams.put(NO_NPWP, nomor_npwp);
                HashMapParams.put(PHOTO_NPWP, ConvertImageNpwp);
                HashMapParams.put(NAMA_BANK, nama_bank);
                HashMapParams.put(NO_REK, nomor_rekening);
                HashMapParams.put(NAMA_PEMILIK_REKENING, nama_pemilik_bank);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                loadDataIdentitasGuruId();

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

//    private void initSpinnerDosen(){
//
//        mApiInterface.matpelFindAll().enqueue(new Callback<ResponseMatpel>() {
//            @Override
//            public void onResponse(Call<ResponseMatpel> call, Response<ResponseMatpel> response) {
//                if (response.isSuccessful()) {
//                    loading.dismiss();
//                    List<Matpel> matpels = response.body().getMaster();
//                    List<String> listSpinner = new ArrayList<String>();
//                    for (int i = 0; i < matpels.size(); i++){
//                        listSpinner.add(matpels.get(i).getMatpel());
//                    }
//
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
//                            android.R.layout.simple_spinner_item, listSpinner);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    SpinnerJk.setAdapter(adapter);
//                } else {
//                    loading.dismiss();
////                    Toast.makeText(mContext, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseMatpel> call, Throwable t) {
//                loading.dismiss();
//                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    private void showForm() {
//        loading = ProgressDialog.show(mContext, null, "harap tunggu...", true, false);
//
//        dialog      = new AlertDialog.Builder(mContext);
//        inflater    = getLayoutInflater();
//        dialogView  = inflater.inflate(R.layout.form_search, null);
//
//        // set title dialog
//        dialog.setTitle("Cari GuruProfil Privat");
//
//        // set pesan dari dialog
//        dialog
//        .setView(dialogView)
//        .setCancelable(false)
//        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog,int id) {
//                // jika tombol diklik, maka akan menutup activity ini
//                IdentitasFragment.this.getActivity().finish();
//            }
//        })
//        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // jika tombol ini diklik, akan menutup dialog
//                // dan tidak terjadi apa2
//                dialog.cancel();
//            }
//        });
//
//        TxtNama        = (EditText) dialogView.findViewById(R.id.txt_nama);
//        TxtTingkatan   = (EditText) dialogView.findViewById(R.id.txt_tingkatan);
//        SpinnerMateri  = (Spinner) dialogView.findViewById(R.id.spinner_materi);
//        SpinnerJk      = (Spinner) dialogView.findViewById(R.id.spinner_jk);
//
//        List<String> jk = new ArrayList<String>();
//        jk.add("Laki-Laki");
//        jk.add("Perempuan");
//
//        SpinnerMateri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedName = parent.getItemAtPosition(position).toString();
////                Toast.makeText(mContext, "Kamu memilih " + selectedName, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        TxtTingkatan.setText(tingkatan);
//
//        ArrayAdapter<String> adapterJK = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, jk);
//        adapterJK.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        SpinnerJk.setAdapter(adapterJK);
//
//        mApiInterface.matpelFindAll().enqueue(new Callback<ResponseMatpel>() {
//            @Override
//            public void onResponse(Call<ResponseMatpel> call, Response<ResponseMatpel> response) {
//                if (response.isSuccessful()) {
//                    loading.dismiss();
//
//                    List<Matpel> matpelItems = response.body().getMaster();
//                    List<String> listSpinner = new ArrayList<String>();
//                    for (int i = 0; i < matpelItems.size(); i++){
//                        listSpinner.add(matpelItems.get(i).getMatpel());
//                    }
//
//                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
//                            android.R.layout.simple_spinner_item, listSpinner);
//
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    SpinnerMateri.setAdapter(adapter);
//
//                } else {
//                    loading.dismiss();
//                    Toast.makeText(mContext, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseMatpel> call, Throwable t) {
//                loading.dismiss();
//                Toast.makeText(mContext, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // membuat alert dialog dari builder
//        AlertDialog alertDialog = dialog.create();
//
//        // menampilkan alert dialog
//        alertDialog.show();
//    }

//    @Override
//    public void onClick(View v) {
//        Intent i;
//        String jenjang;
//        String matpel;
//        switch (v.getId()){
//            case R.id.iv_paud:
//                tingkatan  = "Paud";
//                showForm();
//                break;
//
//            case R.id.iv_sd:
//                tingkatan  = "SD";
//                showForm();
//                break;
//
//            case R.id.iv_smp:
//                i = new Intent(IdentitasFragment.this.getActivity(), GuruActivity.class);
//                startActivity(i);
//                break;
//
//            case R.id.iv_sma:
//                tingkatan  = "SMA";
//
//                break;
//        }
//    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
}
