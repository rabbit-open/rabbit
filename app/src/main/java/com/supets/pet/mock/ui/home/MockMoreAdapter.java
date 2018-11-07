package com.supets.pet.mock.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supets.pet.mock.base.BaseRecycleAdapter;
import com.supets.pet.mock.base.BaseRecycleViewHolder;
import com.supets.pet.mock.ui.version.MockAboutActivity;
import com.supets.pet.mock.ui.config.MockConfigActivity;
import com.supets.pet.mock.ui.crash.MockCrashListActivity;
import com.supets.pet.mock.ui.email.MockEmailListActivity;
import com.supets.pet.mock.ui.datamodule.MockModelActivity;
import com.supets.pet.mock.ui.urltest.MockUrlRuleActivity;
import com.supets.pet.mockui.R;

public class MockMoreAdapter extends BaseRecycleAdapter<String> {


    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecycleViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mock_list_home_set_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final BaseRecycleViewHolder holder, int position) {
        ((TextView) holder.itemView.findViewById(R.id.name)).setText(data.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.getAdapterPosition() == 0) {
                    Intent intent = new Intent(view.getContext(), MockUrlRuleActivity.class);
                    view.getContext().startActivity(intent);
                } else if (holder.getAdapterPosition() == 1) {
                    Intent intent = new Intent(view.getContext(), MockConfigActivity.class);
                    view.getContext().startActivity(intent);
                } else if (holder.getAdapterPosition() == 2) {
                    Intent intent = new Intent(view.getContext(), MockEmailListActivity.class);
                    view.getContext().startActivity(intent);
                } else if (holder.getAdapterPosition() == 3) {
                    Intent intent = new Intent(view.getContext(), MockModelActivity.class);
                    view.getContext().startActivity(intent);
                } else if (holder.getAdapterPosition() == 4) {
                    Intent intent = new Intent(view.getContext(), MockCrashListActivity.class);
                    view.getContext().startActivity(intent);
                } else if (holder.getAdapterPosition() == 5) {
                    Intent intent = new Intent(view.getContext(), MockAboutActivity.class);
                    view.getContext().startActivity(intent);
                }

            }
        });
    }


}