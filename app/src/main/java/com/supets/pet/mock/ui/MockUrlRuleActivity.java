package com.supets.pet.mock.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.supets.commons.widget.CommonHeader;
import com.supets.pet.mockui.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockUrlRuleActivity extends AppCompatActivity {

    private ListView mListView;
    private EditText mWebView;
    private CommonHeader mHeader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_url);

        mHeader = findViewById(R.id.header);
        mWebView = findViewById(R.id.webview);
        mListView = findViewById(R.id.list);

        mHeader.getTitleTextView().setText("映射测试");
        mHeader.getLeftButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mWebView.setText("cloudpet://main");

        findViewById(R.id.testgo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(mWebView.getText().toString()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        UrlRuleAdapter adapter = new UrlRuleAdapter();
        String[] urls = getResources().getStringArray(R.array.testurlconfig);
        adapter.setData(Arrays.asList(urls));
        mListView.setAdapter(adapter);
    }


    class UrlRuleAdapter extends BaseAdapter {

        public List<String> data = new ArrayList<>();

        public void setData(List<String> data) {
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
                        .inflate(R.layout.mock_list_tab_item, viewGroup, false);
            }

            ((TextView) view.findViewById(R.id.name)).setText(data.get(position));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mWebView.setText((String) getItem(position));
                }
            });
            return view;
        }
    }

}
