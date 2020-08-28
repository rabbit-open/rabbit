package com.supets.pet.uctoast;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.supets.pet.mock.ui.translate.TranslateActivity;
import com.supets.pet.mockui.R;

public final class ClipViewWidget implements View.OnClickListener, View.OnTouchListener, ViewContainer.KeyEventHandler {

    private WindowManager mWindowManager;
    private Context mContext;
    private ViewContainer mWholeView;
    private View mContentView;
    private ViewDismissHandler mViewDismissHandler;
    private String mContent;
    private TextView mTextView;

    public ClipViewWidget(Context application, String content) {
        mContext = application;
        mContent = content;
        mWindowManager = (WindowManager) application.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    }

    public void setViewDismissHandler(ViewDismissHandler viewDismissHandler) {
        mViewDismissHandler = viewDismissHandler;
    }

    public void updateContent(String content) {
        mContent = content;
        mTextView.setText(mContent);
    }

    public void show() {

        ViewContainer view = (ViewContainer) View.inflate(mContext, R.layout.clipword_pop_view, null);

        // display content
        mTextView = view.findViewById(R.id.pop_view_text);
        mTextView.setText(mContent);

        mWholeView = view;
        mContentView = view.findViewById(R.id.pop_view_content_view);

        // event listeners
        mContentView.setOnClickListener(this);
        mWholeView.setOnTouchListener(this);
        mWholeView.setKeyEventHandler(this);

        int w = WindowManager.LayoutParams.MATCH_PARENT;
        int h = WindowManager.LayoutParams.MATCH_PARENT;

        int flags = 0;
        int type = 0;

        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(mContext)) {
                type = WindowManager.LayoutParams.TYPE_PHONE;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                }
            } else {
                type = WindowManager.LayoutParams.TYPE_TOAST;
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

        Intent intent = new Intent(mContext, TranslateActivity.class);
        intent.putExtra("content", mContent);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }


    private void removePoppedViewAndClear() {

        // remove view
        if (mWindowManager != null && mWholeView != null) {
            mWindowManager.removeView(mWholeView);
        }

        if (mViewDismissHandler != null) {
            mViewDismissHandler.onViewDismiss();
        }

        // remove listeners
        mContentView.setOnClickListener(null);
        mWholeView.setOnTouchListener(null);
        mWholeView.setKeyEventHandler(null);
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

    public interface ViewDismissHandler {
        void onViewDismiss();
    }
}
