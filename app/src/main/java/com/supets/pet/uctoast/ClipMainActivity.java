package com.supets.pet.uctoast;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.supets.pet.mock.ui.home.MockUiActivity;
import com.supets.pet.mockui.R;

public final class ClipMainActivity extends AppCompatActivity {

    private final static String KEY_CONTENT = "content";
    private TextView mTextView;

    public static void startForContent(Context context, String content) {
        Intent intent = new Intent(context, MockUiActivity.class);
        intent.putExtra(KEY_CONTENT, content);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_main);
        mTextView =findViewById(R.id.text_view);
        Intent intent = getIntent();
        tryToShowContent(intent);
        ListenClipboardService.start(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        tryToShowContent(intent);
    }

    private void tryToShowContent(Intent intent) {
        String content = intent.getStringExtra(KEY_CONTENT);
        if (!TextUtils.isEmpty(content)) {
            mTextView.setText(content);
        }
    }

}