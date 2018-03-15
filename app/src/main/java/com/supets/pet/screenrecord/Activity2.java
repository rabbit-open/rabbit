//package com.supets.pet.screenrecord;
//
//import android.app.ListActivity;
//import android.content.Context;
//import android.content.Intent;
//import android.media.projection.MediaProjectionManager;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//import com.supets.pet.MyApplication;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//public class Activity2 extends ListActivity {
//    private MediaProjectionManager mMpMngr;
//    private static final int REQUEST_MEDIA_PROJECTION = 10086;
//    boolean isCapture;
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        String[] array = {"截屏", "录屏",};
//        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>(Arrays.asList(array))));
//        //1、首先获取MediaProjectionManager
//        mMpMngr = (MediaProjectionManager) getApplicationContext().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
//    }
//
//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        switch (position) {
//            case 0:
//                isCapture = true;
//                stopService(new Intent(getApplicationContext(), RecordService.class));
//                startIntent();
//                break;
//            case 1:
//                isCapture = false;
//                stopService(new Intent(getApplicationContext(), CaptureService.class));
//                startIntent();
//                break;
//            case 2:
//                break;
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //3、通过onActivityResult()获取授权结果。
//        if (requestCode == REQUEST_MEDIA_PROJECTION && resultCode == RESULT_OK) {
//            MyApplication.mResultCode = resultCode;
//            MyApplication.mResultIntent = data;
//            MyApplication.mMpmngr = mMpMngr;
//            startIntent();
//        }
//    }
//
//    private void startIntent() {
//        if (MyApplication.mResultIntent != null && MyApplication.mResultCode != 0) {//已授权
//            if (isCapture)
//                startService(new Intent(getApplicationContext(), CaptureService.class));//开始截屏
//            else startService(new Intent(getApplicationContext(), RecordService.class));//开始录屏
//        }
//        //2、调用MediaProjectionManager.createScreenCaptureIntent()后，会弹出一个dialog询问用户是否授权应用捕捉屏幕
//        else
//            startActivityForResult(mMpMngr.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);//未授权
//    }
//}