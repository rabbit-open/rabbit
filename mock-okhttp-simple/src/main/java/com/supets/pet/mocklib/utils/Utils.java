package com.supets.pet.mocklib.utils;

import android.content.Intent;

import com.supets.pet.mocklib.AppContext;
import com.supets.pet.mocklib.widget.MockData;
import com.supets.pet.mocklib.widget.MockDataReceiver;
import com.supets.pet.mocklib.widget.TuZiWidget;

import java.net.URLDecoder;

public class Utils {

    public static String formatParam(String request) {
        StringBuffer sb = new StringBuffer();
        try {
            String[] params = request.split("&");
            for (String param : params) {
                sb.append(URLDecoder.decode(param)).append("\n");
            }
            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return request == null ? "" : request;
    }

    private static TuZiWidget mTipViewController;

    public static void showFloatView(Intent intent) {
        try {
            MockData data = new MockData();
            data.setUrl(intent.getStringExtra("url"));
            String uuid = intent.getStringExtra("uuid");
            if (uuid != null) {
                data.setData(MockDataReceiver.bigMessage.get(uuid));
                String[] strs = MockDataReceiver.bigRequest.get(uuid).split(uuid);
                if (strs.length == 3) {
                    data.setHeaderParam(strs[0]);
                    data.setRequestParam(strs[1]);
                    data.setResponseParam(strs[2]);
                } else {
                    data.setRequestParam(MockDataReceiver.bigRequest.get(uuid));
                }
            } else {
                return;
            }
            MockDataReceiver.bigMessage.remove(uuid);
            MockDataReceiver.bigMessage.remove(uuid);
            if (mTipViewController == null) {
                mTipViewController = new TuZiWidget(AppContext.INSTANCE);
            }
            mTipViewController.updateContent(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
