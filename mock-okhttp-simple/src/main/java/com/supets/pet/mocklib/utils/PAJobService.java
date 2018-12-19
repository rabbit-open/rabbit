package com.supets.pet.mocklib.utils;

import android.app.ActivityManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import static android.content.ContentValues.TAG;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PAJobService extends JobService {

    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob");
        if (!isMyServiceRunning(FBService.class)) {
            SystermAlertWindowCompact.startService(getApplicationContext());
        }
        return false;
    }

    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob");
        return false;
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}