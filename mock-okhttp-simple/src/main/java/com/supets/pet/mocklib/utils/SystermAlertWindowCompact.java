package com.supets.pet.mocklib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.supets.pet.mocklib.R;

public class SystermAlertWindowCompact {

    private static final int OVERLAY_PERMISSION_REQ_CODE = 0x1002;

    public static void checkPermission(Activity context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(context)) {
                Intent intent = new Intent(context, FBService.class);
                ContextCompat.startForegroundService(context, intent);
            } else {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    context.startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Intent intent = new Intent(context, FBService.class);
            ContextCompat.startForegroundService(context, intent);
        }

    }

    public static void checkPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(context)) {
                Intent intent = new Intent(context, FBService.class);
                ContextCompat.startForegroundService(context, intent);
            } else {
                Toast.makeText(context, context.getString(R.string.permission_no), Toast.LENGTH_SHORT).show();
            }
        } else {
            Intent intent = new Intent(context, FBService.class);
            ContextCompat.startForegroundService(context, intent);
        }

    }

    protected static void onActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(context)) {
                    Toast.makeText(context, context.getString(R.string.permission_fail), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, context.getString(R.string.permission_success), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, FBService.class);
                    ContextCompat.startForegroundService(context, intent);
                }
            }
        }
    }

}
