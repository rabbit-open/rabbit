package com.supets.pet.mock.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supets.pet.mock.base.BaseRecycleAdapter;
import com.supets.pet.mock.base.BaseRecycleViewHolder;
import com.supets.pet.mock.ui.MockToolActivity;
import com.supets.pet.mockui.R;

public class MockToolsAdapter extends BaseRecycleAdapter<String> {


    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecycleViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mock_list_home_tool_item, parent, false));
    }

    @Override
    public void onBindViewHolder(BaseRecycleViewHolder holder, final int position) {
        ((TextView) holder.itemView.findViewById(R.id.name)).setText(data.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    Intent intent = new Intent(view.getContext(), MockToolActivity.class);
                    view.getContext().startActivity(intent);
                }

            }
        });
    }


}