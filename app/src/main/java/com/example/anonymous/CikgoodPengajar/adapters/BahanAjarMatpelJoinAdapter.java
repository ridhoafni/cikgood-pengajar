package com.example.anonymous.CikgoodPengajar.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.config.ServerConfig;
import com.example.anonymous.CikgoodPengajar.models.BahanAjarMatpelJoin;
import com.example.anonymous.CikgoodPengajar.models.Kabupaten;
import com.example.anonymous.CikgoodPengajar.models.Kecamatan;
import com.example.anonymous.CikgoodPengajar.models.Lokasi;
import com.example.anonymous.CikgoodPengajar.models.SemuaProvinsi;
import com.example.anonymous.CikgoodPengajar.response.ModelKabupaten;
import com.example.anonymous.CikgoodPengajar.response.ModelKecamatan;
import com.example.anonymous.CikgoodPengajar.response.ModelProvinsi;
import com.example.anonymous.CikgoodPengajar.response.ResponseLokasi;
import com.example.anonymous.CikgoodPengajar.rests.ApiClient;
import com.example.anonymous.CikgoodPengajar.rests.ApiInterface;
import com.example.anonymous.CikgoodPengajar.rests.CombineApi;
import com.example.anonymous.CikgoodPengajar.rests.ProvInterface;
import com.example.anonymous.CikgoodPengajar.utils.CustomOnItemClickListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.fragments.BahanAjarFragment;

public class BahanAjarMatpelJoinAdapter extends RecyclerView.Adapter<BahanAjarMatpelJoinAdapter.BahanAjarMatpelJoinViewHolder> {
    String guru_id_edit, provinsi_edit, kabupaten_edit, kecamatan_edit;
//    AutoCompleteTextView autoCompleteProvinsi, autoCompleteKabupaten, autoCompleteKecamatan;
    RecyclerView recyclerViewLokasi;
    private List<BahanAjarMatpelJoin> getBahanAjarMatpelByGuruId, bahanAjarMatpelJoins;
    List<SemuaProvinsi> provinsiArrayList = new ArrayList<>();
    List<Kabupaten> kabupatenArrayList = new ArrayList<>();
    List<Kecamatan> kecamatanArrayList = new ArrayList<>();
    LokasiAdapter lokasiAdapter;
    BahanAjarFragment bahanAjarFragment = new BahanAjarFragment();
    ProvInterface provInterface;
    private ApiInterface apiService;
    private ProgressDialog loading;
    AlertDialog.Builder dialog;
    AlertDialog alertDialog;
    LayoutInflater inflater;
    private Context context;
    View dialogView ;
    int id;

    public BahanAjarMatpelJoinAdapter(Context context, List<BahanAjarMatpelJoin> getBahanAjarMatpelByGuruId) {
        this.context = context;
        this.getBahanAjarMatpelByGuruId = getBahanAjarMatpelByGuruId;
    }

    @NonNull
    @Override
    public BahanAjarMatpelJoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_bahan_ajar_matpel, parent, false);
        return new BahanAjarMatpelJoinViewHolder(v);
    }

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    @Override
    public void onBindViewHolder(@NonNull BahanAjarMatpelJoinViewHolder matpelViewHolder, int i) {
        BahanAjarMatpelJoin bahanAjarMatpelJoin = getBahanAjarMatpelByGuruId.get(i);
        matpelViewHolder.tvMatpel.setText(bahanAjarMatpelJoin.getMatpelId());
        int tarif = Integer.parseInt(bahanAjarMatpelJoin.getTarif());
        matpelViewHolder.tvTarif.setText(formatRupiah.format(tarif));

        matpelViewHolder.hapus.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                id = Integer.parseInt(getBahanAjarMatpelByGuruId.get(position).getIdGuruBahanAjarMatpel());
                deleteDataMatpel();
//                loadingDataGuruId();
            }
        }));

        matpelViewHolder.edit.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {

//                id              = Integer.parseInt(getBahanAjarMatpelByGuruId.get(position).getIdGuruBahanAjarMatpel());
//                matapel_id_edit    = String.valueOf(getDataLokasiByGuruId.get(position).getGuruId());
//                provinsi_edit   = getDataLokasiByGuruId.get(position).getProvinsi();
//                kabupaten_edit  = getDataLokasiByGuruId.get(position).getKota();
//                kecamatan_edit  = getDataLokasiByGuruId.get(position).getKecamatan();
//
//                editDataLokasi();

            }
        }));
    }

