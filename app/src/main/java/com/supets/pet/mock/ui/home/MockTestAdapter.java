package com.supets.pet.mock.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.supets.pet.mock.base.BaseRecycleAdapter;
import com.supets.pet.mock.base.BaseRecycleViewHolder;
import com.supets.pet.mock.bean.LocalMockData;
import com.supets.pet.mock.dao.LocalMockDataDB;
import com.supets.pet.mock.ui.config.MockConfigJsonActivity;
import com.supets.pet.mockui.R;

public class MockTestAdapter extends BaseRecycleAdapter<LocalMockData> {

    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecycleViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mock_list_test_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final BaseRecycleViewHolder holder, int position) {

        ((TextView) holder.itemView.findViewById(R.id.name)).setText(data.get(position).getUrl());

        final CheckBox checkBox = holder.itemView.findViewById(R.id.select);
        checkBox.setChecked(data.get(position).getSelected());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.get(holder.getAdapterPosition()).setSelected(checkBox.isChecked());
                LocalMockDataDB.updateMockData(data.get(holder.getAdapterPosition()));
            }
        });

        holder.itemView.findViewById(R.id.configjson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MockConfigJsonActivity.class);
                intent.putExtra("url", data.get(holder.getAdapterPosition()).getUrl());
                view.getContext().startActivity(intent);
            }
        });

        holder.itemView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isDelete) {
                    isDelete = true;
                    //有动画快速删除，任意出现数组越界异常

                    int position = holder.getAdapterPosition();
                    if (position < data.size() && data.size() > 0) {
                        LocalMockDataDB.deleteMockData(data.get(position));
                        data.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(0, getItemCount() - position);
                    } else {
                        Toast.makeText(holder.itemView.getContext(), "删除异常了", Toast.LENGTH_SHORT).show();
                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            isDelete = false;
                        }
                    }).start();
                } else {
                    Toast.makeText(holder.itemView.getContext(), "兄弟，手速慢点", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean isDelete = false;


}