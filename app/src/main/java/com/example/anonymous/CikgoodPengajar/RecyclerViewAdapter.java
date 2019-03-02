package com.example.anonymous.CikgoodPengajar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Result> results;

    public RecyclerViewAdapter(Context context, List<Result> results){

        this.context = context;
        this.results = results;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = results.get(position);
        holder.tvLat.setText(result.getLatitude());
        holder.tvLng.setText(result.getLongitude());
        holder.tvName.setText(result.getName_location());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvLat) TextView tvLat;
        @BindView(R.id.tvLng) TextView tvLng;
        @BindView(R.id.tvName) TextView tvName;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
