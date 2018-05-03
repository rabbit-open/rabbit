package com.supets.pet.mock.ui.version;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.supets.commons.utils.SystemUtils;
import com.supets.commons.utils.json.JSonUtil;
import com.supets.pet.mock.db.WordDao;
import com.supets.pet.mock.utils.Utils;
import com.supets.pet.mockui.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Set;

import okhttp3.Call;

public class MockAboutActivity extends AppCompatActivity {


    private TextView mVersionNum;
    private Button versionupdate2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_about);

        mVersionNum = findViewById(R.id.versionNum);
        View versionupdate = findViewById(R.id.versionupdate);
        versionupdate2 = findViewById(R.id.versionupdate2);
        versionupdate2.setOnClickListener(view -> updateWord());

        versionupdate.setOnClickListener(v -> update());
        String versionCode = getString(R.string.m_user_version_code,
                SystemUtils.getAppVersionName());
        String versionBuidCode = SystemUtils.getAppVersionCode();
        mVersionNum.setText(versionCode + "（" + versionBuidCode + "）");
    }


    private void updateWord() {
        try {
            OkHttpUtils.get().url(
                    "https://raw.githubusercontent.com/rabbit-open/rabbit/master/database/words_update.json")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(MockAboutActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            WordsDTO dto = JSonUtil.fromJson(response, WordsDTO.class);
                            if (dto != null) {
                                updateWords(dto);
                            } else {
                                Toast.makeText(MockAboutActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateWords(WordsDTO dto) {
        versionupdate2.setEnabled(false);
        versionupdate2.setText("正在更新。。。");
        WordDao wordDao = new WordDao(this);


        new Thread(new Runnable() {
            @Override
            public void run() {
                if (dto.content != null) {

                    Set<String> sets = dto.content.keySet();

                    for (String key : sets) {

                        String[] contents = dto.content.get(key);

                        for (int i = 0; i < contents.length; i++) {

                            String[] names2 = contents[i].split(" ");
                            for (int j = 0; j < names2.length; j++) {
                                if (!wordDao.findNameByModule(key, names2[j])) {
                                    Log.v("android", key + "---" + names2[j]);
                                    wordDao.addNewData(key, names2[j]);
                                }
                            }


                        }


                    }
                }

                mVersionNum.post(new Runnable() {
                    @Override
                    public void run() {

                        versionupdate2.setEnabled(true);
                        versionupdate2.setText("单词更新");
                    }
                });

            }
        }).start();


    }

    private void update() {
        try {
            OkHttpUtils.get().url(
                    "https://raw.githubusercontent.com/rabbit-open/rabbit/master/database/version_update.json")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(MockAboutActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
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


    public void updateData(final VersionDTO dto) {
        new AlertDialog.Builder(this)
                .setTitle("版本更新")
                .setMessage(dto.content.text)
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.pushUrl(MockAboutActivity.this, dto.content.url);
                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }


}
