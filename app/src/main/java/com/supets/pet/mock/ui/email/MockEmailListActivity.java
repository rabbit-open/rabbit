package com.supets.pet.mock.ui.email;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.supets.commons.widget.CommonHeader;
import com.supets.pet.mock.bean.EmailData;
import com.supets.pet.mock.dao.EmailDataDB;
import com.supets.pet.mockui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试接口选择列表
 */
public class MockEmailListActivity extends AppCompatActivity {

    private ListView mListView;
    private MockAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_tab);
        initdata();
        initView();
    }

    private void initdata() {

        String[] names = getResources().getStringArray(R.array.defaultEmail);
        for (String name : names) {
            EmailData emailData = new EmailData();
            String[] emails = name.split("-");
            emailData.setEmail(emails[1]);
            emailData.setName(emails[0]);
            EmailDataDB.insertEmailData(emailData);
        }

    }

    private void updateData() {
        List<EmailData> datas = EmailDataDB.queryAll();
        if (datas != null) {
            adapter.data.clear();
            adapter.setData(datas);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }

    private void initView() {
        CommonHeader header = findViewById(R.id.header);
        header.getTitleTextView().setText("邮件列表");
        header.getLeftButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        header.getRightButton().setText("添加邮件");
        header.getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MockEmailEditActivity.class);
                startActivity(intent);
            }

        });

        mListView = findViewById(R.id.list);
        adapter = new MockAdapter();
        mListView.setAdapter(adapter);
    }

    private class MockAdapter extends BaseAdapter {


        public List<EmailData> data = new ArrayList<>();


        public void setData(List<EmailData> data) {
            this.data.addAll(data);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {

            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.mock_list_emaillist_item, viewGroup, false);
            }

            ((TextView) view.findViewById(R.id.name)).setText(data.get(position).getName() + "   " + data.get(position).getEmail());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), MockEmailEditActivity.class);
                    intent.putExtra("id", data.get(position).getId().toString());
                    startActivity(intent);
                }
            });

            view.findViewById(R.id.configjson).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EmailDataDB.deleteEmailData(data.get(position));
                    updateData();
                }
            });
            return view;
        }
    }

}
