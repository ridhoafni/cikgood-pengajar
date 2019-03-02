package com.example.anonymous.CikgoodPengajar.adapters;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import com.example.anonymous.CikgoodPengajar.config.ServerConfig;
import com.example.anonymous.CikgoodPengajar.models.PengalamanKerja;
import com.example.anonymous.CikgoodPengajar.models.RiwayatPendidikan;
import com.example.anonymous.CikgoodPengajar.response.ResponseCreatePengalamanKerja;
import com.example.anonymous.CikgoodPengajar.response.ResponseDeleteRiwayatPendidikan;
import com.example.anonymous.CikgoodPengajar.rests.ApiClient;
import com.example.anonymous.CikgoodPengajar.rests.ApiInterface;
import com.example.anonymous.CikgoodPengajar.utils.CustomOnItemClickListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.fragments.PengalamanFragment;

public class RiwayatPendidikanAdapter extends RecyclerView.Adapter<RiwayatPendidikanAdapter.RiwayatPendidikanViewHolder> {
    String guru_id, gelar, jenis_instansi, nama_instansi, jurusan, tahun_masuk, tahun_selesai, photo_ijazah;
    private EditText etFormRiwayatPendidikanId, etFormGuruId, etFormGelar, etFormNamaInstansi, etFormJurusan, etFormTahunMasuk, getFormTahunSelesai, getFormPhotoIjazah;
    private Spinner spinnerJenisInstansi;
    private List<RiwayatPendidikan> getDataRiwayatPendidikanByGuruId;
    String jabatan, perusahaan, tgl_masuk, tgl_selesai;
    PengalamanFragment pengalamanFragment;
    DatePickerDialog datePickerDialog;
    ImageView ImageView;
    private ApiInterface apiService;
    private ProgressDialog loading;
    AlertDialog.Builder dialog;
    AlertDialog alertDialog;
    LayoutInflater inflater;
    private Context context;
    View dialogView ;
    //    PengalamanFragment pengalamanFragment = new PengalamanFragment();
    int id, id_guru;

    public RiwayatPendidikanAdapter(Context context, List<RiwayatPendidikan> getDataRiwayatPendidikanByGuruId) {
        this.context = context;
        this.getDataRiwayatPendidikanByGuruId = getDataRiwayatPendidikanByGuruId;
    }

