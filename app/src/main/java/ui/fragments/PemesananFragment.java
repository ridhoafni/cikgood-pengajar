package ui.fragments;


import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anonymous.CikgoodPengajar.R;


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

}
