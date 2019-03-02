package ui.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.adapters.CACKabupatenAdapter;
import com.example.anonymous.CikgoodPengajar.adapters.CACKecamatanAdapter;
import com.example.anonymous.CikgoodPengajar.adapters.CAProvinsiAdapter;
import com.example.anonymous.CikgoodPengajar.adapters.DokumenAdapter;
import com.example.anonymous.CikgoodPengajar.adapters.LokasiAdapter;
import com.example.anonymous.CikgoodPengajar.config.ServerConfig;
import com.example.anonymous.CikgoodPengajar.models.Kabupaten;
import com.example.anonymous.CikgoodPengajar.models.Kecamatan;
import com.example.anonymous.CikgoodPengajar.models.Lokasi;
import com.example.anonymous.CikgoodPengajar.models.SemuaProvinsi;
import com.example.anonymous.CikgoodPengajar.response.ModelKabupaten;
import com.example.anonymous.CikgoodPengajar.response.ModelKecamatan;
import com.example.anonymous.CikgoodPengajar.response.ModelProvinsi;
import com.example.anonymous.CikgoodPengajar.response.ResponseFindLokasiById;
import com.example.anonymous.CikgoodPengajar.response.ResponseListDokumen;
import com.example.anonymous.CikgoodPengajar.response.ResponseLokasi;
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
    RecyclerView recyclerViewLokasi, recyclerViewMatpel;
    List<Lokasi> lokasis = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    LokasiAdapter lokasiAdapter;
    ProvInterface provInterface;
    SessionManager sessionManager;
    ProgressDialog loading;
    AlertDialog.Builder dialog;
    ApiInterface apiService;
    LayoutInflater inflater;
    AlertDialog alertDialog;
    View dialogView;
    int guruId;

    List<SemuaProvinsi> provinsiArrayList = new ArrayList<>();

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
        recyclerViewLokasi = (RecyclerView)view.findViewById(R.id.recyclerViewLokasi);
        swipeRefreshLayout      = view.findViewById(R.id.swipe_pengalaman);
        sessionManager = new SessionManager(getActivity());
        btnLokasiMengajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFromDialogLokasi();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent);

        refresh();

        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

        guruId = Integer.parseInt(sessionManager.getGuruProfile().get("id_guru"));
        int id = Integer.parseInt(sessionManager.getGuruProfile().get("id_guru"));

        loadDataLokasiGuruId();

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

                    }
                }, 3000);
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

    @SuppressLint("WrongViewCast")
    private void initDataLokasi() {
        acProvinsi  = (AutoCompleteTextView) dialogView.findViewById(R.id.autoCompleteProvinsi);
        acKabupaten = (AutoCompleteTextView) dialogView.findViewById(R.id.autoCompleteKabupaten);
        acKecamatan = (AutoCompleteTextView) dialogView.findViewById(R.id.autoCompleteKecamatan);

        loadProvinsi();
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
