package com.supets.pet.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.supets.pet.mock.bean.MockData;
import com.supets.pet.mock.config.Config;
import com.supets.pet.mock.dao.MockDataDB;
import com.supets.pet.mock.utils.FormatLogProcess;
import com.supets.pet.mock.utils.Utils;
import com.supets.pet.uctoast.TipViewController;

import java.util.Date;

public class MockDataReceiver extends BroadcastReceiver {

    public static final String MOCK_SERVICE_NETWORK = "mock.crash.network";

    @Override
    public void onReceive(Context context, Intent intent) {


        if (MOCK_SERVICE_NETWORK.equals(intent.getAction())) {

            if (Config.isFilterGuice(intent.getStringExtra("url"))
                    && Config.getJsonSwitch()
                    && !Config.getDebugMode()) {
                try {
                    MockData data = new MockData();
                    data.setUrl(intent.getStringExtra("url"));
                    data.setData(intent.getStringExtra("message"));
                    data.setRequestParam(intent.getStringExtra("requestParam"));
                    data.setTime(new Date());
                    MockDataDB.insertMockData(data);

                    final String json = data.getData();

                    if (!Config.getToastSwitch() || !FormatLogProcess.isJson(json)) {


                        String message =
                                new StringBuffer().append("接口名称：")
                                        .append("\r\n")
                                        .append(data.getUrl())
                                        .append("\r\n")
                                        .append("请求参数：")
                                        .append("\r\n")
                                        .append(Utils.formatParam(data.getRequestParam()))
                                        .append("\r\n")
                                        .append("请求结果：")
                                        .append("\r\n")
                                        .append(FormatLogProcess.format(FormatLogProcess.formatJsonText(json)))
                                        .toString();

                        TipViewController mTipViewController = new TipViewController(context, message);
                        mTipViewController.setViewDismissHandler(null);
                        mTipViewController.show();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
    }
}
