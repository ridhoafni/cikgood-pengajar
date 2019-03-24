package ui.fragments;


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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.adapters.BahanAjarMatpelJoinAdapter;
import com.example.anonymous.CikgoodPengajar.adapters.GuruJadwalAdapter;
import com.example.anonymous.CikgoodPengajar.config.ServerConfig;
import com.example.anonymous.CikgoodPengajar.models.GuruJadwal;
import com.example.anonymous.CikgoodPengajar.response.ResponseCreateJadwal;
import com.example.anonymous.CikgoodPengajar.response.ResponseDaftarBahanAjarJoin;
import com.example.anonymous.CikgoodPengajar.response.ResponseGuruJadwal;
import com.example.anonymous.CikgoodPengajar.rests.ApiClient;
import com.example.anonymous.CikgoodPengajar.rests.ApiInterface;
import com.example.anonymous.CikgoodPengajar.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class JadwalFragment extends Fragment {


    public JadwalFragment() {
        // Required empty public constructor
    }

    Button btnJadwal;
    CheckBox cbPagi, cbSiang, cbMalam;
    ProgressDialog loading;
    AlertDialog.Builder dialog;
    ApiInterface apiService;
    LayoutInflater inflater;
    SessionManager sessionManager;
    AlertDialog alertDialog;
    RecyclerView recyclerViewJadwal;
    String jam;
    View dialogView;
    Spinner spinnerHari;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<String> jams ;
    GuruJadwalAdapter guruJadwalAdapter;
    List<GuruJadwal> guruJadwals = new ArrayList<>();
    int guru_id;
    String[] array_nama_hari = {"Senin", "Selasa", "Rabu", "Kamis", "Jum'at", "Sabtu", "Minggu"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jadwal, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btnJadwal = (Button)view.findViewById(R.id.btn_jadwal);
        recyclerViewJadwal = (RecyclerView)view.findViewById(R.id.recyclerViewJadwal);
        swipeRefreshLayout = view.findViewById(R.id.swipe_jadwal);
        btnJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFromDialogJadwal();
            }
        });
        sessionManager = new SessionManager(getActivity());
        guru_id = Integer.parseInt(sessionManager.getGuruProfile().get("id_guru"));
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
        loadDataJadwalGuruId();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent);
        refresh();
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
                        loadDataJadwalGuruId();

                    }
                }, 3000);
            }
        });

    }


    private void showFromDialogJadwal() {
        dialog      = new AlertDialog.Builder(getActivity());
        inflater    = getLayoutInflater();
        dialogView  = inflater.inflate(R.layout.form_jadwal, null);
        initDataJadwal();
        dialog
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.simpan, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveDataJadwal();
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

    private void initDataJadwal() {
        spinnerHari = (Spinner)dialogView.findViewById(R.id.spinnerHari);
        cbPagi  = (CheckBox)dialogView.findViewById(R.id.cb_pagi);
        cbSiang = (CheckBox)dialogView.findViewById(R.id.cb_siang);
        cbMalam = (CheckBox)dialogView.findViewById(R.id.cb_malam);
        initSpinnerHari();
        loadDataJadwalGuruId();
    }

    private void loadDataJadwalGuruId() {
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
        apiService.getDataJadwalByGuruId(guru_id).enqueue(new Callback<ResponseGuruJadwal>() {
            @Override
            public void onResponse(Call<ResponseGuruJadwal> call, Response<ResponseGuruJadwal> response) {
                System.out.println("Responya :"+response.toString());
                if (response.isSuccessful()){
                    System.out.println("lOADING :"+response.body().getMaster().toString());
                    if (response.body().getMaster().size() > 0)
                        guruJadwals = response.body().getMaster();
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerViewJadwal.setLayoutManager(layoutManager);
                    recyclerViewJadwal.setItemAnimator(new DefaultItemAnimator());
                    guruJadwalAdapter = new GuruJadwalAdapter(getActivity(), guruJadwals);
                    recyclerViewJadwal.setAdapter(guruJadwalAdapter);
                    recyclerViewJadwal.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseGuruJadwal> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initSpinnerHari() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, array_nama_hari);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHari.setPrompt("Pilih Nama Hari");
        spinnerHari.setAdapter( adapter);
    }

//    public void onCheckbox(View view){}

    private void saveDataJadwal() {
        loading = new ProgressDialog(getActivity());
        loading.setCancelable(false);
        loading.setMessage("Loading...");
        loading.show();

        String hari = spinnerHari.getSelectedItem().toString();
        List<CheckBox> items = new ArrayList<CheckBox>();
//        for (CheckBox item : items){
//            if (item.isChecked()){
//                jam = item.getText().toString();
//                System.out.println("ITEMS"+jam);
//                Toast.makeText(getContext(), " "+jam, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), " "+hari, Toast.LENGTH_SHORT).show();
//            }
//        }
        if (cbPagi.isChecked())
            jam = cbPagi.getText().toString();
        if (cbSiang.isChecked())
            jam = cbSiang.getText().toString();
        if (cbMalam.isChecked())
            jam = cbMalam.getText().toString();
        if (cbPagi.isChecked() && cbSiang.isChecked())
            jam = cbPagi.getText().toString()+", "+cbSiang.getText().toString();
        if (cbPagi.isChecked() && cbMalam.isChecked())
            jam = cbPagi.getText().toString()+", "+cbMalam.getText().toString();
        if (cbSiang.isChecked() && cbMalam.isChecked())
            jam = cbSiang.getText().toString()+", "+cbMalam.getText().toString();



        System.out.println("Guru ID "+guru_id);
        System.out.println("Hari "+hari);
        System.out.println("Jam "+jam);

        apiService.createJadwal(guru_id, hari, jam).enqueue(new Callback<ResponseCreateJadwal>() {
            @Override
            public void onResponse(Call<ResponseCreateJadwal> call, Response<ResponseCreateJadwal> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    if (response.body().getCode().equals(200)){
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        loadDataBahanAjarMatpelGuruId();
                    }else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCreateJadwal> call, Throwable t) {

            }
        });

    }

}
