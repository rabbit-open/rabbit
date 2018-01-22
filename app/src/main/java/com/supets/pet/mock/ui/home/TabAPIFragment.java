package com.supets.pet.mock.ui.home;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.supets.commons.widget.CommonHeader;
import com.supets.pet.mock.bean.LocalMockData;
import com.supets.pet.mock.dao.LocalMockDataDB;
import com.supets.pet.mock.base.BaseFragment;
import com.supets.pet.mock.dao.MockDataDB;
import com.supets.pet.mockui.R;

import java.util.List;


public class TabAPIFragment extends BaseFragment {

    private RecyclerView mList;
    private MockTestAdapter adapter;
    private SwipeRefreshLayout mPull;
    private CommonHeader header;




    public static TabAPIFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString("content", content);
        TabAPIFragment tabContentFragment = new TabAPIFragment();
        tabContentFragment.setArguments(arguments);
        return tabContentFragment;
    }


    @Override
    public int getContentLayout() {
        return R.layout.fragment_tab_content;
    }

    @Override
    public void findViews(View view) {
        mList = view.findViewById(R.id.list);
        mList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPull = view.findViewById(R.id.swipe_refresh);
        mPull.setEnabled(false);
        header = view.findViewById(R.id.header);
        initView();
    }

    private void initView() {
        header.getTitleTextView().setText(R.string.app_name);
        header.getTitleTextView().setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        header.getWholeView().setBackgroundResource(R.color.appcolor);
        header.getLeftButton().setVisibility(View.GONE);
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void process() {
        adapter = new MockTestAdapter();
        mList.setAdapter(adapter);
        resetLocalMockData();
        updateData();
    }

    private void resetLocalMockData() {
        List<String> datas = MockDataDB.queryAllUrl();
        if (datas != null) {
            for (String temp : datas) {
                List<LocalMockData> data = LocalMockDataDB.queryAllMockData(temp);
                if (data == null || data.size() == 0) {
                    LocalMockDataDB.insertMockData(new LocalMockData(null, temp, null, false));
                }
            }
        }
    }

    private void updateData() {
        List<LocalMockData> datas = LocalMockDataDB.queryAll();
        if (datas != null) {
            adapter.data.clear();
            adapter.setData(datas);
            adapter.notifyDataSetChanged();
        }
    }


}
