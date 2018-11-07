package com.supets.pet.mock.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supets.pet.mock.base.BaseRecycleAdapter;
import com.supets.pet.mock.base.BaseRecycleViewHolder;
import com.supets.pet.mock.bean.MockData;
import com.supets.pet.mock.dao.MockDataDB;
import com.supets.pet.mock.ui.detail.MockInfoActivity;
import com.supets.pet.mock.utils.FormatLogProcess;
import com.supets.pet.mockui.R;

import java.text.SimpleDateFormat;

public class MockDataAdapter extends BaseRecycleAdapter<MockData> {


    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mock_list_datalist_item, parent, false);
        return new BaseRecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BaseRecycleViewHolder holder, int position) {

        ((TextView) holder.itemView.findViewById(R.id.name)).setText(data.get(position).getUrl());


        if (FormatLogProcess.isJson(data.get(position).getData())) {
            (holder.itemView.findViewById(R.id.status)).setBackgroundColor(holder.itemView.getResources().getColor(R.color.appcolor));
        } else {
            (holder.itemView.findViewById(R.id.status)).setBackgroundColor(Color.parseColor("#ff0000"));
        }

        String time = dateToStrLong(data.get(position).getTime());
        ((TextView) holder.itemView.findViewById(R.id.time)).setText(time);

        ((TextView) holder.itemView.findViewById(R.id.param)).setText(data.get(position).getData());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MockInfoActivity.class);
                intent.putExtra("id", data.get(holder.getAdapterPosition()).getId().toString());
                view.getContext().startActivity(intent);
            }
        });

        holder.itemView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //无动画快速删除，不容易出现数组越界
                // if (position < data.size()&&data.size()>0) {
                int pos = holder.getAdapterPosition();
                MockDataDB.deleteMockData(data.get(pos));
                data.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(0, getItemCount() - pos);
                //  }
            }
        });

    }

    public static String dateToStrLong(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd HH:mm");
        String dateString = formatter.format(dateDate);
        return dateString;
    }
}