package com.example.anonymous.CikgoodPengajar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.anonymous.CikgoodPengajar.R;
import com.example.anonymous.CikgoodPengajar.models.SemuaProvinsi;

import java.util.ArrayList;
import java.util.List;

public class CAProvinsiAdapter extends ArrayAdapter<SemuaProvinsi> {
    private LayoutInflater layoutInflater;
    List<SemuaProvinsi> mSemuaprovinsis;


    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((SemuaProvinsi)resultValue).getNama();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<SemuaProvinsi> suggestions = new ArrayList<SemuaProvinsi>();
                for (SemuaProvinsi Semuaprovinsi : mSemuaprovinsis) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (Semuaprovinsi.getNama().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(Semuaprovinsi);
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<SemuaProvinsi>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(mSemuaprovinsis);
            }
            notifyDataSetChanged();
        }
    };

    public CAProvinsiAdapter(Context context, int textViewResourceId, List<SemuaProvinsi> Semuaprovinsis) {
        super(context, textViewResourceId, Semuaprovinsis);
        // copy all the Semuaprovinsis into a master list
        mSemuaprovinsis = new ArrayList<SemuaProvinsi>(Semuaprovinsis.size());
        mSemuaprovinsis.addAll(Semuaprovinsis);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.custom_autocomplete, null);
        }

        final SemuaProvinsi Semuaprovinsi = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.provinsi);
        name.setText(Semuaprovinsi.getNama());
        return view;
    }

    private static String getId(String id) {

        return id;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }
}
