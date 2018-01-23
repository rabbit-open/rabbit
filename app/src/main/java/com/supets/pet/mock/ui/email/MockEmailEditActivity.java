package com.supets.pet.mock.ui.email;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.supets.commons.App;
import com.supets.commons.widget.CommonHeader;
import com.supets.pet.mock.bean.EmailData;
import com.supets.pet.mock.dao.EmailDataDB;
import com.supets.pet.mockui.R;


public class MockEmailEditActivity extends AppCompatActivity {

    private EmailData mockExampleData;

    private EditText name;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_updateemail);

        String modelId = getIntent().getStringExtra("id");
        if (modelId != null) {
            mockExampleData = EmailDataDB.queryEmailDataById(modelId).get(0);
        }

        initView();

    }

    private void initView() {

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);

        if (mockExampleData != null) {
            name.setText(mockExampleData.getName());
            email.setText(mockExampleData.getEmail());
        }

        CommonHeader header = findViewById(R.id.header);
        header.getTitleTextView().setText(mockExampleData == null ? "添加邮箱" : "修改邮箱");
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

                String name2 = name.getText().toString();
                String email2 = email.getText().toString();

                if (TextUtils.isEmpty(name2)) {
                    App.toast(" 名称不能为空");
                    return;
                }
                if (TextUtils.isEmpty(email2)) {
                    App.toast(" 邮箱名称不能为空");
                    return;
                }

                if (mockExampleData == null) {
                    EmailDataDB.insertEmailData(new EmailData(null, name2, email2));
                } else {
                    mockExampleData.setName(name2);
                    mockExampleData.setEmail(email2);
                    EmailDataDB.updateEmailData(mockExampleData);
                }

                finish();

            }

        });
    }

}
