package com.supets.pet.mocklib.widget;


import android.content.Context;
import android.content.Intent;

public class MockDataReceiver {

    private static TuZiWidget mTipViewController;

    public static void onReceive(Context context, Intent intent) {

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
