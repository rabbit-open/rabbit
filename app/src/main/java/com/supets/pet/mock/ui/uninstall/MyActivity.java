//package com.supets.pet.mock.ui.uninstall;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ActivityCompat;
//
//public class MyActivity extends Activity {
//    private static final int REQUEST_INSTALL = 124;
//    private final UpdateBuilder builder = UpdateBuilder.create();
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        /**
//         * 请在Application中进行UpdateConfig的全局配置
//         */
//        builder.setInstallStrategy((context, s, update) -> {
//            if (Build.VERSION.SDK_INT < 26) {
//                //直接安装
//                new DefaultInstallStrategy().install(context, s, update);
//            } else if (getPackageManager().canRequestPackageInstalls()) {//26 版本才有此方法
//                //可以安装未知来源应用
//                new DefaultInstallStrategy().install(context, s, update);
//            } else {
//                //申请权限
//                if (ActivityCompat.shouldShowRequestPermissionRationale(MyActivity.this, Manifest.permission.REQUEST_INSTALL_PACKAGES)) {
//                    //自定义的Dialog，可以用Android系统自带Dialog代替
//                    new MessageDialog.Builder(UMainActivity.this)
//                            .title("提示")
//                            .content("为了正常升级“预逍”APP,请允许“预逍”安装未知来源应用，本功能只限用于版本升级")
//                            .positiveText("确定")
//                            .negativeText("取消")
//                            .canceledOnTouchOutside(false)
//                            .cancelable(false)
//                            .onPositive((dialog, which) -> ActivityCompat.requestPermissions(MyActivity.this,
//                                    new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES},
//                                    REQUEST_INSTALL))
//                            .onNegative(((dialog, which) -> ActivityCompat.requestPermissions(MyActivity.this,
//                                    new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES},
//                                    REQUEST_INSTALL)))
//                            .show();
//                } else {
//                    ActivityCompat.requestPermissions(MyActivity.this,
//                            new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES},
//                            REQUEST_INSTALL);
//                }
//            }
//        });
//        builder.check();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_INSTALL) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                builder.check();
//            } else {
//                //启动授权页面
//                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + getPackageName()));
//                startActivityForResult(intent, REQUEST_INSTALL);
//            }
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_INSTALL) {
//            builder.check();
//        }
//    }
//
//
//}
