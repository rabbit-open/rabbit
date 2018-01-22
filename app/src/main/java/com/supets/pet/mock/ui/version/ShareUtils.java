package com.supets.pet.mock.ui.version;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;

public class ShareUtils {

    public void shareTextInfo(Context context, String text) {
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(textIntent, "分享"));
    }

    public void sharePicInfo(Context context, String localpath) {
        Intent imageIntent = new Intent(Intent.ACTION_SEND);
        imageIntent.setType("image/jpeg");
        imageIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(localpath));
        context.startActivity(Intent.createChooser(imageIntent, "分享"));
    }

    public void sharePicInfo(Context context, Uri... localpath) {
        ArrayList<Uri> imageUris = new ArrayList<>();
        for (Uri uri : localpath) {
            imageUris.add(uri);
        }
        Intent mulIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        mulIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        mulIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(mulIntent, "多文件分享"));

    }

    //指定分享到微信
    public void shareTextWeiXinInfo(Context context, String text) {
        Intent wechatIntent = new Intent(Intent.ACTION_SEND);
        wechatIntent.setPackage("com.tencent.mm");
        wechatIntent.setType("text/plain");
        wechatIntent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(wechatIntent);
    }

    //指定分享到QQ
    public void shareTextQQInfo(Context context, String text) {
        Intent qqIntent = new Intent(Intent.ACTION_SEND);
        qqIntent.setPackage("com.tencent.mobileqq");
        qqIntent.setType("text/plain");
        qqIntent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(qqIntent);
    }

}
