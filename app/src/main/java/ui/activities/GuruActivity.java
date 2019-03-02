package ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.adapters.GuruAdapter;
import com.example.anonymous.CikgoodPengajar.config.ServerConfig;
import com.example.anonymous.CikgoodPengajar.models.GuruProfil;
import com.example.anonymous.CikgoodPengajar.response.ResponseGuruProfil;
import com.example.anonymous.CikgoodPengajar.rests.ApiClient;
import com.example.anonymous.CikgoodPengajar.rests.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuruActivity extends AppCompatActivity {
    private static final String KEY_ID_GURU = "key_id_guru";
    public static final String URL = ServerConfig.API_ENDPOINT;
    private List<GuruProfil> datas = new ArrayList<>();
    private GuruAdapter guruAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    Context context;
    ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Daftar GuruProfil Les Privat");

        guruAdapter = new GuruAdapter(this, datas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(guruAdapter);
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
        loadDataGuru();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), MainTabActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadDataGuru() {

        apiService.guruFindAll().enqueue(new Callback<ResponseGuruProfil>() {
            @Override
            public void onResponse(Call<ResponseGuruProfil> call, Response<ResponseGuruProfil> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    if (response.body().getMaster().size()>0){
                        datas = response.body().getMaster();
                        guruAdapter = new GuruAdapter(GuruActivity.this, datas);
                        recyclerView.setAdapter(guruAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseGuruProfil> call, Throwable t) {

            }
        });
    }
}
