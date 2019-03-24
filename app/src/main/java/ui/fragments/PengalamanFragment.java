package ui.fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.adapters.DokumenAdapter;
import com.example.anonymous.CikgoodPengajar.adapters.PengalamanKerjaAdapter;
import com.example.anonymous.CikgoodPengajar.adapters.PengalamanMengajarAdapter;
import com.example.anonymous.CikgoodPengajar.adapters.RiwayatPendidikanAdapter;
import com.example.anonymous.CikgoodPengajar.config.ServerConfig;
import com.example.anonymous.CikgoodPengajar.models.Dokumen;
import com.example.anonymous.CikgoodPengajar.models.PengalamanKerja;
import com.example.anonymous.CikgoodPengajar.models.PengalamanMengajar;
import com.example.anonymous.CikgoodPengajar.models.RiwayatPendidikan;
import com.example.anonymous.CikgoodPengajar.response.ResponseCreatePengalamanKerja;
import com.example.anonymous.CikgoodPengajar.response.ResponseDataPengalamanMengajar;
import com.example.anonymous.CikgoodPengajar.response.ResponseListDokumen;
import com.example.anonymous.CikgoodPengajar.response.ResponsePengalamanKerja;
import com.example.anonymous.CikgoodPengajar.response.ResponsePengalamanMengajar;
import com.example.anonymous.CikgoodPengajar.response.ResponseRiwayatPendidikan;
import com.example.anonymous.CikgoodPengajar.rests.ApiClient;
import com.example.anonymous.CikgoodPengajar.rests.ApiInterface;
import com.example.anonymous.CikgoodPengajar.utils.SessionManager;
import com.google.android.gms.vision.text.Line;

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
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.activities.MainTabActivity;
import ui.activities.SettingsActivity;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PengalamanFragment extends Fragment {


    public PengalamanFragment() {
        // Required empty public constructor
    }

    Button GetImageFromGalleryButton, UploadImageOnServerButton;

    ImageView ShowSelectedImage, ShowSelectedImageDokumen;

    EditText GetImageName;

    Bitmap FixBitmap;

    String GURU_ID          = "guru_id" ;
    String GELAR            = "gelar" ;
    String JENIS_INSTITUSI  = "jenis_institusi" ;
    String NAMA_INSTITUSI   = "nama_institusi" ;
    String JURUSAN          = "jurusan" ;
    String TAHUN_MASUK      = "tahun_masuk" ;
    String TAHUN_SELESAI    = "tahun_selesai" ;
    String PHOTO_IJAZAH     = "photo_ijazah" ;

    String JENIS_DOKUMEN    = "jenis_dokumen";
    String NAMA_DOKUMEN     = "nama_dokumen";
    String TAHUN            = "tahun";
    String PHOTO_DOKUMEN    = "photo_dokumen";

    String ServerUploadPath = ServerConfig.UPLOAD_FOTO_RIWAYAT_PENDIDIKAN_ENDPOINT;
    String ServerUploadDokumenPath = ServerConfig.UPLOAD_FOTO_DOKUMEN_ENDPOINT;

    ProgressDialog progressDialog ;

    ByteArrayOutputStream byteArrayOutputStream ;

    byte[] byteArray ;

    String ConvertImage, selectedName ;

    String GetImageNameFromEditText;

    String guru_id, gelar, jenis_institusi, nama_institusi, jurusan, tahun_masuk, tahun_selesai, photo_ijazah;

    String guru_id_dokumen, jenis_dokumen, nama_dokumen, tahun, photo_dokumen;

    HttpURLConnection httpURLConnection ;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter ;

    int RC ;

    BufferedReader bufferedReader ;

    StringBuilder stringBuilder;

    boolean check = true;

    private Button btPengalamanMengajar, btPengalamanKerja, btRiwayatPendidikan,btDokumen;
    private EditText etFormMengajarPengalaman, etFormMengajarTglMasuk, etFormMengajarTglKeluar, etFormMengajarGuruId;
    private EditText etFormKerjaJabatan, etFormKerjaPerusahaan, etFormKerjaTglMasuk, etFormKerjaTglKeluar, etFormKerjaGuruId;
    private EditText etFormGuruId, etFormGelar, etFormNamaInstitusi, etFormJurusan, etFormTahunMasuk, etFormTahunSelesai;
    private EditText etFormGuruIdDokumen, etFormNamaDokumen, etFormTahunDokumen;
    private Spinner spinnerFormJenisInstitusi, spinnerFormJenisDokumen;
    TextView tvPengalamanMengajar, tvPengalamanTglMasuk, tvPengalamanTglKeluar, garis, judul;
    List<PengalamanMengajar> pengalamanMengajars = new ArrayList<>();
    List<PengalamanKerja> pengalamanKerjas = new ArrayList<>();
    List<RiwayatPendidikan> riwayatPendidikans = new ArrayList<>();
    List<Dokumen> dokumens = new ArrayList<>();
    private PengalamanMengajarAdapter pengalamanMengajarAdapter;
    private PengalamanKerjaAdapter pengalamanKerjaAdapter;
    private RiwayatPendidikanAdapter riwayatPendidikanAdapter;
    private DokumenAdapter dokumenAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    DatePickerDialog datePickerDialog;
    LinearLayout llMengajar;
    RecyclerView recyclerViewMengajar;
    RecyclerView recyclerViewKerja;
    RecyclerView recyclerViewPendidikan;
    RecyclerView recyclerViewDokumen;
    private ProgressDialog loading;
    SessionManager sessionManager;
    int getGuruId;
    AlertDialog.Builder dialog;
    PengalamanMengajar data;
    ApiInterface apiService;
    AlertDialog alertDialog;
    LayoutInflater inflater;
    View dialogView;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pengalaman, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btPengalamanMengajar    = (Button)view.findViewById(R.id.btn_pengalaman_mengajar);
        btPengalamanKerja       = (Button)view.findViewById(R.id.btn_pengalaman_kerja);
        btRiwayatPendidikan     = (Button)view.findViewById(R.id.btn_riwayat_pendididikan);
        btDokumen               = (Button)view.findViewById(R.id.btn_dokumen);
        tvPengalamanMengajar    = (TextView)view.findViewById(R.id.tv_pengalaman_mengajar);
        garis                   = (TextView)view.findViewById(R.id.garis);
        recyclerViewMengajar    = (RecyclerView)view.findViewById(R.id.recyclerViewMengajar);
        recyclerViewKerja       = (RecyclerView)view.findViewById(R.id.recyclerViewKerja);
        recyclerViewPendidikan  = (RecyclerView)view.findViewById(R.id.recyclerViewPendidikan);
        recyclerViewDokumen     = (RecyclerView)view.findViewById(R.id.recyclerViewDokumen);
        swipeRefreshLayout      = view.findViewById(R.id.swipe_pengalaman);
        llMengajar              = (LinearLayout) view.findViewById(R.id.rl_mengajar2);
        judul                   = (TextView) view.findViewById(R.id.judul);


        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent);

        refresh();

        pengalamanMengajarAdapter = new PengalamanMengajarAdapter(getActivity(), pengalamanMengajars);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewMengajar.setLayoutManager(layoutManager);
        recyclerViewMengajar.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMengajar.setAdapter(pengalamanMengajarAdapter);
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

        sessionManager  = new SessionManager(getActivity());
        getGuruId = Integer.parseInt(sessionManager.getGuruProfile().get("id_guru"));

        loadDataPengalamanMengajarGuruId();

        loadDataPengalamanKerjaGuruId();

        loadDataRiwayatPendidkanGuruId();

        loadDataDokumenGuruId();

        data = new PengalamanMengajar();

        btPengalamanMengajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFromDialogPengalamanMengajar();
            }
        });

        btPengalamanKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFromDialogPengalamanKerja();
            }
        });

        btRiwayatPendidikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFromDialogRiwayatPendidikan();
            }
        });

        btDokumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFromDialogDokumen();
            }
        });
    }

    private void refresh() {
//        pengalamanMengajars.clear();
//        pengalamanKerjas.clear();
//        riwayatPendidikans.clear();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        loadDataPengalamanKerjaGuruId();
                        loadDataPengalamanMengajarGuruId();
                        loadDataRiwayatPendidkanGuruId();
                        loadDataDokumenGuruId();

                    }
                }, 3000);
            }
        });

    }

    public void loadDataPengalamanMengajarGuruId() {
//        System.out.println("ID :"+getGuruId);
        SessionManager sm;


        sm  = new SessionManager(getActivity());
        int id = Integer.parseInt(sessionManager.getGuruProfile().get("id_guru"));
        apiService.getDataPengalamanMengajarByGuruId(id).enqueue(new Callback<ResponseDataPengalamanMengajar>() {
            @Override
            public void onResponse(Call<ResponseDataPengalamanMengajar> call, Response<ResponseDataPengalamanMengajar> response) {
                System.out.println("Responya :"+response.toString());
                if (response.isSuccessful()){
                    if (response.body().getMaster().size() > 0)
                    pengalamanMengajars = response.body().getMaster();
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewMengajar.setLayoutManager(layoutManager);
                    recyclerViewMengajar.setItemAnimator(new DefaultItemAnimator());
                    pengalamanMengajarAdapter = new PengalamanMengajarAdapter(getActivity(), pengalamanMengajars);
                    recyclerViewMengajar.setAdapter(pengalamanMengajarAdapter);
                    recyclerViewMengajar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseDataPengalamanMengajar> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void loadDataPengalamanKerjaGuruId() {
//        System.out.println("ID :"+getIdPengalamanMengajar);
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
        apiService.getDataPengalamanKerjaByGuruId(getGuruId).enqueue(new Callback<ResponsePengalamanKerja>() {
            @Override
            public void onResponse(Call<ResponsePengalamanKerja> call, Response<ResponsePengalamanKerja> response) {
                System.out.println("Responya :"+response.toString());
                if (response.isSuccessful()){
                    if (response.body().getMaster().size() > 0)
                    pengalamanKerjas = response.body().getMaster();
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewKerja.setLayoutManager(layoutManager);
                    recyclerViewKerja.setItemAnimator(new DefaultItemAnimator());
                    pengalamanKerjaAdapter = new PengalamanKerjaAdapter(getActivity(), pengalamanKerjas);
                    recyclerViewKerja.setAdapter(pengalamanKerjaAdapter);
                    recyclerViewKerja.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponsePengalamanKerja> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void loadDataRiwayatPendidkanGuruId() {
//        System.out.println("ID :"+getIdPengalamanMengajar);
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
        apiService.getDataRiwayatPendidikanByGuruId(getGuruId).enqueue(new Callback<ResponseRiwayatPendidikan>() {
            @Override
            public void onResponse(Call<ResponseRiwayatPendidikan> call, Response<ResponseRiwayatPendidikan> response) {
                System.out.println("Responya :"+response.toString());
                if (response.isSuccessful()){
                    if (response.body().getMaster().size() > 0)
                        riwayatPendidikans = response.body().getMaster();
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewPendidikan.setLayoutManager(layoutManager);
                    recyclerViewPendidikan.setItemAnimator(new DefaultItemAnimator());
                    riwayatPendidikanAdapter = new RiwayatPendidikanAdapter(getActivity(), riwayatPendidikans);
                    recyclerViewPendidikan.setAdapter(riwayatPendidikanAdapter);
                    recyclerViewPendidikan.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseRiwayatPendidikan> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void loadDataDokumenGuruId() {
//        System.out.println("ID :"+getIdPengalamanMengajar);
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
        apiService.getDataDokumenByGuruId(getGuruId).enqueue(new Callback<ResponseListDokumen>() {
            @Override
            public void onResponse(Call<ResponseListDokumen> call, Response<ResponseListDokumen> response) {
                System.out.println("Responya :"+response.toString());
                if (response.isSuccessful()){
                    if (response.body().getMaster().size() > 0)
                        dokumens = response.body().getMaster();
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewDokumen.setLayoutManager(layoutManager);
                    recyclerViewDokumen.setItemAnimator(new DefaultItemAnimator());
                    dokumenAdapter = new DokumenAdapter(getActivity(), dokumens);
                    recyclerViewDokumen.setAdapter(dokumenAdapter);
                    recyclerViewDokumen.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseListDokumen> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void showFromDialogPengalamanMengajar() {
    dialog      = new AlertDialog.Builder(getActivity());
    inflater    = getLayoutInflater();
    dialogView  = inflater.inflate(R.layout.form_pengalaman_mengajar, null);
    initDataPengalamanMengajar();
        dialog
            .setView(dialogView)
            .setCancelable(false)
            .setPositiveButton(R.string.simpan, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            saveDataPengalamaMengajar();
        }
    })
            .setNegativeButton(R.string.batal ,new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            // jika tombol ini diklik, akan menutup dialog
            // dan tidak terjadi apa2
            dialog.cancel();
        }
    });

    // membuat alert dialog dari builder
    alertDialog = dialog.create();

    // menampilkan alert dialog
        alertDialog.show();

}

    private void showFromDialogPengalamanKerja() {
        dialog          = new AlertDialog.Builder(getActivity());
        inflater        = getLayoutInflater();
        dialogView      = inflater.inflate(R.layout.form_pengalaman_kerja, null);
        initDataPengalamanKerja();
        dialog
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.simpan, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveDataPengalamaKerja();
                    }
                })
                .setNegativeButton(R.string.batal ,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        alertDialog = dialog.create();

        // menampilkan alert dialog
        alertDialog.show();

    }

    private void showFromDialogRiwayatPendidikan() {
        dialog          = new AlertDialog.Builder(getActivity());
        inflater        = getLayoutInflater();
        dialogView      = inflater.inflate(R.layout.form_riwayat_pendidikan, null);
        initDataRiwayatPendidikan();
        dialog
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.simpan, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveDataRiwayatPendidikan();
                    }
                })
                .setNegativeButton(R.string.batal ,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        alertDialog = dialog.create();

        // menampilkan alert dialog
        alertDialog.show();

    }

    private void showFromDialogDokumen() {
        dialog          = new AlertDialog.Builder(getActivity());
        inflater        = getLayoutInflater();
        dialogView      = inflater.inflate(R.layout.form_dokumen, null);
        initDataDokumen();
        dialog
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.simpan, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveDataDokumen();
                    }
                })
                .setNegativeButton(R.string.batal ,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        alertDialog = dialog.create();

        // menampilkan alert dialog
        alertDialog.show();

    }

    private void initDataDokumen() {
        etFormGuruIdDokumen         = dialogView.findViewById(R.id.etFormGuruIdDokumen);
        spinnerFormJenisDokumen     = (Spinner)dialogView.findViewById(R.id.etFormJenisDokumen);
        etFormNamaDokumen           = dialogView.findViewById(R.id.etFormNamaDokume);
        etFormTahunDokumen          = dialogView.findViewById(R.id.etFormTahun);
        ShowSelectedImage            = (ImageView)dialogView.findViewById(R.id.imageViewDokumen);
        byteArrayOutputStream       = new ByteArrayOutputStream();
        GetImageFromGalleryButton   = (Button)dialogView.findViewById(R.id.button);

        GetImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });

        etFormGuruIdDokumen.setText(sessionManager.getGuruProfile().get("id_guru"));

        List<String> dokumen = new ArrayList<String>();
        dokumen.add("Penghargaan");
        dokumen.add("Sertifikat");
        dokumen.add("Curriculum Vitae");

        spinnerFormJenisDokumen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedName = parent.getItemAtPosition(position).toString();
//                Toast.makeText(context, "Kamu memilih " + selectedName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapterJI = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, dokumen);
        adapterJI.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFormJenisDokumen.setPrompt("Pilih Jenis Dokumen");
        spinnerFormJenisDokumen.setAdapter( adapterJI);

    }

    private void initDataPengalamanMengajar() {
        etFormMengajarPengalaman  = dialogView.findViewById(R.id.etFormPengalamanMengajar);
        etFormMengajarGuruId      = dialogView.findViewById(R.id.etFormGuruId);
        etFormMengajarTglMasuk    = dialogView.findViewById(R.id.etFormPengalamanTglMasuk);
        etFormMengajarTglKeluar   = dialogView.findViewById(R.id.etFormPengalamanTglKeluar);

        etFormMengajarGuruId.setText(sessionManager.getGuruProfile().get("id_guru"));

        etFormMengajarTglMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDateTglMasuk();
            }

            private void showDialogDateTglMasuk() {
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
                        etFormMengajarTglMasuk.setText(sdf.format(newDate.getTime()));
                    }
                },
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

                /**
                 * Tampilkan DatePicker dialog
                 */
                datePickerDialog.show();;
            }
        });

        etFormMengajarTglKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDateTglMasuk();
            }

            private void showDialogDateTglMasuk() {
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
                        etFormMengajarTglKeluar.setText(sdf.format(newDate.getTime()));
                    }
                },
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

                /**
                 * Tampilkan DatePicker dialog
                 */
                datePickerDialog.show();;
            }
        });
    }

    private void initDataPengalamanKerja() {

        etFormKerjaGuruId      = dialogView.findViewById(R.id.etFormKerjaGuruId);
        etFormKerjaJabatan     = dialogView.findViewById(R.id.etFormKerjaJabatan);
        etFormKerjaPerusahaan  = dialogView.findViewById(R.id.etFormKerjaPerusahaan);
        etFormKerjaTglMasuk    = dialogView.findViewById(R.id.etFormKerjaTglMasuk);
        etFormKerjaTglKeluar   = dialogView.findViewById(R.id.etFormKerjaTglKeluar);

        etFormKerjaGuruId.setText(sessionManager.getGuruProfile().get("id_guru"));

        etFormKerjaTglMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogKerjaTglMasuk();
            }

            private void showDialogKerjaTglMasuk() {
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
                        etFormKerjaTglMasuk.setText(sdf.format(newDate.getTime()));
                    }
                },
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

                /**
                 * Tampilkan DatePicker dialog
                 */
                datePickerDialog.show();;
            }
        });

        etFormKerjaTglKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDateTglMasuk();
            }

            private void showDialogDateTglMasuk() {
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
                        etFormKerjaTglKeluar.setText(sdf.format(newDate.getTime()));
                    }
                },
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

                /**
                 * Tampilkan DatePicker dialog
                 */
                datePickerDialog.show();;
            }
        });
    }

    private void initDataRiwayatPendidikan() {
        etFormGuruId                = dialogView.findViewById(R.id.etFormGuruId);
        etFormGelar                 = dialogView.findViewById(R.id.etFormGelar);
        spinnerFormJenisInstitusi   = (Spinner)dialogView.findViewById(R.id.etFormJenisInstitusi);
        etFormNamaInstitusi         = dialogView.findViewById(R.id.etFormNamaInstitusi);
        etFormJurusan               = dialogView.findViewById(R.id.etFormJurusan);
        etFormTahunMasuk            = dialogView.findViewById(R.id.etFormTahunMasuk);
        etFormTahunSelesai          = dialogView.findViewById(R.id.etFormTahunSelesai);
        ShowSelectedImage           = (ImageView)dialogView.findViewById(R.id.imageView);
        byteArrayOutputStream       = new ByteArrayOutputStream();
        GetImageFromGalleryButton = (Button)dialogView.findViewById(R.id.button);

        GetImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

            }
        });

        etFormGuruId.setText(sessionManager.getGuruProfile().get("id_guru"));

        List<String> jenis_institusi = new ArrayList<String>();
        jenis_institusi.add("Dalam Negeri");
        jenis_institusi.add("Luar Negeri");

        spinnerFormJenisInstitusi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedName = parent.getItemAtPosition(position).toString();
