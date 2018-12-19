package com.supets.pet.mocklib.widget;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MockDataReceiver extends BroadcastReceiver {

    public static final String MOCK_SERVICE_NETWORK = "mock.crash.network2";
    private static TuZiWidget mTipViewController;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (MOCK_SERVICE_NETWORK.equals(intent.getAction())) {

            try {
                MockData data = new MockData();
                data.setUrl(intent.getStringExtra("url"));
                data.setData(intent.getStringExtra("message"));
                data.setRequestParam(intent.getStringExtra("requestParam"));

                if (!TuZiWidget.isShow) {
                    mTipViewController = new TuZiWidget(context, data);
                    mTipViewController.setViewDismissHandler(() -> {

                    });
                    mTipViewController.show();
                } else {
                    mTipViewController.updateContent(data);
                }

            } catch (Exception e) {
                e.printStackTrace();
                mTipViewController = null;
            }
        }

    }
}
