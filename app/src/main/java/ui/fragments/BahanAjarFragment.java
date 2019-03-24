package ui.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.adapters.BahanAjarMatpelJoinAdapter;
import com.example.anonymous.CikgoodPengajar.adapters.CACKabupatenAdapter;
import com.example.anonymous.CikgoodPengajar.adapters.CACKecamatanAdapter;
import com.example.anonymous.CikgoodPengajar.adapters.CAProvinsiAdapter;
import com.example.anonymous.CikgoodPengajar.adapters.DokumenAdapter;
import com.example.anonymous.CikgoodPengajar.adapters.LokasiAdapter;
import com.example.anonymous.CikgoodPengajar.config.ServerConfig;
import com.example.anonymous.CikgoodPengajar.models.BahanAjarMatpelJoin;
import com.example.anonymous.CikgoodPengajar.models.DataMatpel;
import com.example.anonymous.CikgoodPengajar.models.Kabupaten;
import com.example.anonymous.CikgoodPengajar.models.Kecamatan;
import com.example.anonymous.CikgoodPengajar.models.Lokasi;
import com.example.anonymous.CikgoodPengajar.models.MasterMatpel;
import com.example.anonymous.CikgoodPengajar.models.SemuaProvinsi;
import com.example.anonymous.CikgoodPengajar.response.ModelKabupaten;
import com.example.anonymous.CikgoodPengajar.response.ModelKecamatan;
import com.example.anonymous.CikgoodPengajar.response.ModelProvinsi;
import com.example.anonymous.CikgoodPengajar.response.ResponseCreateBahanAjarMatpel;
import com.example.anonymous.CikgoodPengajar.response.ResponseDaftarBahanAjarJoin;
import com.example.anonymous.CikgoodPengajar.response.ResponseDataMatpel;
import com.example.anonymous.CikgoodPengajar.response.ResponseFindLokasiById;
import com.example.anonymous.CikgoodPengajar.response.ResponseListDokumen;
import com.example.anonymous.CikgoodPengajar.response.ResponseLokasi;
import com.example.anonymous.CikgoodPengajar.response.ResponseMasterMatpel;
import com.example.anonymous.CikgoodPengajar.response.ResponseMatpel;
import com.example.anonymous.CikgoodPengajar.rests.ApiClient;
import com.example.anonymous.CikgoodPengajar.rests.ApiInterface;
import com.example.anonymous.CikgoodPengajar.rests.CombineApi;
import com.example.anonymous.CikgoodPengajar.rests.ProvInterface;
import com.example.anonymous.CikgoodPengajar.utils.SessionManager;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BahanAjarFragment extends Fragment {
    AutoCompleteTextView acProvinsi, acKabupaten, acKecamatan;
    String provinsi, kabupaten, kecamatan;
    Button btnLokasiMengajar, btnMataPelajaran;
    EditText etTarif;
    RecyclerView recyclerViewLokasi, recyclerViewMatpel;
    List<Lokasi> lokasis = new ArrayList<>();
    List<BahanAjarMatpelJoin> bahanAjarMatpelJoins = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    LokasiAdapter lokasiAdapter;
    BahanAjarMatpelJoinAdapter bahanAjarMatpelJoinAdapter;
    ProvInterface provInterface;
    SessionManager sessionManager;
    ProgressDialog loading;
    AlertDialog.Builder dialog;
    ApiInterface apiService;
    LayoutInflater inflater;
    AlertDialog alertDialog;
    View dialogView;
    int guruId, idDataMatpel, idMatpel;
    Context context;

    Spinner spinnerMasterMatpel, spinnerDataMapel;

    List<SemuaProvinsi> provinsiArrayList = new ArrayList<>();

    List<MasterMatpel> masterMatpelArrayList = new ArrayList<>();

    List<Kabupaten> kabupatenArrayList = new ArrayList<>();

    List<Kecamatan> kecamatanArrayList = new ArrayList<>();

    public BahanAjarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bahan_ajar, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btnLokasiMengajar   = (Button)view.findViewById(R.id.btn_lokasi_mengajar);
        btnMataPelajaran    = (Button)view.findViewById(R.id.btn_matapel);
        recyclerViewLokasi  = (RecyclerView)view.findViewById(R.id.recyclerViewLokasi);
        recyclerViewMatpel  = (RecyclerView)view.findViewById(R.id.recyclerViewMatpel);
        swipeRefreshLayout  = (SwipeRefreshLayout) view.findViewById(R.id.swipe_bahan_ajar);
        sessionManager      = new SessionManager(getActivity());
        btnLokasiMengajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFromDialogLokasi();
            }
        });

        btnMataPelajaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFromDialogMatpel();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent);

        refresh();

        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

        guruId = Integer.parseInt(sessionManager.getGuruProfile().get("id_guru"));
        int id = Integer.parseInt(sessionManager.getGuruProfile().get("id_guru"));

        loadDataLokasiGuruId();
        loadDataBahanAjarMatpelGuruId();
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
                        loadDataLokasiGuruId();
                        loadDataBahanAjarMatpelGuruId();
                    }
                }, 3000);
            }
        });

    }

    private void loadDataBahanAjarMatpelGuruId() {
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
        apiService.getBahanAjarMatpelJoin(guruId).enqueue(new Callback<ResponseDaftarBahanAjarJoin>() {
            @Override
            public void onResponse(Call<ResponseDaftarBahanAjarJoin> call, Response<ResponseDaftarBahanAjarJoin> response) {
                System.out.println("Responya :"+response.toString());
                if (response.isSuccessful()){
                    System.out.println("lOADING :"+response.body().getMaster().toString());
                    if (response.body().getMaster().size() > 0)
                        bahanAjarMatpelJoins = response.body().getMaster();
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewMatpel.setLayoutManager(layoutManager);
                    recyclerViewMatpel.setItemAnimator(new DefaultItemAnimator());
                    bahanAjarMatpelJoinAdapter = new BahanAjarMatpelJoinAdapter(getActivity(), bahanAjarMatpelJoins);
                    recyclerViewMatpel.setAdapter(bahanAjarMatpelJoinAdapter);
                    recyclerViewMatpel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseDaftarBahanAjarJoin> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void loadDataLokasiGuruId() {
//        System.out.println("ID :"+getIdPengalamanMengajar);
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
        apiService.getDataLokasiByGuruId(guruId).enqueue(new Callback<ResponseFindLokasiById>() {
            @Override
            public void onResponse(Call<ResponseFindLokasiById> call, Response<ResponseFindLokasiById> response) {
                System.out.println("Responya :"+response.toString());
                if (response.isSuccessful()){
                    System.out.println("lOADING :"+response.body().getMaster().toString());
                    if (response.body().getMaster().size() > 0)
                        lokasis = response.body().getMaster();
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewLokasi.setLayoutManager(layoutManager);
                    recyclerViewLokasi.setItemAnimator(new DefaultItemAnimator());
                    lokasiAdapter = new LokasiAdapter(getActivity(), lokasis);
                    recyclerViewLokasi.setAdapter(lokasiAdapter);
                    recyclerViewLokasi.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseFindLokasiById> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void showFromDialogLokasi() {
        dialog      = new AlertDialog.Builder(getActivity());
        inflater    = getLayoutInflater();
        dialogView  = inflater.inflate(R.layout.form_bahan_ajar_lokasi, null);
        initDataLokasi();
        dialog
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.simpan, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveDataLokasi();
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

    private void showFromDialogMatpel() {
        dialog      = new AlertDialog.Builder(getActivity());
        inflater    = getLayoutInflater();
        dialogView  = inflater.inflate(R.layout.form_mata_pelajaran, null);
        initDataMatpel();
//        loadSpinnerMatpel();
        dialog
//        loadSpinnerMatpel();
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.simpan, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveDataMatpel();
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

    private void saveDataMatpel() {
        loading = new ProgressDialog(getActivity());
        loading.setCancelable(false);
        loading.setMessage("Loading...");
        loading.show();

        guruId = Integer.parseInt(sessionManager.getGuruProfile().get("id_guru"));
        String masterMatpel     = spinnerMasterMatpel.getSelectedItem().toString();
        String dataMatpel       = spinnerDataMapel.getSelectedItem().toString();
        int tarif               = Integer.parseInt(etTarif.getText().toString());

        System.out.println("ID :"+guruId);
        System.out.println("Matpel :"+idMatpel);
        System.out.println("Tarif :"+tarif);

        apiService.createBahanAjarMatpel(guruId, dataMatpel, tarif).enqueue(new Callback<ResponseCreateBahanAjarMatpel>() {
            @Override
            public void onResponse(Call<ResponseCreateBahanAjarMatpel> call, Response<ResponseCreateBahanAjarMatpel> response) {
                loading.dismiss();
                if (response.isSuccessful()){
                    if (response.body().getCode().equals(200)){
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        loadDataBahanAjarMatpelGuruId();
                    }else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCreateBahanAjarMatpel> call, Throwable t) {

            }
        });
    }

    @SuppressLint("WrongViewCast")
    private void initDataLokasi() {
        acProvinsi  = (AutoCompleteTextView) dialogView.findViewById(R.id.autoCompleteProvinsi);
        acKabupaten = (AutoCompleteTextView) dialogView.findViewById(R.id.autoCompleteKabupaten);
        acKecamatan = (AutoCompleteTextView) dialogView.findViewById(R.id.autoCompleteKecamatan);

        loadProvinsi();
    }

    private void initDataMatpel() {

        spinnerMasterMatpel  = (Spinner) dialogView.findViewById(R.id.spinnerMatpel);
        spinnerDataMapel     = (Spinner) dialogView.findViewById(R.id.spinnerMatpelDetail);
        etTarif              = (EditText)dialogView.findViewById(R.id.etTarif);

        initSpinnerMatpel();
    }

    private void initSpinnerMatpel(){
        loading = ProgressDialog.show(getActivity(), null, "harap tunggu...", true, false);

        apiService.masterMatpelFindAll().enqueue(new Callback<ResponseMasterMatpel>() {
            @Override
            public void onResponse(Call<ResponseMasterMatpel> call, Response<ResponseMasterMatpel> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    List<MasterMatpel> semuadosenItems = response.body().getMaster();
                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < semuadosenItems.size(); i++){
                        listSpinner.add(semuadosenItems.get(i).getNama());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerMasterMatpel.setAdapter(adapter);
                    spinnerMasterMatpel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedName = parent.getItemAtPosition(position).toString();
//                           requestDetailDosen(selectedName);

//                            Toast.makeText(getActivity(), "Kamu memilih " + selectedName, Toast.LENGTH_SHORT).show();

                            for (MasterMatpel semuamastermatpel : semuadosenItems){


                                if (semuamastermatpel.getNama().equals(spinnerMasterMatpel.getSelectedItem().toString())){
                                    loadDataMatpel(semuamastermatpel.getIdMasterMatpel());
                                    idMatpel = semuamastermatpel.getIdMasterMatpel();
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    loading.dismiss();
                    Toast.makeText(getActivity(), "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMasterMatpel> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadDataMatpel(Integer idDataMatpel) {
        loading = ProgressDialog.show(getActivity(), null, "harap tunggu...", true, false);

        apiService.getDataMatpelByMatpelId(idDataMatpel).enqueue(new Callback<ResponseDataMatpel>() {
            @Override
            public void onResponse(Call<ResponseDataMatpel> call, Response<ResponseDataMatpel> response) {
                System.out.println("Responnya :"+response);
                if (response.isSuccessful()) {
                    loading.dismiss();
                    List<DataMatpel> semuadatadosenItems = response.body().getMaster();
                    List<String> listSpinnerDataMatpel = new ArrayList<String>();
                    for (int i = 0; i < semuadatadosenItems.size(); i++){
                        listSpinnerDataMatpel.add(semuadatadosenItems.get(i).getMatpelDetail());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_item, listSpinnerDataMatpel);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDataMapel.setAdapter(adapter);

                    spinnerDataMapel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedName = parent.getItemAtPosition(position).toString();
//                            Toast.makeText(context, "Kamu memilih " + selectedName, Toast.LENGTH_SHORT).show();
//                            for (MasterMatpel semuamastermatpel : masterMatpelArrayList){
//                                if (semuamastermatpel.getNama().equals(spinnerMasterMatpel.getSelectedItem().toString())){
//                                    loadDataMatpel(semuamastermatpel.getIdMasterMatpel());
//                                }
//                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    loading.dismiss();
                    Toast.makeText(getActivity(), "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataMatpel> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getActivity(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadProvinsi(){

        provInterface = CombineApi.getApiProv();
        Call<ModelProvinsi> getProvinsi = provInterface.getProvinsi();
        getProvinsi.enqueue(new Callback<ModelProvinsi>() {
            @Override
            public void onResponse(Call<ModelProvinsi> call, Response<ModelProvinsi> response) {
                provinsiArrayList = response.body().getSemuaprovinsi();
                CAProvinsiAdapter adapter = new CAProvinsiAdapter(getActivity(), android.R.layout.select_dialog_item, provinsiArrayList);
                acProvinsi.setAdapter(adapter);
                acProvinsi.setThreshold(0);
                acProvinsi.dismissDropDown();
                acProvinsi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (SemuaProvinsi semuaprovinsi : provinsiArrayList){
                            if (semuaprovinsi.getNama().equals(acProvinsi.getText().toString())){
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

    private void loadKabupaten(String id) {
        provInterface = CombineApi.getApiProv();
        Call<ModelKabupaten> getKabupaten = provInterface.getKabupaten(id);
        getKabupaten.enqueue(new Callback<ModelKabupaten>() {
            @Override
            public void onResponse(Call<ModelKabupaten> call, Response<ModelKabupaten> response) {
                kabupatenArrayList = response.body().getKabupatens();
                CACKabupatenAdapter adapter = new CACKabupatenAdapter(getActivity() ,android.R.layout.select_dialog_item, kabupatenArrayList);
                acKabupaten.setAdapter(adapter);
                acKabupaten.setThreshold(0);
                acKabupaten.dismissDropDown();
                HashMap<String,String> a = new HashMap<String,String>();
                acKabupaten.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (Kabupaten kabupaten : kabupatenArrayList){
                            if (kabupaten.getNama().equals(acKabupaten.getText().toString())){
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
                acKecamatan.setAdapter(adapter);
                HashMap<String,String> a = new HashMap<String,String>();
                acKecamatan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (Kecamatan kecamatan : kecamatanArrayList){
                            if (kecamatan.getNama().equals(acKecamatan.getText().toString())){
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

    private void saveDataLokasi() {

        loading = new ProgressDialog(getActivity());
        loading.setCancelable(false);
        loading.setMessage("Loading...");
        loading.show();

        guruId = Integer.parseInt(sessionManager.getGuruProfile().get("id_guru"));
        provinsi  = acProvinsi.getText().toString();
        kabupaten = acKabupaten.getText().toString();
        kecamatan = acKecamatan.getText().toString();

        System.out.println("Id : "+guruId);
        System.out.println("Provinsi : "+provinsi);
        System.out.println("Kabupaten : "+kabupaten);
        System.out.println("Kecamatan : "+kecamatan);

        apiService.createBahanAjarLokasi(guruId, provinsi, kabupaten, kecamatan).enqueue(new Callback<ResponseLokasi>() {
            @Override
            public void onResponse(Call<ResponseLokasi> call, Response<ResponseLokasi> response) {
                loading.dismiss();
                if (response.isSuccessful()){
                    if (response.body().getCode().equals(200)){
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        loadDataLokasiGuruId();
                    }else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLokasi> call, Throwable t) {

            }
        });
    }


}
