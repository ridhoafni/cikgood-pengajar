package ui.fragments;


//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.utils.SessionManager;

import ui.activities.MainTabActivity;
import ui.activities.SettingsActivity;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {

    private Button btnEdit;
    private Context context;
    private TextView tvNama, tvEmail, tvJk, tvHP;
    ImageView imgProfile;
    SessionManager sessionManager;

    private static final String KEY_ID_GURU             = "id";
    private static final String KEY_NAMA                = "nama";
    private static final String KEY_EMAIL               = "email";
    private static final String KEY_NO_HP               = "no_hp";
    private static final String KEY_PASSWORD            = "password";
    private static final String KEY_TGL_LAHIR           = "password";
    private static final String KEY_JK                  = "jk";
    private static final String KEY_PROVINSI_KTP        = "provinsi_ktp";
    private static final String KEY_KOTA_KTP            = "kota_ktp";
    private static final String KEY_KECAMATAN_KTP       = "kecamatan_ktp";
    private static final String KEY_ALAMAT_KTP          = "alamat_ktp";
    private static final String KEY_PROVINSI_DOMISILI   = "provinsi_domisili";
    private static final String KEY_KOTA_DOMISILI       = "kota_domisili";
    private static final String KEY_KECAMATAN_DOMISILI  = "kecamatan_domisili";
    private static final String KEY_ALAMAT_DOMISILI     = "alamat_domisili";
    private static final String KEY_BIODATA             = "biodata";
    private static final String KEY_PHOTO_PROFILE       = "photo_profile";

    private int SESSION_ID_MURID;
    private String SESSION_NAMA;
    private String SESSION_EMAIL;
    private String SESSION_NO_HP;
    private String SESSION_PASSWORD;
    private String SESSION_TGL_LAHIR;
    private String SESSION_JK;
    private String SESSION_PROVINSI_KTP;
    private String SESSION_KOTA_KTP;
    private String SESSION_KECAMATAN_KTP;
    private String SESSION_ALAMAT_KTP;
    private String SESSION_PROVINSI_DOMISILI;
    private String SESSION_KOTA_DOMISILI;
    private String SESSION_KECAMATAN_DOMISILI;
    private String SESSION_ALAMAT_DOMISILI;
    private String SESSION_BIODATA;
    private String SESSION_PHOTO_PROFILE;

    public static ProfilFragment newInstance() {
        return new ProfilFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        btnEdit = (Button) view.findViewById(R.id.button_edit);
        tvNama = (TextView) view.findViewById(R.id.tv_profil_nama);
        tvEmail= (TextView) view.findViewById(R.id.tv_profil_email);
        tvHP   = (TextView) view.findViewById(R.id.tv_profil_hp);

        sessionManager = new SessionManager(getActivity());

        tvNama.setText(sessionManager.getGuruProfile().get("nama"));
        tvEmail.setText(sessionManager.getGuruProfile().get("email"));
        tvHP.setText(sessionManager.getGuruProfile().get("no_hp"));

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i = new Intent(getActivity(), MainTabActivity.class);
                Intent edit = new Intent(getApplicationContext(), MainTabActivity.class);
                edit.putExtra(ProfilFragment.KEY_ID_GURU,      SESSION_ID_MURID);
                edit.putExtra(ProfilFragment.KEY_NAMA,         SESSION_NAMA);
                edit.putExtra(ProfilFragment.KEY_NO_HP,        SESSION_NO_HP);
                edit.putExtra(ProfilFragment.KEY_EMAIL,        SESSION_EMAIL);

//                EditProfilFragment fragment = new EditProfilFragment();

//                Bundle bundle = new Bundle();
//                bundle.putString(ProfilFragment.KEY_NAMA, SESSION_NAMA);
//                bundle.putString(ProfilFragment.KEY_NO_HP, SESSION_NO_HP);
//                bundle.putString(ProfilFragment.KEY_EMAIL, SESSION_EMAIL);
                startActivity(edit);
//                fragment.setArguments(bundle);

//                FragmentManager fragmentManager = getFragmentManager();
//                if (fragmentManager != null){
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.frame_container, fragment, EditProfilFragment.class.getSimpleName());
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//                }
            }
        });
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_settings, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_setings:
                Intent i;
                i = new Intent(getActivity(), SettingsActivity.class);
                startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }

}
