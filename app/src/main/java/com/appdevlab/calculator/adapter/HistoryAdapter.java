package com.appdevlab.calculator.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.appdevlab.calculator.R;

import java.util.ArrayList;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    private ArrayList<String> primary,secondary;

    public HistoryAdapter(ArrayList<String> primary, ArrayList<String> secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.history_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.primary.setText(primary.get(position));
        holder.secondary.setText(secondary.get(position));

    }

    @Override
    public int getItemCount() {
        return primary.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView primary,secondary;

        ViewHolder(View itemView) {
            super(itemView);
            this.secondary = (TextView) itemView.findViewById(R.id.secondary);
            this.primary = (TextView) itemView.findViewById(R.id.primary);
        }
    }
}