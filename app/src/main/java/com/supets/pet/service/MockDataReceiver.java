package com.supets.pet.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.supets.pet.jsonview.JSONViewHelper;
import com.supets.pet.jsonview.JSONViewHelper2;
import com.supets.pet.jsonview.JsonView;
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

                    String json = data.getData();

                    if (!Config.getToastSwitch() || !FormatLogProcess.isJson(json)) {

                        if (FormatLogProcess.isJson(json)) {
                            JsonView jsonView = JSONViewHelper2.parse(data.getData(), new LinearLayout(context));
                            json = JSONViewHelper.parse(jsonView);
                        }


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
                                        .append(FormatLogProcess.format(json))
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
