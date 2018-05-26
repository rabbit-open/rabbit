package com.supets.pet.service;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supets.pet.mock.bean.MockData;
import com.supets.pet.mock.utils.FormatLogProcess;
import com.supets.pet.mock.utils.Utils;
import com.supets.pet.mockui.R;

import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter {


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View textView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        TextView label = textView.findViewById(R.id.label);
        TextView name = textView.findViewById(R.id.name);
        label.setOnClickListener(view -> {
            name.setVisibility(name.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });
        return new RecyclerView.ViewHolder(textView) {
        };
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.name);
        TextView label = holder.itemView.findViewById(R.id.label);


        MockData data = datas.get(position);

        label.setText("请求接口:".concat(data.getUrl()));

        StringBuffer message = new StringBuffer()
                .append("请求参数：")
                .append("\r\n")
                .append(Utils.formatParam(data.getRequestParam()))
                .append("\r\n")
                .append("请求结果：")
                .append("\r\n")
                //.append(FormatLogProcess.format(FormatLogProcess.formatJsonText(json)))
                .append(FormatLogProcess.format(data.getData()));
        textView.setText(message.toString());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public ArrayList<MockData> datas = new ArrayList<>();

    public void putData(MockData data) {
        datas.add(data);
        notifyDataSetChanged();
    }
}
