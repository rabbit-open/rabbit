package com.supets.pet.mock.ui.version;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.supets.commons.utils.SystemUtils;
import com.supets.commons.utils.json.JSonUtil;
import com.supets.pet.mockui.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class MockAboutActivity extends AppCompatActivity {


    private TextView mVersionNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_about);

        mVersionNum = findViewById(R.id.versionNum);
        View versionupdate = findViewById(R.id.versionupdate);
        versionupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    OkHttpUtils.get().url(
                            "https://raw.githubusercontent.com/rabbit-open/rabbit/master/database/version_update.json")
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(String response, int id) {

                                    VersionDTO dto = JSonUtil.fromJson(response, VersionDTO.class);
                                    if (dto != null) {
                                        String versionBuidCode = SystemUtils.getAppVersionCode();
                                        if (!TextUtils.isEmpty(versionBuidCode) && dto.content.version != Integer.parseInt(versionBuidCode)) {
                                            updateData(dto);
                                        } else {
                                            Toast.makeText(MockAboutActivity.this, "已经是最新版本", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(MockAboutActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        String versionCode = getString(R.string.m_user_version_code,
                SystemUtils.getAppVersionName());
        String versionBuidCode = SystemUtils.getAppVersionCode();
        mVersionNum.setText(versionCode + "（" + versionBuidCode + "）");
    }


    public void updateData(final VersionDTO dto) {
        new AlertDialog.Builder(this)
                .setTitle("版本更新")
                .setMessage(dto.content.text)
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pushUrl(dto.content.url);
                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }


    private void pushUrl(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }


}
