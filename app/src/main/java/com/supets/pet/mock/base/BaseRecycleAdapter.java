package com.supets.pet.mock.base;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.supets.pet.mock.dao.LocalMockDataDB;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecycleViewHolder> {

    public List<T> data = new ArrayList<>();

    public void setData(List<T> data) {
        this.data = data;
    }

    public void addHomeData(List<T> data) {
        this.data.clear();
        if (data != null) {
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addMoreData(List<T> data) {
        if (data != null) {
            int itemCount = data.size();
            int postionStart = this.getItemCount();
            this.data.addAll(data);
            this.notifyItemRangeInserted(postionStart, itemCount);
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void insert(int position, T temp) {
        this.data.add(position, temp);
        this.notifyItemInserted(position);
        this.notifyItemRangeChanged(position, getItemCount()-position);
    }

    public void insert(int position, List<T> temp) {
        this.data.addAll(position, temp);
        this.notifyItemInserted(position);
        this.notifyItemRangeChanged(position, getItemCount() - position);
    }

    public void remove(int position) {
        this.data.remove(position);
        this.notifyItemRemoved(position);
        this.notifyItemRangeChanged(0, getItemCount() - position);
    }

    private boolean isDelete = false;

    public void removeAnim(int position) {
        if (!isDelete) {
            isDelete = true;
            //有动画快速删除，任意出现数组越界异常
            if (position < data.size() && data.size() > 0) {
                data.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(0, getItemCount() - position);
            } else {
                Log.v(this.getClass().getSimpleName(),
                        "删除异常了");
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
            Log.v(this.getClass().getSimpleName(),
                    "兄弟，手速慢点");
        }
    }


}
