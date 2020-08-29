package com.supets.pet.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.supets.pet.mock.bean.MockData;
import com.supets.pet.mock.config.Config;
import com.supets.pet.mock.dao.MockDataDB;
import com.supets.pet.mock.utils.FileUtils;
import com.supets.pet.mock.utils.FormatLogProcess;

import java.util.Date;

public class MockDataReceiver extends BroadcastReceiver {

    public static final String MOCK_SERVICE_NETWORK = "mock.crash.network";
    private static TuZiWidget mTipViewController;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (MOCK_SERVICE_NETWORK.equals(intent.getAction())) {
            //满足过滤规则
            //抓取数据模式
            //调试模式不能打开
            if (Config.isFilterGuice(intent.getStringExtra("url"))
                    && Config.getDataMode()
                    && !Config.getDebugMode()) {
                try {
                    MockData data = new MockData();
                    String url = intent.getStringExtra("url");
                    data.setUrl(url);

                    String uuid = intent.getStringExtra("uuid");
                    String filePath = intent.getStringExtra("filePath");
                    if (uuid != null) {
                        String message = FileUtils.readFileContent(filePath);
                        String[] strs = message.split(uuid);
                        data.setHeaderParam(strs[0]);
                        data.setRequestParam(strs[1]);
                        data.setData(strs[2]);
                    } else {
                        data.setData(intent.getStringExtra("message"));
                        data.setRequestParam(intent.getStringExtra("requestParam"));
                    }
                    data.setTime(new Date());
                    MockDataDB.insertMockData(data);

                    final String json = data.getData();

                    //是否打开只抓取非json
                    if (!Config.getErrorJsonSwitch() || !FormatLogProcess.isJson(json)) {

                        if (Config.getToastinstance()) {
                            new TuZiWidget(context, data).setViewDismissHandler(() -> {
                            }).show();
                            return;
                        }

                        if (!TuZiWidget.isShow) {
                            mTipViewController = new TuZiWidget(context, data);
                            mTipViewController.setViewDismissHandler(() -> {

                            });
                            mTipViewController.show();
                        } else {
                            mTipViewController.updateContent(data);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    mTipViewController = null;
                }
            }


        }
    }
}
