package ui.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ui.fragments.BahanAjarFragment;
import ui.fragments.JadwalFragment;
import ui.fragments.FragmentUploadImage;
import ui.fragments.IdentitasFragment;
import ui.fragments.PengalamanFragment;
import ui.fragments.UploadFragment;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class MainTabActivity extends AppCompatActivity {
    SessionManager sessionManager;
    private static final String TAG = "MainTabActivity";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
//    private int[] tabIcons = {
//            R.drawable.ic_home_black_24dp,
//            R.drawable.ic_search_black_24dp,
//            R.drawable.ic_store_mall_directory_black_24dp,
//            R.drawable.ic_mail_outline_black_24dp
//    };

    FragmentUploadImage editProfilFragment;
    Fragment identitasFragment;
    Fragment bahanAjarFragment;
    PengalamanFragment pengalamanFragment;
    JadwalFragment jadwalFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Data Guru");

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
//        setupTabIcons();
        sessionManager = new SessionManager(this);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                Intent i = new Intent(getApplicationContext(), ActivityFragment.class);
                startActivity(i);
//                ProfilFragment profilFragment = new ProfilFragment();
////                android.app.FragmentManager fragmentManager = getFragmentManager();
////                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////                fragmentTransaction.replace(R.id.frame_container, profilFragment, ProfilFragment.class.getSimpleName());
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_frame, profilFragment, profilFragment.getClass().getSimpleName())
//                        .addToBackStack(null)
//                        .commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    // end OnCreate

//    private void setupTabIcons() {
//        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
//        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
//        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
//        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
//    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        editProfilFragment  = new FragmentUploadImage();
        identitasFragment   = new IdentitasFragment();
        bahanAjarFragment   = new BahanAjarFragment();
        pengalamanFragment  = new PengalamanFragment();
        jadwalFragment      = new JadwalFragment();

        adapter.addFragment(editProfilFragment, "Profil");
        adapter.addFragment(identitasFragment, "Identitas");
        adapter.addFragment(bahanAjarFragment, "Bahan Ajar");
        adapter.addFragment(jadwalFragment, "Jadwal");
        adapter.addFragment(pengalamanFragment, "Pengalaman");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            return null;
            return mFragmentTitleList.get(position);
        }
    }


}