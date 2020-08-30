package com.supets.pet.device.filebrowser;

public class FileData {
    public String name;//显示名称
    public String path;//本身路径
    public boolean isDir;//本身是否是目录

    public FileData(String name, String path, boolean isDir) {
        this.name = name;
        this.path = path;
        this.isDir = isDir;
    }
}
