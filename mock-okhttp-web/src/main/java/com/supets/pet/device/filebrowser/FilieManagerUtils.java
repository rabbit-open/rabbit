package com.supets.pet.device.filebrowser;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

public class FilieManagerUtils {


    public static ArrayList<FileData> getFileList(String root) {
        ArrayList<FileData> data = new ArrayList<>();
        File rootFile = new File(root);//当前目录
        if (rootFile.isDirectory()) {
            if (rootFile.getAbsolutePath().equals(Environment.getExternalStorageDirectory().getAbsolutePath())) {
                data.add(new FileData("根目录:", Environment.getExternalStorageDirectory().getAbsolutePath(), true));
            } else {
                data.add(new FileData("当前所在目录：" + rootFile.getName() + "  上一级目录:" + rootFile.getParentFile().getName(), rootFile.getParentFile().getAbsolutePath(), true));
            }
            File[] files = rootFile.listFiles();
            if (files != null) {
                for (File temp : files) {
                    data.add(new FileData(temp.getName(), temp.getAbsolutePath(), temp.isDirectory()));
                }
            }
        }
        return data;
    }

    public static ArrayList<FileData> getFileListSdCard() {
        return getFileList(Environment.getExternalStorageDirectory().getAbsolutePath());
    }

}
