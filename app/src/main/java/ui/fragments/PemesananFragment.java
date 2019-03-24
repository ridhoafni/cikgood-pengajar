package ui.fragments;


import android.content.Intent;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.anonymous.CikgoodPengajar.R;

import ui.activities.SettingsActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class PemesananFragment extends Fragment {


    public static PemesananFragment newInstance() {
        return new PemesananFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pemesanan, container, false);
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
