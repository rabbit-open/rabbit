package com.supets.pet.mock.ui.home;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.supets.commons.widget.CommonHeader;
import com.supets.pet.mock.base.BaseFragment;
import com.supets.pet.mockui.R;

import java.util.ArrayList;
import java.util.List;


public class TabSetFragment extends BaseFragment {

    private RecyclerView mList;
    private MockMoreAdapter adapter;
    private SwipeRefreshLayout mPull;
    private CommonHeader header;

    public static TabSetFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString("content", content);
        TabSetFragment tabContentFragment = new TabSetFragment();
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
        header.getTitleTextView().setText("设置");
        header.getTitleTextView().setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        header.getWholeView().setBackgroundResource(R.color.appcolor);
        header.getLeftButton().setVisibility(View.GONE);
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void process() {
        adapter = new MockMoreAdapter();
        mList.setAdapter(adapter);
        List<String> datas = new ArrayList<>();
        datas.add("映射测试");
        datas.add("测试配置");
        datas.add("邮件管理");
        datas.add("数据模型");
        datas.add("异常管理");
        datas.add("版本更新");
        adapter.setData(datas);
        adapter.notifyDataSetChanged();
    }


}
