package com.example.qr_codescan;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.minging.app.zxing.activity.QRcodeUtils;

public class CreateQRcodeDemo extends Activity {


    private EditText codeEdit;

    private Button twoCodeBtn;
    private ImageView codeImg;


    private Button oneCodeBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        setListener();

    }


    private void initView() {
        codeEdit = (EditText) findViewById(R.id.code_edittext);
        twoCodeBtn = (Button) findViewById(R.id.code_btn);
        oneCodeBtn = (Button) findViewById(R.id.btn_code);
        codeImg = (ImageView) findViewById(R.id.code_img);
    }

    private void setListener() {
        twoCodeBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String str = codeEdit.getText().toString().trim();
                Bitmap bmp = null;
                try {
                    if (str != null && !"".equals(str)) {
                        bmp = QRcodeUtils.CreateTwoDCode(str);
                    }
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                if (bmp != null) {
                    codeImg.setImageBitmap(bmp);
                }

            }
        });


        oneCodeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = codeEdit.getText().toString().trim();
                int size = str.length();
                for (int i = 0; i < size; i++) {
                    int c = str.charAt(i);
                    if ((19968 <= c && c < 40623)) {
                        Toast.makeText(CreateQRcodeDemo.this, "�����������ʱ�̲���������", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
                Bitmap bmp = null;
                try {
                    if (str != null && !"".equals(str)) {
                        bmp = QRcodeUtils.CreateOneDCode(str);
                    }
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                if (bmp != null) {
                    codeImg.setImageBitmap(bmp);
                }
            }
        });
    }


}
