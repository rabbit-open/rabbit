package com.supets.pet.mock.ui.home;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.supets.pet.uctoast.ListenClipboardService;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;


public class MockUiActivity extends TabLayoutBottomActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        update();
        ListenClipboardService.start(this);

        permissionCheck();
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
}
