package com.supets.pet.service;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter {


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        return new RecyclerView.ViewHolder(textView) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        String dd = datas.get(position);
        textView.setText(dd);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public ArrayList<String> datas = new ArrayList<>();

    public void putData(String data) {
        datas.add(0, data);
    }
}
