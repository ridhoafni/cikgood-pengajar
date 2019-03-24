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
import com.example.anonymous.CikgoodPengajar.models.GuruJadwal;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.fragments.BahanAjarFragment;

public class GuruJadwalAdapter extends RecyclerView.Adapter<GuruJadwalAdapter.GuruJadwalViewHolder> {
    String guru_id_edit, provinsi_edit, kabupaten_edit, kecamatan_edit;
    AutoCompleteTextView autoCompleteProvinsi, autoCompleteKabupaten, autoCompleteKecamatan;
    RecyclerView recyclerViewLokasi;

    private List<GuruJadwal> getGuruJadwalByGuruId, lokasis;

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

    public GuruJadwalAdapter(Context context, List<GuruJadwal> getGuruJadwalByGuruId) {
        this.context = context;
        this.getGuruJadwalByGuruId = getGuruJadwalByGuruId;
    }

    @NonNull
    @Override
    public GuruJadwalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_rv_jadwal, parent, false);
        return new GuruJadwalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GuruJadwalViewHolder jadwalViewHolder, int i) {
        GuruJadwal guruJadwal = getGuruJadwalByGuruId.get(i);
        jadwalViewHolder.tvHari.setText(guruJadwal.getHari());
        jadwalViewHolder.tvJam.setText(guruJadwal.getJam());

//        String jam = guruJadwal.getJam();
//
//        String[] jamString = jam.split(", ");
//
//        for (int j = 0; i < jamString.length; j++){
////            GetTvPondok.setText(calculatePondok(dataPondok));
//            jadwalViewHolder.tvJam.setText(calculate(jamString));
//
//        }

        jadwalViewHolder.hapus.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                id = getGuruJadwalByGuruId.get(position).getIdJadwal();
                deleteDataJadwal();
//                loadingDataGuruId();
            }
        }));

        jadwalViewHolder.edit.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {

                id              = getGuruJadwalByGuruId.get(position).getIdJadwal();
                guru_id_edit    = String.valueOf(getGuruJadwalByGuruId.get(position).getGuruId());
//                provinsi_edit   = getDataLokasiByGuruId.get(position).getProvinsi();
//                kabupaten_edit  = getDataLokasiByGuruId.get(position).getKota();
//                kecamatan_edit  = getDataLokasiByGuruId.get(position).getKecamatan();

//                editDataLokasi();

            }
        }));
    }

//    public String calculate(String[] a) {
//
//        StringBuilder output = new StringBuilder();
//        int no = 1;
//        for (String s : a) {
//            output.append("\n ").append(no++).append(". ").append(s);
////            no++;
//        }
//        return output.toString();
//    }

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
//                        loadProvinsi();
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
//        loadProvinsi();
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
//                        loadProvinsi();
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
        return getGuruJadwalByGuruId.size();
    }

    public class GuruJadwalViewHolder extends RecyclerView.ViewHolder {
        TextView tvHari, tvJam;
        LinearLayout linearLayout;
        Button hapus, edit;

        public GuruJadwalViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHari     = itemView.findViewById(R.id.tv_hari);
            tvJam     = itemView.findViewById(R.id.tv_jam);
            hapus      = itemView.findViewById(R.id.btn_hapus_jadwal);
            edit       = itemView.findViewById(R.id.btn_edit_jadwal);
        }

    }

    private void deleteDataJadwal() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Peringatan");
        dialog
                .setMessage("Yakin ingin menghapus data ini?")
                .setCancelable(false)
                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
                        apiService.deleteJadwal(id).enqueue(new Callback<ResponseLokasi>() {
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

//    public void loadProvinsi(){
//
//        provInterface = CombineApi.getApiProv();
//        Call<ModelProvinsi> getProvinsi = provInterface.getProvinsi();
//        getProvinsi.enqueue(new Callback<ModelProvinsi>() {
//            @Override
//            public void onResponse(Call<ModelProvinsi> call, Response<ModelProvinsi> response) {
//                provinsiArrayList = response.body().getSemuaprovinsi();
//                CAProvinsiAdapter adapter = new CAProvinsiAdapter(context, android.R.layout.select_dialog_item, provinsiArrayList);
//                autoCompleteProvinsi.setAdapter(adapter);
//                autoCompleteProvinsi.setThreshold(0);
//                autoCompleteProvinsi.dismissDropDown();
//                autoCompleteProvinsi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        for (SemuaProvinsi semuaprovinsi : provinsiArrayList){
//                            if (semuaprovinsi.getNama().equals(autoCompleteProvinsi.getText().toString())){
//                                loadKabupaten(semuaprovinsi.getId());
//                            }
//                        }
//
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<ModelProvinsi> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void loadKabupaten(String id) {
//        provInterface = CombineApi.getApiProv();
//        Call<ModelKabupaten> getKabupaten = provInterface.getKabupaten(id);
//        getKabupaten.enqueue(new Callback<ModelKabupaten>() {
//            @Override
//            public void onResponse(Call<ModelKabupaten> call, Response<ModelKabupaten> response) {
//                kabupatenArrayList = response.body().getKabupatens();
//                CACKabupatenAdapter adapter = new CACKabupatenAdapter(context ,android.R.layout.select_dialog_item, kabupatenArrayList);
//                autoCompleteKabupaten.setAdapter(adapter);
//                autoCompleteKabupaten.setThreshold(0);
//                autoCompleteKabupaten.dismissDropDown();
//                HashMap<String,String> a = new HashMap<String,String>();
//                autoCompleteKabupaten.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        for (Kabupaten kabupaten : kabupatenArrayList){
//                            if (kabupaten.getNama().equals(autoCompleteKabupaten.getText().toString())){
//                                loadKecamatan(kabupaten.getId());
//                                break;
//                            }
//                        }
//
//                    }
//                });
//
//
//            }
//            @Override
//            public void onFailure(Call<ModelKabupaten> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void loadKecamatan(String id) {
//        provInterface = CombineApi.getApiProv();
//        Call<ModelKecamatan> getKecamatan = provInterface.getKecamatan(id);
//        getKecamatan.enqueue(new Callback<ModelKecamatan>() {
//            @Override
//            public void onResponse(Call<ModelKecamatan> call, Response<ModelKecamatan> response) {
//                kecamatanArrayList = response.body().getKecamatans();
//                CACKecamatanAdapter adapter = new CACKecamatanAdapter(context, android.R.layout.select_dialog_item, kecamatanArrayList);
//                autoCompleteKecamatan.setAdapter(adapter);
//                HashMap<String,String> a = new HashMap<String,String>();
//                autoCompleteKecamatan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        for (Kecamatan kecamatan : kecamatanArrayList){
//                            if (kecamatan.getNama().equals(autoCompleteKecamatan.getText().toString())){
////                                loadDesa(kecamatan.getId());
//                                break;
//                            }
//                        }
//
//                    }
//                });
//
//
//            }
//            @Override
//            public void onFailure(Call<ModelKecamatan> call, Throwable t) {
//
//            }
//        });
//    }



}