//                Toast.makeText(context, "Kamu memilih " + selectedName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapterJI = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, jenis_institusi);
        adapterJI.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFormJenisInstitusi.setPrompt("Pilih Jenis Institusi");
        spinnerFormJenisInstitusi.setAdapter( adapterJI);
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

    private void saveDataPengalamaKerja() {

        loading = new ProgressDialog(getActivity());
        loading.setCancelable(false);
        loading.setMessage("Loading...");
        loading.show();

        int kerja_guru_id         = getGuruId;
        String kerja_jabatan      = etFormKerjaJabatan.getText().toString();
        String kerja_perusahaan   = etFormKerjaPerusahaan.getText().toString();
        String kerja_tgl_masuk    = etFormKerjaTglMasuk.getText().toString();
        String kerja_tgl_selesai  = etFormKerjaTglKeluar.getText().toString();

        apiService.createPengalamankerja(kerja_guru_id, kerja_jabatan, kerja_perusahaan,kerja_tgl_masuk, kerja_tgl_selesai).enqueue(new Callback<ResponseCreatePengalamanKerja>() {
            @Override
            public void onResponse(Call<ResponseCreatePengalamanKerja> call, Response<ResponseCreatePengalamanKerja> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    if (response.body().getCode().equals(200)){
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        alertDialog.cancel();
                        loadDataPengalamanKerjaGuruId();
                    }else{
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCreatePengalamanKerja> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void saveDataPengalamaMengajar() {

        loading = new ProgressDialog(getActivity());
        loading.setCancelable(false);
        dialog.setMessage("Loading...");
        loading.show();

        int guru_id         = getGuruId;
        String pengalaman   = etFormMengajarPengalaman.getText().toString();
        String tgl_masuk    = etFormMengajarTglMasuk.getText().toString();
        String tgl_selesai  = etFormMengajarTglKeluar.getText().toString();

        System.out.println("Idnya :"+guru_id);
        apiService.createPengalamanMengajar(guru_id, pengalaman, tgl_masuk, tgl_selesai).enqueue(new Callback<ResponsePengalamanMengajar>() {
            @Override
            public void onResponse(Call<ResponsePengalamanMengajar> call, Response<ResponsePengalamanMengajar> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    if (response.body().getCode().equals(200)){
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        alertDialog.cancel();
                        loadDataPengalamanMengajarGuruId();

                    }else{
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePengalamanMengajar> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void saveDataRiwayatPendidikan() {

        loading = new ProgressDialog(getActivity());
        loading.setCancelable(false);
        dialog.setMessage("Loading...");
        loading.show();

        guru_id            = String.valueOf(getGuruId);
        gelar              = etFormGelar.getText().toString();
        jenis_institusi    = spinnerFormJenisInstitusi.getSelectedItem().toString();
        nama_institusi     = etFormNamaInstitusi.getText().toString();
        jurusan            = etFormJurusan.getText().toString();
        tahun_masuk        = etFormTahunMasuk.getText().toString();
        tahun_selesai      = etFormTahunSelesai.getText().toString();

        UploadImageToServer();
    }

    private void saveDataDokumen() {

        loading = new ProgressDialog(getActivity());
        loading.setCancelable(false);
        dialog.setMessage("Loading...");
        loading.show();

        guru_id_dokumen    = String.valueOf(getGuruId);
        jenis_dokumen      = spinnerFormJenisDokumen.getSelectedItem().toString();
        nama_dokumen       = etFormNamaDokumen.getText().toString();
        tahun              = etFormTahunDokumen.getText().toString();

        UploadImageDokumenToServer();
    }

    public void UploadImageToServer(){

        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();

        ConvertImage = android.util.Base64.encodeToString(byteArray, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(getActivity(),"Berhasil...","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                progressDialog.dismiss();
                loading.dismiss();
                loadDataRiwayatPendidkanGuruId();
                Toast.makeText(getActivity(),string1,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(GURU_ID, guru_id);
                HashMapParams.put(GELAR, gelar);
                HashMapParams.put(JENIS_INSTITUSI, jenis_institusi);
                HashMapParams.put(NAMA_INSTITUSI, nama_institusi);
                HashMapParams.put(JURUSAN, jurusan);
                HashMapParams.put(TAHUN_MASUK, tahun_masuk);
                HashMapParams.put(TAHUN_SELESAI, tahun_selesai);

                HashMapParams.put(PHOTO_IJAZAH, ConvertImage);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public void UploadImageDokumenToServer(){

        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();

        ConvertImage = android.util.Base64.encodeToString(byteArray, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(getActivity(),"Berhasil...","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                progressDialog.dismiss();
                loading.dismiss();
                loadDataDokumenGuruId();
                Toast.makeText(getActivity(),string1,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(GURU_ID, guru_id_dokumen);
                HashMapParams.put(JENIS_DOKUMEN, jenis_dokumen);
                HashMapParams.put(NAMA_DOKUMEN, nama_dokumen);
                HashMapParams.put(TAHUN, tahun);

                HashMapParams.put(PHOTO_DOKUMEN, ConvertImage);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadDokumenPath, HashMapParams);

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
