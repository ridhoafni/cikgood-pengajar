package ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.anonymous.CikgoodPengajar.R;

import ui.fragments.HomeFragment;
import ui.fragments.PemesananFragment;
import ui.fragments.ProfilFragment;

public class NavigationView extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
//    private Toolbar toolbar;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_view);

//      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar = getSupportActionBar();

        setupBottomNavigation();

        if(savedInstanceState == null){
            loadHomeFragment();
        }
    }

    private void setupBottomNavigation() {

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_homes:
                        loadHomeFragment();
                        toolbar.setTitle("Home");
                        return true;
                    case R.id.navigation_users:
                        loadProfileFragment();
                        toolbar.setTitle("Profil");
                        return true;
                    case R.id.navigation_orders:
                        loadOrdersFragment();
                        toolbar.setTitle("Pemesanan");
                        return true;
                }
                return false;
            }
        });
    }

    private void loadHomeFragment() {
        HomeFragment fragment = HomeFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadProfileFragment() {

        ProfilFragment fragment = ProfilFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

    private void loadOrdersFragment() {

        PemesananFragment fragment = PemesananFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }
}
