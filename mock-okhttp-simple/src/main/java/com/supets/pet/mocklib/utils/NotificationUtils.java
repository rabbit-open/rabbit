package com.supets.pet.mocklib.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.supets.pet.mocklib.R;

public class NotificationUtils extends ContextWrapper {

    private NotificationManager manager;
    public String channel_id = "channel_default_id";
    public String channel_name = "default_name";
    public String channel_desc = "notify desc";

    public NotificationUtils(Context context) {
        super(context);
    }

    public NotificationUtils(Context context, String id, String name, String desc) {
        super(context);
        this.channel_id = id;
        this.channel_name = name;
        this.channel_desc = desc;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setBypassDnd(true);
        channel.setShowBadge(true);
        channel.setDescription(channel_desc);
        channel.setVibrationPattern(new long[]{100, 100, 200});
        channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), channel_id);
    }

    public void sendNotification(String title, String content, int pushUniqueid) {

        NotificationCompat.Builder notification = getChannelNotification()
                .setContentTitle(title)
                .setContentText(content)
                .setOngoing(false)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.icon_tab))
                .setSmallIcon(R.drawable.icon_tab)
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            notification.setChannelId(channel_id);
        }
        getManager().notify(pushUniqueid, notification.build());

    }

    public void sendNotification(String title, String content, PendingIntent pendingIntent, int pushUniqueid) {
        NotificationCompat.Builder notification = getChannelNotification()
                .setContentTitle(title).setOngoing(false)
                .setContentText(content).setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.icon_tab))
                .setSmallIcon(R.drawable.icon_tab)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            notification.setChannelId(channel_id);
        }

        getManager().notify(pushUniqueid, notification.build());

    }

    public Notification createNotification(String title, String content) {

        NotificationCompat.Builder notification = getChannelNotification();
        notification.setContentTitle(title)
                .setContentText(content).setOngoing(false)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.icon_tab))
                .setSmallIcon(R.drawable.icon_tab)
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            notification.setChannelId(channel_id);
        }
        return notification.build();
    }

    public Notification createNotification(String title, String content, Bitmap largeIcon, int smallIcon, String ticker) {
        NotificationCompat.Builder notification = getChannelNotification();
        notification.setContentTitle(title)
                .setContentText(content)
                .setLargeIcon(largeIcon).setOngoing(false)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(smallIcon)
                .setAutoCancel(true).setTicker(ticker);
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            notification.setChannelId(channel_id);
        }
        return notification.build();
    }

    public static void openNotifyChannelSet(Activity activity, String channelId) {
        Intent channelIntent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        channelIntent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.getPackageName());
        channelIntent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
        //渠道id必须是我们之前注册的
        activity.startActivity(channelIntent);
    }

    public static final String SETTINGS_ACTION = "android.settings.APPLICATION_DETAILS_SETTINGS";

    public static void goToSet(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
            Intent intent = new Intent().setAction(SETTINGS_ACTION).setData(Uri.fromParts("package",
                    context.getApplicationContext().getPackageName(), null));
            context.startActivity(intent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent().setAction(SETTINGS_ACTION).setData(Uri.fromParts("package",
                    context.getApplicationContext().getPackageName(), null));
            context.startActivity(intent);
        }
    }
}
