package com.supets.pet.mock.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.supets.commons.App;
import com.supets.commons.widget.CommonHeader;
import com.supets.pet.mock.bean.MockExampleData;
import com.supets.pet.mock.dao.MockExampleDataDB;
import com.supets.pet.mockui.R;

public class MockModelEditActivity extends AppCompatActivity {

    private MockExampleData mockExampleData;

    private EditText title;
    private EditText json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_updatemodel);

        String modelId = getIntent().getStringExtra("id");
        if (modelId != null) {
            mockExampleData = MockExampleDataDB.queryAllMockDataById(modelId).get(0);
        }

        initView();

    }

    private void initView() {

        title = (EditText) findViewById(R.id.title);
        json = (EditText) findViewById(R.id.json);

        if (mockExampleData != null) {
            title.setText(mockExampleData.getName());
            json.setText(mockExampleData.getData());
        }

        CommonHeader header = (CommonHeader) findViewById(R.id.header);
        header.getTitleTextView().setText(mockExampleData == null ? "添加数据模型" : "修改数据模型");
        header.getLeftButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        header.getRightButton().setText("保存");
        header.getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = title.getText().toString();
                String content = json.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    App.toast(" 数据模型名称不能为空");
                    return;
                }
                if (TextUtils.isEmpty(content)) {
                    App.toast(" 数据模型数据名称不能为空");
                    return;
                }

                if (mockExampleData == null) {
                    MockExampleDataDB.insertMockData(new MockExampleData(null, name, content));
                } else {
                    mockExampleData.setData(content);
                    mockExampleData.setName(name);
                    MockExampleDataDB.updateMockData(mockExampleData);
                }

                finish();

            }

        });
    }

}
