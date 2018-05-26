package com.supets.pet.mock.ui.config;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.supets.commons.widget.CommonHeader;
import com.supets.pet.mock.config.Config;
import com.supets.pet.mockui.R;
import com.supets.pet.uctoast.ClipViewWidget;

public class MockConfigActivity extends AppCompatActivity implements View.OnClickListener, ClipViewWidget.ViewDismissHandler {

    private CheckBox mDebugSwitch;
    private CheckBox mDebugMore;
    private CheckBox mToastMode;
    private CheckBox mToastinstance;
    private CheckBox toast;

    private EditText mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_config);
        initView();
    }

    private void initView() {

        CommonHeader header = findViewById(R.id.header);
        header.getTitleTextView().setText("测试配置");

        header.getLeftButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mDebugSwitch = findViewById(R.id.debugmode);
        mDebugSwitch.setOnClickListener(this);
        mDebugSwitch.setChecked(Config.getDebugMode());

        mDebugMore = findViewById(R.id.jsonswitch);
        mDebugMore.setOnClickListener(this);
        mDebugMore.setChecked(Config.getJsonSwitch());

        mToastMode = findViewById(R.id.toastmode);
        mToastMode.setOnClickListener(this);
        mToastMode.setChecked(Config.getToastSwitch());

        mToastinstance = findViewById(R.id.toastinstance);
        mToastinstance.setOnClickListener(this);
        mToastinstance.setChecked(Config.getToastinstance());

        toast = findViewById(R.id.toast);
        toast.setOnClickListener(this);
        toast.setChecked(Config.getToast());

        mApi = findViewById(R.id.api);
        mApi.setText(Config.getBaseAPI());


        View apisave = findViewById(R.id.apisave);
        apisave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mApi.getText().toString())) {
                    Config.setBaseAPI(mApi.getText().toString().trim());
                    Toast.makeText(MockConfigActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                }

            }
        });

        final EditText emailname = findViewById(R.id.emailname);
        emailname.setText(Config.getEmailName());

        View emailnamesave = findViewById(R.id.emailnamesave);
        emailnamesave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(emailname.getText().toString())) {
                    Config.setEmailName(emailname.getText().toString().trim());
                    Toast.makeText(MockConfigActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                }

            }
        });


        final EditText emailpass = findViewById(R.id.emailpass);
        emailpass.setText(Config.getEmailPass());

        View emailpasssave = findViewById(R.id.emailpasssave);
        emailpasssave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(emailpass.getText().toString())) {
                    Config.setEmailPass(emailpass.getText().toString().trim());
                    Toast.makeText(MockConfigActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == mDebugSwitch) {
            Config.setDebugMode(mDebugSwitch.isChecked());
        }

        if (v == mDebugMore) {
            Config.setJsonSwitch(mDebugMore.isChecked());
        }

        if (v == mToastMode) {
            Config.setToastSwitch(mToastMode.isChecked());
        }

        if (v == mToastinstance) {
            Config.setToastinstance(mToastinstance.isChecked());
        }

        if (v == toast) {
            Config.setToast(toast.isChecked());
        }
    }

    @Override
    public void onViewDismiss() {

    }
}
