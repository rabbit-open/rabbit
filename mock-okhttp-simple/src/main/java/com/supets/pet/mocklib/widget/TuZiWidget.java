package com.supets.pet.mocklib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.supets.pet.mocklib.R;

public final class TuZiWidget implements View.OnClickListener, View.OnTouchListener, ViewContainer.KeyEventHandler {

    private WindowManager mWindowManager;
    private Context mContext;
    private ViewContainer mWholeView;
    private View mContentView;
    private RecyclerView mList;
    private GridAdapter adapter;

    @SuppressLint("ClickableViewAccessibility")
    public TuZiWidget(Context application) {
        mContext = application;
        mWindowManager = (WindowManager) application.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        initView();
    }

    private void initView() {
        ViewContainer view = (ViewContainer) View.inflate(mContext, R.layout.tuzi_pop_view, null);
        mList = view.findViewById(R.id.list);
        mList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter = new GridAdapter();
        mList.setAdapter(adapter);
        mWholeView = view;
        mContentView = view.findViewById(R.id.pop_view_content_view);
        // event listeners
        mContentView.setOnClickListener(this);
        mWholeView.setOnTouchListener(this);
        mWholeView.setKeyEventHandler(this);
    }

    public void updateContent(MockData content) {
        if (mWholeView.getParent() == null) {
            adapter.addHomeData(content);
            show();
        } else {
            adapter.putData(content);
        }
    }

    private void show() {
        int w = WindowManager.LayoutParams.MATCH_PARENT;
        int h = WindowManager.LayoutParams.MATCH_PARENT;

        int flags = 0;
        int type = 0;

        if (Build.VERSION.SDK_INT >= 24) {
            if (Settings.canDrawOverlays(mContext)) {
                type = WindowManager.LayoutParams.TYPE_PHONE;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                }
            }
        } else {
            type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(w, h, type, flags, PixelFormat.TRANSLUCENT);
        layoutParams.gravity = Gravity.TOP;
        mWindowManager.addView(mWholeView, layoutParams);
    }

    @Override
    public void onClick(View v) {
        removePoppedViewAndClear();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void removePoppedViewAndClear() {
        if (mWindowManager != null && mWholeView != null && mWholeView.getParent() != null) {
            mWindowManager.removeView(mWholeView);
        }
    }

    /**
     * touch the outside of the content view, remove the popped view
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Rect rect = new Rect();
        mContentView.getGlobalVisibleRect(rect);
        if (!rect.contains(x, y)) {
            removePoppedViewAndClear();
        }
        return false;
    }

    @Override
    public void onKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            removePoppedViewAndClear();
        }
    }

}
