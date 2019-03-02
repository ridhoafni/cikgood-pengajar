package com.example.anonymous.CikgoodPengajar.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.config.ServerConfig;
import com.example.anonymous.CikgoodPengajar.models.PengalamanMengajar;
import com.example.anonymous.CikgoodPengajar.response.ResponseDeletePengalamanMengajar;
import com.example.anonymous.CikgoodPengajar.rests.ApiClient;
import com.example.anonymous.CikgoodPengajar.rests.ApiInterface;
import com.example.anonymous.CikgoodPengajar.utils.CustomOnItemClickListener;

import org.w3c.dom.Text;

import java.lang.invoke.CallSite;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.activities.GuruDetailActivity;
import ui.fragments.PengalamanFragment;

public class PengalamanMengajarAdapter extends RecyclerView.Adapter<PengalamanMengajarAdapter.PengalamanMengajarViewHolder> {
    private List<PengalamanMengajar> getDataPengalamanMengajarByGuruId;
    private ApiInterface apiService;
    private Context context;
    PengalamanFragment pengalamanFragment = new PengalamanFragment();
    int id;

    public PengalamanMengajarAdapter(Context context, List<PengalamanMengajar> getDataPengalamanMengajarByGuruId) {
        this.context = context;
        this.getDataPengalamanMengajarByGuruId = getDataPengalamanMengajarByGuruId;
    }

    @NonNull
    @Override
    public PengalamanMengajarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_mengajar, parent, false);
        return new PengalamanMengajarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PengalamanMengajarViewHolder pengalamanMengajarViewHolder, int i) {
        PengalamanMengajar pengalamanMengajar = getDataPengalamanMengajarByGuruId.get(i);
        pengalamanMengajarViewHolder.tvPengalamanMengajar.setText(pengalamanMengajar.getPengalaman());
        pengalamanMengajarViewHolder.hapus.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                id = getDataPengalamanMengajarByGuruId.get(position).getIdPengalamanMengajar();
                deleteDataPengalamanMengajar();
            }
        }));
    }

    @Override
    public int getItemCount() {
        return getDataPengalamanMengajarByGuruId.size();
    }

    public class PengalamanMengajarViewHolder extends RecyclerView.ViewHolder {
        TextView tvPengalamanMengajar;
        LinearLayout linearLayout;
        Button hapus;

        public PengalamanMengajarViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout            = itemView.findViewById(R.id.LinearLayout);
            tvPengalamanMengajar    = itemView.findViewById(R.id.tv_pengalaman_mengajar);
            hapus                   = itemView.findViewById(R.id.btn_hapus_mengajar);
        }

    }

    private void deleteDataPengalamanMengajar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Peringatan");
        dialog
                .setMessage("Yakin ingin menghapus data ini?")
                .setCancelable(false)
                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
                        apiService.deletePengalamanMengajar(id).enqueue(new Callback<ResponseDeletePengalamanMengajar>() {
                            @Override
                            public void onResponse(Call<ResponseDeletePengalamanMengajar> call, Response<ResponseDeletePengalamanMengajar> response) {
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
                            public void onFailure(Call<ResponseDeletePengalamanMengajar> call, Throwable t) {
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
