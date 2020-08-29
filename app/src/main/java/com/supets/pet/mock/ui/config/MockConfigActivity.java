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

    private CheckBox debugMode;
    private CheckBox dataMode;
    private CheckBox erroeJsonSwitch;
    private CheckBox mToastinstance;
    private CheckBox phoneSwitch;

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

        debugMode = findViewById(R.id.debugmode);
        debugMode.setOnClickListener(this);
        debugMode.setChecked(Config.getDebugMode());

        dataMode = findViewById(R.id.jsonswitch);
        dataMode.setOnClickListener(this);
        dataMode.setChecked(Config.getDataMode());

        erroeJsonSwitch = findViewById(R.id.toastmode);
        erroeJsonSwitch.setOnClickListener(this);
        erroeJsonSwitch.setChecked(Config.getErrorJsonSwitch());

        mToastinstance = findViewById(R.id.toastinstance);
        mToastinstance.setOnClickListener(this);
        mToastinstance.setChecked(Config.getToastinstance());

        phoneSwitch = findViewById(R.id.phoneSwitch);
        phoneSwitch.setOnClickListener(this);
        phoneSwitch.setChecked(Config.getToastCompat());

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
        if (v == debugMode) {
            Config.setDebugMode(debugMode.isChecked());
        }

        if (v == dataMode) {
            Config.setDataMode(dataMode.isChecked());
        }

        if (v == erroeJsonSwitch) {
            Config.setErrorJsonSwitch(erroeJsonSwitch.isChecked());
        }

        if (v == mToastinstance) {
            Config.setToastinstance(mToastinstance.isChecked());
        }
        if (v == phoneSwitch) {
            Config.setToastCompat(phoneSwitch.isChecked());
        }

    }

    @Override
    public void onViewDismiss() {

    }
}
