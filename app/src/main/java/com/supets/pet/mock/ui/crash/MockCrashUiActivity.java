package com.supets.pet.mock.ui.crash;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.supets.commons.widget.CommonHeader;
import com.supets.pet.mock.bean.EmailData;
import com.supets.pet.mock.config.Config;
import com.supets.pet.mock.dao.EmailDataDB;
import com.supets.pet.mock.utils.QQMailUtil;
import com.supets.pet.mockui.R;

import java.util.List;

public class MockCrashUiActivity extends AppCompatActivity {

    CommonHeader mHeader;
    TextView mCrashLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_ui);

        String crashlog = getIntent().getStringExtra("crashlog");

        if (crashlog == null) {
            crashlog = "这是一封测试邮件,不需要回复";
        }

        mHeader = (CommonHeader) findViewById(R.id.header);
        mCrashLog = (TextView) findViewById(R.id.crashLog);

        mHeader.getLeftButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mHeader.getTitleTextView().setText(R.string.crash_title);
        mHeader.getRightButton().setText(R.string.send_mail);
        mHeader.getRightButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSendEmail();
            }
        });
        mCrashLog.setText(crashlog);
    }

    private void doSendEmail() {
        List<EmailData> data = EmailDataDB.getEmailList();
        final String[] list = new String[data.size()];
        for ( int i = 0; i < data.size(); i++) {
            list[i] = data.get(i).getName() + "<" + data.get(i).getEmail() + ">";
        }
        new AlertDialog.Builder(this)
                .setTitle(R.string.emaillist)
                .setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        QQMailUtil.setUser(Config.getEmailName());
                        QQMailUtil.setPassword(Config.getEmailPass());
                        QQMailUtil.sendEmail(Config.getEmailName(), data.get(which),
                                getString(R.string.crash_title),
                                mCrashLog.getText().toString());
                    }
                }).show();
    }

}