    @NonNull
    @Override
    public RiwayatPendidikanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_riwayat_pendidikan_adapter, parent, false);
        return new RiwayatPendidikanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatPendidikanViewHolder riwayatPendidikanViewHolder, int i) {
        RiwayatPendidikan riwayatPendidikan = getDataRiwayatPendidikanByGuruId.get(i);
        riwayatPendidikanViewHolder.tvJurusan.setText(riwayatPendidikan.getJurusan());
        riwayatPendidikanViewHolder.tvNamaInstansi.setText(riwayatPendidikan.getNamaInstitusi());

        riwayatPendidikanViewHolder.hapus.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                id = getDataRiwayatPendidikanByGuruId.get(position).getIdRiwayatPendidikan();
                deleteDataPengalamanKerja();
            }
        }));

        riwayatPendidikanViewHolder.edit.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {

                id                  = getDataRiwayatPendidikanByGuruId.get(position).getIdRiwayatPendidikan();
                guru_id             = String.valueOf(getDataRiwayatPendidikanByGuruId.get(position).getGuruId());
                gelar               = getDataRiwayatPendidikanByGuruId.get(position).getGelar();
                jenis_instansi      = getDataRiwayatPendidikanByGuruId.get(position).getJenisInstitusi();
                nama_instansi       = getDataRiwayatPendidikanByGuruId.get(position).getNamaInstitusi();
                jurusan             = getDataRiwayatPendidikanByGuruId.get(position).getJurusan();
                tahun_masuk         = getDataRiwayatPendidikanByGuruId.get(position).getTahunMasuk();
                tahun_selesai       = getDataRiwayatPendidikanByGuruId.get(position).getTahunSelesai();
                photo_ijazah        = getDataRiwayatPendidikanByGuruId.get(position).getPhotoIjazah();
//                System.out.println("Tgl masuk :"+tgl_masuk);

                editDataRiwayatPendidikan();
            }
        }));
    }

    private void editDataRiwayatPendidikan() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogView  = inflater.inflate(R.layout.form_riwayat_pendidikan, null);
        initDataRiwayatPendidikan();
        dialog
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
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
        AlertDialog alertDialog = dialog.create();

        // menampilkan alert dialog
        alertDialog.show();

    }

    private void initDataRiwayatPendidikan() {
//        etFormRiwayatPendidikanId      = dialogView.findViewById(R.id.etFormGuruId);
        etFormGuruId            = dialogView.findViewById(R.id.etFormGuruId);
        etFormGelar             = dialogView.findViewById(R.id.etFormGelar);
        spinnerJenisInstansi    = dialogView.findViewById(R.id.etFormJenisInstitusi);
        etFormNamaInstansi      = dialogView.findViewById(R.id.etFormNamaInstitusi);
        etFormJurusan           = dialogView.findViewById(R.id.etFormJurusan);
        etFormTahunMasuk        = dialogView.findViewById(R.id.etFormTahunMasuk);
        getFormTahunSelesai     = dialogView.findViewById(R.id.etFormTahunSelesai);

        List<String> jenis_institusi = new ArrayList<String>();
        jenis_institusi.add("Dalam Negeri");
        jenis_institusi.add("Luar Negeri");

        spinnerJenisInstansi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selectedName = parent.getItemAtPosition(position).toString();
                String selectedName = parent.getItemAtPosition(position).toString();
                spinnerJenisInstansi.setSelection(position);
//                Toast.makeText(context, "Kamu memilih " + selectedName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adapterJI = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, jenis_institusi);
        adapterJI.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenisInstansi.setPrompt("Pilih Jenis Institusi");
        spinnerJenisInstansi.setAdapter( adapterJI);

        etFormGelar.setText(gelar);
//        spinnerJenisInstansi.setText(jenis_institusi);

        int index = jenis_institusi.indexOf(0);
        if (index != -1){
            spinnerJenisInstansi.setSelection(index);
        }

        etFormNamaInstansi.setText(nama_instansi);
        etFormJurusan.setText(jurusan);
        etFormTahunMasuk.setText(tahun_masuk);
        getFormTahunSelesai.setText(tahun_selesai);
        ImageView = dialogView.findViewById(R.id.imageView);
        Picasso.get().load(ServerConfig.GURU_IJAZAH_PATH+photo_ijazah).into(ImageView);
    }

    private void saveDataRiwayatPendidikan() {
        dialog      = new AlertDialog.Builder(context);
        loading = new ProgressDialog(context);
        loading.setCancelable(false);
        dialog.setMessage("Loading...");
        loading.show();

        Integer id_riwayat_pendidikan    = id;
        Integer guru_id_pendidikan       = Integer.valueOf(guru_id);
        String gelar_pendidikan          = etFormGelar.getText().toString();
        String jenis_instansi_pendidikan = spinnerJenisInstansi.getSelectedItem().toString();
        String nama_instansi_pendidikan  = etFormNamaInstansi.getText().toString();
        String tahun_masuk_pendidikan    = etFormTahunMasuk.getText().toString();
        String tahun_selesai_pendidikan  = getFormTahunSelesai.getText().toString();

        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
//        apiService.updatePengalamanKerja(id_riwayat_pendidikan, guru_id_pendidikan, gelar_pendidikan, jenis_instansi_pendidikan, nama_instansi_pendidikan, tahun_masuk_pendidikan, tahun_selesai_pendidikan).enqueue(new Callback<ResponseCreatePengalamanKerja>() {
//            @Override
//            public void onResponse(Call<ResponseCreatePengalamanKerja> call, Response<ResponseCreatePengalamanKerja> response) {
//                System.out.println("Responnya :"+response.toString());
//                if (response.isSuccessful()){
//                    loading.dismiss();
//                    if (response.body().getCode().equals(200)){
//                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
////                        alertDialog.cancel();
//                        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
////                        pengalamanFragment = new PengalamanFragment();
////                        pengalamanFragment.loadDataPengalamanKerjaGuruId();
////                        loadDataPengalamanKerjaGuruId();
//
//                    }else{
//                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseCreatePengalamanKerja> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return getDataRiwayatPendidikanByGuruId.size();
    }

    public class RiwayatPendidikanViewHolder extends RecyclerView.ViewHolder {
        TextView tvJurusan, tvNamaInstansi;
        LinearLayout linearLayout;
        Button hapus, edit;

        public RiwayatPendidikanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJurusan        = itemView.findViewById(R.id.tv_jurusan);
            tvNamaInstansi   = itemView.findViewById(R.id.tv_nama_instansi);
            hapus            = itemView.findViewById(R.id.btn_hapus_pendidikan);
            edit             = itemView.findViewById(R.id.btn_edit_pendidikan);
        }

    }

    private void deleteDataPengalamanKerja() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Peringatan");
        dialog
                .setMessage("Yakin ingin menghapus data ini?")
                .setCancelable(false)
                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
                        apiService.deleteRiwayatPendidikan(id).enqueue(new Callback<ResponseDeleteRiwayatPendidikan>() {
                            @Override
                            public void onResponse(Call<ResponseDeleteRiwayatPendidikan> call, Response<ResponseDeleteRiwayatPendidikan> response) {
                                if (response.isSuccessful()){
                                    int code        = response.body().getCode();
                                    String message  = response.body().getMessage();
                                    if (code == 200){
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseDeleteRiwayatPendidikan> call, Throwable t) {
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