//    private  void loadingDataGuruId(){
//        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        dialogView  = inflater.inflate(R.layout.fragment_bahan_ajar, null);
//        initDataLoadLokasi();
//
//        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
//        apiService.getDataLokasiByGuruId(Integer.parseInt(guru_id_edit)).enqueue(new Callback<ResponseFindLokasiById>() {
//            @Override
//            public void onResponse(Call<ResponseFindLokasiById> call, Response<ResponseFindLokasiById> response) {
//                System.out.println("Responya :"+response.toString());
//                if (response.isSuccessful()){
//                    if (response.body().getMaster().size() > 0)
//                        lokasis = response.body().getMaster();
//                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
//                    recyclerViewLokasi.setLayoutManager(layoutManager);
//                    recyclerViewLokasi.setItemAnimator(new DefaultItemAnimator());
//                    lokasiAdapter = new LokasiAdapter(context, lokasis);
//                    recyclerViewLokasi.setAdapter(lokasiAdapter);
//                    recyclerViewLokasi.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseFindLokasiById> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }

//    private void initDataLoadLokasi() {
//        recyclerViewLokasi = (RecyclerView) dialogView.findViewById(R.id.recyclerViewLokasi);
//        loadingDataGuruId();
//    }

//    private void editDataLokasi() {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        dialogView  = inflater.inflate(R.layout.form_bahan_ajar_lokasi, null);
//        initDataLokasi();
//        dialog
//                .setView(dialogView)
//                .setCancelable(false)
//                .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        loadProvinsi();
//                        saveDataLokasi();
//                    }
//                })
//                .setNegativeButton(R.string.batal ,new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // jika tombol ini diklik, akan menutup dialog
//                        // dan tidak terjadi apa2
//                        dialog.cancel();
//                    }
//                });
//
//        // membuat alert dialog dari builder
//        AlertDialog alertDialog = dialog.create();
//
//        // menampilkan alert dialog
//        alertDialog.show();
//
//    }

//    private void initDataLokasi() {
//        autoCompleteProvinsi    = dialogView.findViewById(R.id.autoCompleteProvinsi);
//        autoCompleteKabupaten   = dialogView.findViewById(R.id.autoCompleteKabupaten);
//        autoCompleteKecamatan   = dialogView.findViewById(R.id.autoCompleteKecamatan);
//
//        autoCompleteProvinsi.setText(provinsi_edit);
//        autoCompleteKabupaten.setText(kabupaten_edit);
//        autoCompleteKecamatan.setText(kecamatan_edit);
//
////        loadProvinsi();
//    }

//    private void saveDataLokasi() {
//        dialog  = new AlertDialog.Builder(context);
//        loading = new ProgressDialog(context);
//        loading.setCancelable(false);
//        loading.setMessage("Loading...");
//        loading.show();
//
//        Integer id_lokasi_save  = id;
//        Integer guru_id_save    = Integer.valueOf(guru_id_edit);
//        String provinsi_save    = autoCompleteProvinsi.getText().toString();
//        String kabupaten_save   = autoCompleteKabupaten.getText().toString();
//        String kecamatan_save   = autoCompleteKecamatan.getText().toString();
//
//        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
//        apiService.updateBahanAjarLokasi(id_lokasi_save, guru_id_save, provinsi_save, kabupaten_save, kecamatan_save).enqueue(new Callback<ResponseLokasi>() {
//            @Override
//            public void onResponse(Call<ResponseLokasi> call, Response<ResponseLokasi> response) {
//                System.out.println("Responnya :"+response.toString());
//                if (response.isSuccessful()){
//                    loading.dismiss();
//                    if (response.body().getCode().equals(200)){
////                        loadProvinsi();
////                        loadingDataGuruId();
//                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseLokasi> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return getBahanAjarMatpelByGuruId.size();
    }

    public class BahanAjarMatpelJoinViewHolder extends RecyclerView.ViewHolder {
        TextView tvMatpel, tvTarif;
        LinearLayout linearLayout;
        Button hapus, edit;

        public BahanAjarMatpelJoinViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMatpel     = itemView.findViewById(R.id.tv_matpel);
            tvTarif      = itemView.findViewById(R.id.tv_tarif);
            hapus        = itemView.findViewById(R.id.btn_hapus_matpel);
            edit         = itemView.findViewById(R.id.btn_edit_matpel);
        }

    }

    private void deleteDataMatpel() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Peringatan");
        dialog
                .setMessage("Yakin ingin menghapus data ini?")
                .setCancelable(false)
                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
                        apiService.deleteMatpel(id).enqueue(new Callback<ResponseLokasi>() {
                            @Override
                            public void onResponse(Call<ResponseLokasi> call, Response<ResponseLokasi> response) {
                                if (response.isSuccessful()){
                                    int code        = response.body().getCode();
                                    String message  = response.body().getMessage();
                                    if (code == 200){
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//                                        loadingDataGuruId();
                                    }else{
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseLokasi> call, Throwable t) {
                                t.printStackTrace();
                                Toast.makeText( context,"Jaringan Error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = dialog.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

}

