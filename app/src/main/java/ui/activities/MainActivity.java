package ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ui.fragments.BantuanFragment;
import ui.fragments.BerandaFragment;
import ui.fragments.PemesananFragment;
import com.example.anonymous.CikgoodPengajar.PlaceAutocompleteActivity;
import ui.fragments.ProfileFragment;
import com.example.anonymous.CikgoodPengajar.R;

public class MainActivity extends AppCompatActivity{

    private ActionBar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // load  the store fragment by default
//        toolbar.setTitle("Shop");
        loadFragment(new BerandaFragment());

        //Here, this Activity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //Permision is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Membutuhkan Izin Lokasi", Toast.LENGTH_SHORT).show();
            } else {
                //No explanation needed; request the permission
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        } else {
            // permission has already been granted
            Toast.makeText(this, "Izin Lokasi diberikan", Toast.LENGTH_SHORT).show();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()){
                case R.id.navigation_beranda:
                    toolbar.setTitle("CIKGOOD");
                    fragment = new BerandaFragment();
                    loadFragment(fragment);
//                    Intent IntentOjek = new Intent(MainActivity.this, BerandaActivity.class);
//                    startActivity(IntentOjek);
//                    toolbar.setTitle("Akun");
                    return true;
                case R.id.navigation_bantuan:
                    toolbar.setTitle("Bantuan");
                    fragment = new BantuanFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    toolbar.setTitle("Akun");
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment){
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void openAutoPlace(View view) {
        startActivity(new Intent(this, PlaceAutocompleteActivity.class));
    }

    public void openDirectionMap(View view) {
        startActivity(new Intent(this, DirectionActivity.class));
    }

//    public void openOjek(View view) {
//        startActivity(new Intent(this, OjekActivity.class));
//    }
}
