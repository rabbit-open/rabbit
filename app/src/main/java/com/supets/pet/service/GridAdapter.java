package com.supets.pet.service;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supets.pet.mockui.R;

import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter {


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View textView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new RecyclerView.ViewHolder(textView) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.name);
        TextView label = holder.itemView.findViewById(R.id.label);
        String dd = datas.get(position);
        label.setText("请求" + position + "--->>");
        textView.setText(dd);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public ArrayList<String> datas = new ArrayList<>();

    public void putData(String data) {
        datas.add(data);
        notifyDataSetChanged();
    }
}
