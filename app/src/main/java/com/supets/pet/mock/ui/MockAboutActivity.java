package com.supets.pet.mock.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.supets.commons.utils.SystemUtils;
import com.supets.pet.mockui.R;

public class MockAboutActivity extends AppCompatActivity {


    private TextView  mVersionNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_about);

        mVersionNum=findViewById(R.id.versionNum);

        String versionCode = getString(R.string.m_user_version_code,
                SystemUtils.getAppVersionName());
        String versionBuidCode = SystemUtils.getAppVersionCode();
        mVersionNum.setText(versionCode + "（" + versionBuidCode + "）");

    }
}
