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
import com.supets.pet.mock.bean.CrashData;
import com.supets.pet.mock.dao.CrashDataDB;
import com.supets.pet.mockui.R;

import java.util.ArrayList;
import java.util.List;

public class MockCrashListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_tab);

        initView();
        update();

    }

    private void update() {
        List<CrashData> datas = CrashDataDB.queryAll();
        if (datas != null) {
            adapter.setData(datas);
            adapter.notifyDataSetChanged();
        }
    }

    ListView mListView;
    MockAdapter adapter;

    private void initView() {
        CommonHeader header = (CommonHeader) findViewById(R.id.header);
        header.getTitleTextView().setText("异常抓取列表");
        header.getLeftButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        header.getRightButton().setText("刷新");
        header.getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrashDataDB.deleteAll();
                update();
            }
        });

        mListView = (ListView) findViewById(R.id.list);
        adapter = new MockAdapter();
        mListView.setAdapter(adapter);

    }


    class MockAdapter extends BaseAdapter {


        public List<CrashData> data = new ArrayList<>();


        public void setData(List<CrashData> data) {
            this.data = data;
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
                        .inflate(R.layout.mock_list_crash_item, viewGroup, false);
            }

            ((TextView) view.findViewById(R.id.name)).setText(data.get(position).getCrash());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), MockCrashUiActivity.class);
                    intent.putExtra("crashlog", data.get(position).getCrash());
                    view.getContext().startActivity(intent);
                }
            });

            return view;
        }
    }

}
