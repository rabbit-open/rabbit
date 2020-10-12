package com.supets.pet.mocklib.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supets.pet.mocklib.R;
import com.supets.pet.mocklib.utils.FormatLogProcess;
import com.supets.pet.mocklib.utils.Utils;

import org.json.JSONObject;

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
        TextView name = holder.itemView.findViewById(R.id.name);
        TextView label = holder.itemView.findViewById(R.id.label);
        holder.itemView.setOnClickListener(view -> {
            name.setVisibility(name.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            if (name.getVisibility() == View.GONE) {
                notifyItemChanged(position);
            }
        });
        MockData data = datas.get(position);
        try {
            label.setText("请求接口:\r\n".concat(data.getUrl()));

            if (FormatLogProcess.isJson(data.getData())) {

                String string = new JSONObject(data.getData()).toString();
                String message =
                        "请求Header参数：\n".concat(Utils.formatParam(data.getHeaderParam()))
                                .concat("\n请求Post参数：\n").concat(Utils.formatParam(data.getRequestParam()))
                                .concat("\n响应Header参数：\n").concat(Utils.formatParam(data.getResponseParam()))
                                .concat("\n请求响应结果：\n").concat(FormatLogProcess.format(string));
                name.setText(message);

            } else {
                String message =
                        "请求Header参数：\n".concat(Utils.formatParam(data.getHeaderParam())).
                                concat("\n请求Post参数：\n").concat(Utils.formatParam(data.getRequestParam()))
                                .concat("\n响应Header参数：\n").concat(Utils.formatParam(data.getResponseParam()))
                                .concat("\n请求响应结果：\n").concat(data.getData());
                name.setText(message);
            }
        } catch (Exception e) {

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

    public void addHomeData(MockData data) {
        datas.clear();
        datas.add(data);
        notifyDataSetChanged();
    }

}
