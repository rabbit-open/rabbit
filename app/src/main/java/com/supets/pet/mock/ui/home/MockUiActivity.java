package com.supets.pet.mock.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;

import com.supets.pet.uctoast.ListenClipboardService;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import io.reactivex.disposables.Disposable;


public class MockUiActivity extends TabLayoutBottomActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        update();
        ListenClipboardService.start(this);

        permissionCheck();

        if (!checkFloatPermission(this)) {
            requestFloatPermission(MockUiActivity.this, FloatRequestCode);
        }
    }


    Disposable disposable;

    private void permissionCheck() {
        RxPermissions rxPermissions = new RxPermissions(this);
        disposable = rxPermissions.requestEachCombined(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.CAMERA)
                .subscribe(permission -> {
                    //只回调一次
                    if (permission.granted) {
                        //全部权限获取成功
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        //至少一个权限获取失败，但是用户没有勾选”不再询问“，在这里应该弹出对话框向用户解释为何需要该权限
                    } else {
                        //至少一个权限申请失败，用户勾选了“不再询问”，在这里应该引导用户去设置页面打开权限
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }


    private static final int FloatRequestCode = 1002;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FloatRequestCode) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!checkFloatPermission(MockUiActivity.this)) {
                        requestFloatPermission(MockUiActivity.this, FloatRequestCode);
                    }
                }
            }, 1000);
        }
    }

    /***
     * 检查悬浮窗开启权限
     * @param context
     * @return
     */
    public boolean checkFloatPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return true;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                Class cls = Class.forName("android.content.Context");
                Field declaredField = cls.getDeclaredField("APP_OPS_SERVICE");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(cls);
                if (!(obj instanceof String)) {
                    return false;
                }
                String str2 = (String) obj;
                obj = cls.getMethod("getSystemService", String.class).invoke(context, str2);
                cls = Class.forName("android.app.AppOpsManager");
                Field declaredField2 = cls.getDeclaredField("MODE_ALLOWED");
                declaredField2.setAccessible(true);
                Method checkOp = cls.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class);
                int result = (Integer) checkOp.invoke(obj, 24, Binder.getCallingUid(), context.getPackageName());
                return result == declaredField2.getInt(cls);
            } catch (Exception e) {
                return false;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                AppOpsManager appOpsMgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                if (appOpsMgr == null)
                    return false;
                int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", android.os.Process.myUid(), context
                        .getPackageName());
                return Settings.canDrawOverlays(context) || mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED;
            } else {
                return Settings.canDrawOverlays(context);
            }
        }
    }

    /**
     * 悬浮窗开启权限
     *
     * @param context
     * @param requestCode
     */
    public void requestFloatPermission(Activity context, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivityForResult(intent, requestCode);
    }

}
