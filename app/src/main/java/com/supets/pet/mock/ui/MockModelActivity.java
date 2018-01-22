package com.supets.pet.mock.ui;

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
import com.supets.pet.mock.bean.MockExampleData;
import com.supets.pet.mock.dao.MockExampleDataDB;
import com.supets.pet.mockui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试接口选择列表
 */
public class MockModelActivity extends AppCompatActivity {

    private ListView mListView;
    private MockAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_tab);

        initView();
    }

    private void updateData() {
        List<MockExampleData> datas = MockExampleDataDB.queryAll();
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
        CommonHeader header = (CommonHeader) findViewById(R.id.header);
        header.getTitleTextView().setText("数据模型");
        header.getLeftButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        header.getRightButton().setText("添加模型");
        header.getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MockModelEditActivity.class);
                startActivity(intent);
            }

        });

        mListView = (ListView) findViewById(R.id.list);
        adapter = new MockAdapter();
        mListView.setAdapter(adapter);
    }

    private class MockAdapter extends BaseAdapter {


        public List<MockExampleData> data = new ArrayList<>();


        public void setData(List<MockExampleData> data) {
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
                        .inflate(R.layout.mock_list_model_item, viewGroup, false);
            }

            ((TextView) view.findViewById(R.id.name)).setText(data.get(position).getName());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), MockModelEditActivity.class);
                    intent.putExtra("id", data.get(position).getId().toString());
                    startActivity(intent);
                }
            });

            view.findViewById(R.id.configjson).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MockExampleDataDB.deleteMockData(data.get(position));
                    updateData();
                }
            });
            return view;
        }
    }

}
