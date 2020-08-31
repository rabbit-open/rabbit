package com.supets.pet.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.supets.pet.bean.MockData;
import com.supets.pet.config.Config;
import com.supets.pet.mockui.R;
import com.supets.pet.utils.WifiUtils;

public final class TuZiWidget implements View.OnClickListener, View.OnTouchListener, ViewContainer.KeyEventHandler {

    private WindowManager mWindowManager;
    private Context mContext;
    private ViewContainer mWholeView;
    private RecyclerView mList;
    private GridAdapter adapter;
    private TextView logoTitle;

    @SuppressLint("ClickableViewAccessibility")
    public TuZiWidget(Context application) {
        mContext = application;
        mWindowManager = (WindowManager) application.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        initView();
    }


    private void initView() {
        ViewContainer view = (ViewContainer) View.inflate(mContext, R.layout.tuzi_pop_view, null);
        mList = view.findViewById(R.id.list);
        logoTitle = view.findViewById(R.id.logoTitle);
        setWifi();
        mList.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter = new GridAdapter();
        mList.setAdapter(adapter);
        mWholeView = view;
        mWholeView.setOnTouchListener(this);
        mWholeView.setKeyEventHandler(this);

    }

    private void setWifi() {
        String ip = WifiUtils.getWifiIp(mContext);
        if (WifiUtils.getWifiIp(mContext) != null) {
            logoTitle.setText("兔子小助手（" + ip + ")");
        } else {
            logoTitle.setText("兔子小助手");
        }
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
        setWifi();
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
            type = WindowManager.LayoutParams.TYPE_TOAST;//24 不允许定义Toast对话框
        }

        if (Config.getToastCompat()) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (Settings.canDrawOverlays(mContext)) {
                    type = WindowManager.LayoutParams.TYPE_PHONE;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                    }
                }
            } else {
                type = WindowManager.LayoutParams.TYPE_PHONE;
            }
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
        removePoppedViewAndClear();
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
