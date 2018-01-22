package com.supets.pet.mock.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * MockBook
 *
 * @user lihongjiang
 * @description
 * @date 2018/1/3
 * @updatetime 2018/1/3
 */

public abstract class SuspensionBarScrollListener extends RecyclerView.OnScrollListener {


    private int mCurrentPosition = 0;

    private int mSuspensionHeight;

    private View mSuspensionBar;

    public SuspensionBarScrollListener(View mSuspensionBar) {
        this.mSuspensionBar = mSuspensionBar;
    }

    public void setSuspensionBar(View mSuspensionBar) {
        this.mSuspensionBar = mSuspensionBar;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        mSuspensionHeight = mSuspensionBar.getHeight();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            onScrolledLinear(recyclerView, dx, dy);
        }
    }

    private void onScrolledLinear(RecyclerView recyclerView, int dx, int dy) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        if (recyclerView.getAdapter().getItemViewType(mCurrentPosition + 1) == getItemViewType()) {

            View view = layoutManager.findViewByPosition(mCurrentPosition + 1);
            if (view != null) {
                if (view.getTop() <= mSuspensionHeight) {
                    mSuspensionBar.setY(-(mSuspensionHeight - view.getTop()));
                } else {
                    mSuspensionBar.setY(0);
                }
            }
        }


        if (mCurrentPosition != layoutManager.findFirstVisibleItemPosition()) {
            mCurrentPosition = layoutManager.findFirstVisibleItemPosition();
            mSuspensionBar.setY(0);

            updateSuspensionBar(mCurrentPosition);
        }


    }

    protected abstract int getItemViewType();

    public abstract void updateSuspensionBar(int mCurrentPosition);


}
