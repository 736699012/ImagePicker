package com.example.imagepackge.domain;

public class ImageItem {

    private String path;
    private String name;
    private long addTime;

    public ImageItem(String path, String name, long addTime) {
        this.path = path;
        this.name = name;
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "ImageItem{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", addTime=" + addTime +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }


}
