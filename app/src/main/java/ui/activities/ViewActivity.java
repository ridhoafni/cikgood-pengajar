package ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.RecyclerViewAdapter;
import com.example.anonymous.CikgoodPengajar.Result;
import com.example.anonymous.CikgoodPengajar.Value;
import com.example.anonymous.CikgoodPengajar.network.ApiServices;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewActivity extends AppCompatActivity {
    public static final String URL = "http://192.168.100.15/android_maps/";
    private List<Result> results = new ArrayList<>();
    private RecyclerViewAdapter viewAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Daftar Lat/Lng");


        viewAdapter = new RecyclerViewAdapter(this, results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);

        loadingDataMap();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadingDataMap() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices api = retrofit.create(ApiServices.class);
        Call<Value> call = api.view();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                progressBar.setVisibility(View.GONE);
                if (value.equals(1)) {
                    results = response.body().getResult();
                    viewAdapter = new RecyclerViewAdapter(ViewActivity.this, results);
                    recyclerView.setAdapter(viewAdapter);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {

            }
        });
    }
}