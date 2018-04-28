package com.supets.pet.mock.ui.translate;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.supets.commons.utils.json.JSonUtil;
import com.supets.pet.mockui.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class TranslateActivity extends FragmentActivity {


    private PlayMp3 mp3 = new PlayMp3();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_translate);

        EditText input = findViewById(R.id.input);
        findViewById(R.id.search).setOnClickListener(view -> {
            String words = input.getText().toString();
            if (!TextUtils.isEmpty(words)) {
                requestData(words.toLowerCase());
            }
        });

        String words = getIntent().getStringExtra("content");
        if (!TextUtils.isEmpty(words)) {
            input.setText(words.toLowerCase());
            requestData(words.toLowerCase());
        }
    }


    public void requestData(String key) {

        OkHttpUtils.get().url("http://dict-co.iciba.com/api/dictionary.php")
                .addParams("w", key)
                .addParams("type", "json")
                .addParams("key", "B6B16CB466B7A85503BC9E1BA38FFA74")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(TranslateActivity.this, "网络失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {

                Log.v("json", response);

                WordTranslateDTO dto = JSonUtil.fromJson(response, WordTranslateDTO.class);
                if (dto != null) {
                    if (!TextUtils.isEmpty(dto.word_name)) {
                        updateData(dto);
                    } else {
                        Toast.makeText(TranslateActivity.this, "没有查到", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TranslateActivity.this, "JSON解析错误", Toast.LENGTH_SHORT).show();
                }
            }


        });

    }

    private void updateData(WordTranslateDTO dto) {
        TextView content = findViewById(R.id.webview);
        content.setText(getClickableHtml(dto.getsymbols()));//解析html
        content.setMovementMethod(LinkMovementMethod.getInstance());//设置可点击
    }


    private void setLinkClickable(final SpannableStringBuilder clickableHtmlBuilder,
                                  final URLSpan urlSpan) {
        int start = clickableHtmlBuilder.getSpanStart(urlSpan);
        int end = clickableHtmlBuilder.getSpanEnd(urlSpan);
        int flags = clickableHtmlBuilder.getSpanFlags(urlSpan);
        ClickableSpan clickableSpan = new ClickableSpan() {

            public void onClick(View view) {
                String url = urlSpan.getURL();
                mp3.play(url);
            }


            public void updateDrawState(TextPaint ds) {
                //设置颜色
                ds.setColor(Color.argb(255, 54, 92, 124));
                //设置是否要下划线
                ds.setUnderlineText(false);
            }

        };
        clickableHtmlBuilder.setSpan(clickableSpan, start, end, flags);
    }

    private CharSequence getClickableHtml(String html) {
        Spanned spannedHtml = Html.fromHtml(html);
        SpannableStringBuilder clickableHtmlBuilder = new SpannableStringBuilder(spannedHtml);
        URLSpan[] urls = clickableHtmlBuilder.getSpans(0, spannedHtml.length(), URLSpan.class);
        for (final URLSpan span : urls) {
            setLinkClickable(clickableHtmlBuilder, span);
        }
        return clickableHtmlBuilder;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp3.destroy();
    }
}
