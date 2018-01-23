package com.supets.pet.mock.ui.home;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.supets.commons.widget.CommonHeader;
import com.supets.pet.mock.base.BaseFragment;
import com.supets.pet.mockui.R;

import java.util.ArrayList;
import java.util.List;


public class TabToolsFragment extends BaseFragment {

    private RecyclerView mList;
    private MockToolsAdapter adapter;
    private SwipeRefreshLayout mPull;
    private CommonHeader header;

    public static TabToolsFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString("content", content);
        TabToolsFragment tabContentFragment = new TabToolsFragment();
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
        mList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mPull = view.findViewById(R.id.swipe_refresh);
        mPull.setEnabled(false);
        header = view.findViewById(R.id.header);
        initView();
    }

    private void initView() {
        header.getTitleTextView().setText("工具");
        header.getTitleTextView().setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        header.getWholeView().setBackgroundResource(R.color.appcolor);
        header.getLeftButton().setVisibility(View.GONE);
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void process() {
        adapter = new MockToolsAdapter();
        mList.setAdapter(adapter);
        List<String> datas = new ArrayList<>();
        datas.add("JSON助手");
        datas.add("在线json解析");
        datas.add("在线二维码");
        datas.add("玩安卓");
        datas.add("画形状");
        adapter.setData(datas);
        adapter.notifyDataSetChanged();
    }


}
