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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.config.ServerConfig;
import com.example.anonymous.CikgoodPengajar.models.PengalamanKerja;
import com.example.anonymous.CikgoodPengajar.response.ResponseCreatePengalamanKerja;
import com.example.anonymous.CikgoodPengajar.rests.ApiClient;
import com.example.anonymous.CikgoodPengajar.rests.ApiInterface;
import com.example.anonymous.CikgoodPengajar.utils.CustomOnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.fragments.PengalamanFragment;

public class PengalamanKerjaAdapter extends RecyclerView.Adapter<PengalamanKerjaAdapter.PengalamanKerjaViewHolder> {
    private EditText etFormKerjaJabatan, etFormKerjaPerusahaan, etFormKerjaTglMasuk, etFormKerjaTglKeluar, etFormKerjaGuruId;
    String jabatan, perusahaan, tgl_masuk, tgl_selesai;
    private List<PengalamanKerja> getDataPengalamanKerjaByGuruId;
    PengalamanFragment pengalamanFragment;
    DatePickerDialog datePickerDialog;
    private ApiInterface apiService;
    private ProgressDialog loading;
    AlertDialog.Builder dialog;
    AlertDialog alertDialog;
    LayoutInflater inflater;
    private Context context;
    View dialogView ;
    //    PengalamanFragment pengalamanFragment = new PengalamanFragment();
    int id, id_kerja;

    public PengalamanKerjaAdapter(Context context, List<PengalamanKerja> getDataPengalamanKerjaByGuruId) {
        this.context = context;
        this.getDataPengalamanKerjaByGuruId = getDataPengalamanKerjaByGuruId;
    }

    @NonNull
    @Override
    public PengalamanKerjaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_kerja, parent, false);
        return new PengalamanKerjaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PengalamanKerjaViewHolder pengalamanKerjaViewHolder, int i) {
        PengalamanKerja pengalamanKerja = getDataPengalamanKerjaByGuruId.get(i);
        pengalamanKerjaViewHolder.tvPengalamanKerjaJabatan.setText(pengalamanKerja.getJabatan());
        pengalamanKerjaViewHolder.hapus.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                id = getDataPengalamanKerjaByGuruId.get(position).getIdPengalamanKerja();
                deleteDataPengalamanKerja();
            }
        }));
        pengalamanKerjaViewHolder.edit.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {

                id_kerja            = getDataPengalamanKerjaByGuruId.get(position).getIdPengalamanKerja();
                id                  = getDataPengalamanKerjaByGuruId.get(position).getGuruId();
                jabatan             = getDataPengalamanKerjaByGuruId.get(position).getJabatan();
                perusahaan          = getDataPengalamanKerjaByGuruId.get(position).getPerusahaan();
                tgl_masuk           = getDataPengalamanKerjaByGuruId.get(position).getTglMasuk();
                tgl_selesai         = getDataPengalamanKerjaByGuruId.get(position).getTglSelesai();

                System.out.println("Tgl masuk :"+tgl_masuk);

                editDataPengalamanKerja();
            }
        }));
    }

    private void editDataPengalamanKerja() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogView  = inflater.inflate(R.layout.form_pengalaman_kerja, null);
        initDataPengalamanKerja();
        dialog
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
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
        AlertDialog alertDialog = dialog.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void initDataPengalamanKerja() {
        etFormKerjaGuruId      = dialogView.findViewById(R.id.etFormKerjaGuruId);
        etFormKerjaJabatan     = dialogView.findViewById(R.id.etFormKerjaJabatan);
        etFormKerjaPerusahaan  = dialogView.findViewById(R.id.etFormKerjaPerusahaan);
        etFormKerjaTglMasuk    = dialogView.findViewById(R.id.etFormKerjaTglMasuk);
        etFormKerjaTglKeluar   = dialogView.findViewById(R.id.etFormKerjaTglKeluar);

        etFormKerjaJabatan.setText(jabatan);
        etFormKerjaPerusahaan.setText(perusahaan);
        etFormKerjaTglMasuk.setText(tgl_masuk);
        etFormKerjaTglKeluar.setText(tgl_selesai);
//        etFormKerjaGuruId.setText(guru_id);

        etFormKerjaTglMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogKerjaTglMasuk();
            }

            private void showDialogKerjaTglMasuk() {
                Calendar calendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
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
                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
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

    private void saveDataPengalamaKerja() {
        dialog      = new AlertDialog.Builder(context);
        loading = new ProgressDialog(context);
        loading.setCancelable(false);
        dialog.setMessage("Loading...");
        loading.show();
        Integer id_pengalaman_kerja     = id_kerja;
        Integer guru_id                 = id;
        String jabatan                  = etFormKerjaJabatan.getText().toString();
        String perusahaan               = etFormKerjaPerusahaan.getText().toString();
        String tgl_masuk                = etFormKerjaTglMasuk.getText().toString();
        String tgl_selesai              = etFormKerjaTglKeluar.getText().toString();

        System.out.println("A :"+id_pengalaman_kerja);
        System.out.println("A :"+guru_id);
        System.out.println("A :"+jabatan);
        System.out.println("A :"+perusahaan);
        System.out.println("A :"+tgl_masuk);
        System.out.println("A :"+tgl_selesai);

        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
        apiService.updatePengalamanKerja(id_pengalaman_kerja, guru_id, jabatan, perusahaan, tgl_masuk, tgl_selesai).enqueue(new Callback<ResponseCreatePengalamanKerja>() {
            @Override
            public void onResponse(Call<ResponseCreatePengalamanKerja> call, Response<ResponseCreatePengalamanKerja> response) {
                System.out.println("Responnya :"+response.toString());
                if (response.isSuccessful()){
                    loading.dismiss();
                    if (response.body().getCode().equals(200)){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        alertDialog.cancel();
                        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
//                        pengalamanFragment = new PengalamanFragment();
//                        pengalamanFragment.loadDataPengalamanKerjaGuruId();
//                        loadDataPengalamanKerjaGuruId();

                    }else{
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCreatePengalamanKerja> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return getDataPengalamanKerjaByGuruId.size();
    }

    public class PengalamanKerjaViewHolder extends RecyclerView.ViewHolder {
        TextView tvPengalamanKerjaJabatan;
        LinearLayout linearLayout;
        Button hapus, edit;

        public PengalamanKerjaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPengalamanKerjaJabatan        = itemView.findViewById(R.id.tv_pengalaman_kerja_jabatan);
            hapus                           = itemView.findViewById(R.id.btn_hapus_kerja);
            edit                            = itemView.findViewById(R.id.btn_edit_kerja);
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
                        apiService.deletePengalamanKerja(id).enqueue(new Callback<ResponseCreatePengalamanKerja>() {
                            @Override
                            public void onResponse(Call<ResponseCreatePengalamanKerja> call, Response<ResponseCreatePengalamanKerja> response) {
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
                            public void onFailure(Call<ResponseCreatePengalamanKerja> call, Throwable t) {
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

