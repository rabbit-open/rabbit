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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GridAdapter extends RecyclerView.Adapter {


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View textView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        TextView name = textView.findViewById(R.id.name);
        textView.setOnClickListener(view -> {
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

        label.setText("请求接口:\r\n".concat(data.getUrl()));

        if (FormatLogProcess.isJson(data.getData())) {
            try {
                String string = new JSONObject(data.getData()).toString();
                String message =
                        "请求Header参数：\r\n".concat(
                                Utils.formatParam(data.getHeaderParam())).
                                concat("请求Post参数：\r\n").concat(
                                Utils.formatParam(data.getRequestParam())).concat(
                                "\r\n请求结果：\r\n").concat(
                                FormatLogProcess.format(string));
                textView.setText(message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            String message =
                    "请求Header参数：\r\n".concat(
                            Utils.formatParam(data.getHeaderParam())).
                            concat("请求Post参数：\r\n").concat(
                            Utils.formatParam(data.getRequestParam())).concat(
                            "\r\n请求结果：\r\n").concat(data.getData());
            textView.setText(message);
        }
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
