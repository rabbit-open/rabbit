package com.supets.pet.alert;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.supets.pet.mockui.R;
import com.supets.pet.uctoast.ClipMainActivity;


public class PullMsgService extends Service {

    View mFloatLayout;
    LayoutParams wmParams;
    WindowManager mWindowManager;

    private long MinUnit = 1 * 1000;
    private long MaxTine = 5 * MinUnit;
    private boolean isRun = false;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!isRun) {
            isRun = true;
            HandlerThread handlerThread = new HandlerThread("msg");
            handlerThread.start();
            handler = new Handler(handlerThread.getLooper());
            sendRequest();
        }
    }


    private Handler uiHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (isStop) {
                return false;
            }
            if (msg.what == 0x1100) {

                if (mFloatLayout != null) {
                    mWindowManager.removeView(mFloatLayout);
                    mFloatLayout = null;
                }
                createFloatView();
                if (isStop) {
                    return false;
                }
                //uiHandler.sendEmptyMessageDelayed(0x1101, 3000);
            }

            if (msg.what == 0x1101) {
                if (mFloatLayout != null) {
                    mWindowManager.removeView(mFloatLayout);
                    mFloatLayout = null;
                }
                sendRequest();
            }
            return false;
        }
    });

    private Handler handler;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //fail
            // sendRequest();

            //success
            if (isStop) {
                return;
            }
            uiHandler.sendMessage(uiHandler.obtainMessage(0x1100, null));

        }
    };


    private void sendRequest() {
        if (isStop) {
            return;
        }
        handler.postDelayed(runnable, MaxTine);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createFloatView() {

        MaxTine = 5 * MinUnit;

        //LocalWindowManager
        //mWindowManager = this.getWindowManager();
        //mWindowManager = getWindow().getWindowManager();

        //CompatModeWrapper
        mWindowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);

        wmParams = new LayoutParams();
        wmParams.windowAnimations = R.style.Animation_Msg;
        wmParams.type = LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.width = LayoutParams.MATCH_PARENT;
        wmParams.height = LayoutParams.WRAP_CONTENT;

        final LayoutInflater inflater = LayoutInflater.from(getApplication());
        mFloatLayout = inflater.inflate(R.layout.float_window_small, null);

        mFloatLayout.findViewById(R.id.percent);

        mWindowManager.addView(mFloatLayout, wmParams);

        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        mFloatLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ClipMainActivity.startForContent(getApplicationContext(), null);
                uiHandler.removeMessages(0x1101);
                uiHandler.sendEmptyMessage(0x1101);
            }
        });

    }

    private boolean isStop = false;

    @Override
    public void onDestroy() {
        super.onDestroy();
        isStop = true;
        uiHandler.removeMessages(0x1100);
        uiHandler.removeMessages(0x1101);
        handler.removeCallbacks(runnable);

        if (mFloatLayout != null) {
            mWindowManager.removeView(mFloatLayout);
            mFloatLayout = null;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (mFloatLayout != null) {
            mWindowManager.removeView(mFloatLayout);
            mFloatLayout = null;
        }
        isStop = true;
        uiHandler.removeMessages(0x1100);
        uiHandler.removeMessages(0x1101);
        handler.removeCallbacks(runnable);
        return super.onUnbind(intent);
    }
}
