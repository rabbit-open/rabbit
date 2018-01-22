package com.supets.pet.mock.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.supets.commons.widget.CommonHeader;
import com.supets.pet.mock.base.SupetRecyclerViewScrollListener;
import com.supets.pet.mock.base.SuspensionBarScrollListener;
import com.supets.pet.mock.bean.MockData;
import com.supets.pet.mock.dao.MockDataDB;
import com.supets.pet.mock.base.BaseFragment;
import com.supets.pet.mockui.R;

import java.util.List;


public class TabDataFragment extends BaseFragment {

    private RecyclerView mList;
    private MockDataAdapter adapter;
    private SwipeRefreshLayout mPull;
    private CommonHeader header;

//    private TextView mBottom;
//    private int mCurrentPosition;

    public static TabDataFragment newInstance(String content) {
        Bundle arguments = new Bundle();
        arguments.putString("content", content);
        TabDataFragment tabContentFragment = new TabDataFragment();
        tabContentFragment.setArguments(arguments);
        return tabContentFragment;
    }


    @Override
    public int getContentLayout() {
        return R.layout.fragment_tab_content;
    }

    @Override
    public void findViews(View view) {

        //mBottom = view.findViewById(R.id.fudong);

        mList = view.findViewById(R.id.list);
        mList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList.setItemAnimator(null);

        mPull = view.findViewById(R.id.swipe_refresh);
        mPull.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                offset = 0;
                update();
                mPull.setRefreshing(false);
            }
        });
        mList.addOnScrollListener(new SupetRecyclerViewScrollListener() {
            @Override
            public void onLoadNextPage(RecyclerView view) {

                if (!mPull.isRefreshing()) {
                    update();
                }
            }
        });
        mPull.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
            @Override
            public boolean canChildScrollUp(SwipeRefreshLayout parent, @Nullable View child) {
                if (mList == null) {
                    return false;
                }
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mList.getLayoutManager();
                return linearLayoutManager.findFirstCompletelyVisibleItemPosition() != 0;
            }
        });

//        mList.addOnScrollListener(new SuspensionBarScrollListener(mBottom) {
//
//            @Override
//            protected int getItemViewType() {
//                return 0;
//            }
//
//            @Override
//            public void updateSuspensionBar(int position) {
//                mCurrentPosition = position;
//                updateSuspensionBar2();
//            }
//        });
        header = view.findViewById(R.id.header);
        initView();
    }

    private void initView() {
        header.getTitleTextView().setText(R.string.app_name);
        header.getTitleTextView().setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        header.getWholeView().setBackgroundResource(R.color.appcolor);
        header.getLeftButton().setVisibility(View.GONE);
    }
//
//    private void updateSuspensionBar2() {
//        mBottom.setText(adapter.data.get(mCurrentPosition).getUrl());
//    }


    @Override
    public void setListeners() {

    }

    @Override
    public void process() {
        adapter = new MockDataAdapter();
        mList.setAdapter(adapter);
        offset = 0;
        update();
        //updateSuspensionBar2();
    }

    private int offset = 0;

    private void update() {
        List<MockData> datas = MockDataDB.queryAllPage(offset);
        boolean nomore = datas == null || datas.size() < 20;

        if (!nomore) {
            offset++;
        }

        if (offset == 0) {
            adapter.addHomeData(datas);
        } else {
            adapter.addMoreData(datas);
        }

    }


}
