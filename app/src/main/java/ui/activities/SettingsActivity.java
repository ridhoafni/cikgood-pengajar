package ui.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.utils.SessionManager;

import ui.fragments.EditProfilFragment;
import ui.fragments.HomeFragment;
import ui.fragments.ProfilFragment;

public class SettingsActivity extends AppCompatActivity {

    private TextView keluar;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        keluar = findViewById(R.id.tv_keluar);
        sessionManager = new SessionManager(this);

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                sessionManager.logoutGuru();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pengaturan");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
//                onBackPressed();
////                ProfilFragment fragment = new ProfilFragment();
//                ProfilFragment fragment = ProfilFragment.newInstance();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                if (fragmentManager != null) {
//                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.fragment_frame, fragment);
//                    ft.commit();
                Intent intent = new Intent(SettingsActivity.this, NavigationView.class);
                startActivity(intent);

//                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
