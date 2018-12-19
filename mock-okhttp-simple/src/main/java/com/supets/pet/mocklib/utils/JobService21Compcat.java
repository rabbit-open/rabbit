package com.supets.pet.mocklib.utils;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;

public class JobService21Compcat {

    public static final int JOB_ID = 100;

    //Perio  task
    public static void scheduleJob(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ComponentName serviceComponent = new ComponentName(context, PAJobService.class);
            JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceComponent);

            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setPeriodic(15 * 60 * 1000);
            } else {
                builder.setPeriodic( 60 * 1000);
            }
            builder.setMinimumLatency(5000);
            builder.setPersisted(true);
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            int ret = jobScheduler.schedule(builder.build());
            if (ret == JobScheduler.RESULT_SUCCESS) {
                Log.d("JobService21Compcat", "Job scheduled successfully");
            } else {
                Log.d("JobService21Compcat", "Job scheduling failed");
            }

        }
    }

}
