package ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.ResetPasswordActivity;
import com.example.anonymous.CikgoodPengajar.config.ServerConfig;
import com.example.anonymous.CikgoodPengajar.response.ResponseCreateGuru;
import com.example.anonymous.CikgoodPengajar.response.ResponseCreateMurid;
import com.example.anonymous.CikgoodPengajar.rests.ApiClient;
import com.example.anonymous.CikgoodPengajar.rests.ApiInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

//    public static final String URL = ServerConfig.API_ENDPOINT;
    EditText etNama, etNoHP, etEmail, etPassword, etAlamat, etJk, etNisn, etKelas, etNamaSekolah, etPhoto;
    Spinner spinnerJK;
    Button btnSubmit;
    ApiInterface apiService;
    private Button btnSignIn, btnSignUp, btnResetPassword, btnChoose;
    private TextView tvResponse;
    private ProgressBar progressBar;
    Context context;
    private static final String TAG = SignupActivity.class.getSimpleName();
    public static final String TAG2 = "MY MESSAGE";
    private ImageView imageView;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);
        etNama = findViewById(R.id.et_nama);
        etNoHP = findViewById(R.id.et_no_hp);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnSubmit = findViewById(R.id.btn_submit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        tvResponse = findViewById(R.id.tv_response);

        apiService = ApiClient.getClient(ServerConfig.API_ENDPOINT).create(ApiInterface.class);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                kirimDataMurid();
            }

        });
    }

    private void kirimDataMurid() {

        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String nama         = etNama.getText().toString();
        String email        = etEmail.getText().toString();
        String no_hp        = etNoHP.getText().toString();
        String password     = etPassword.getText().toString();

        apiService.registerDataGuru(nama, email, no_hp, password).enqueue(new Callback<ResponseCreateGuru>() {
            @Override
            public void onResponse(Call<ResponseCreateGuru> call, Response<ResponseCreateGuru> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    if (response.body().getCode().equals(200)){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent i = new Intent( SignupActivity.this, LoginActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCreateGuru> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error!" +t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}