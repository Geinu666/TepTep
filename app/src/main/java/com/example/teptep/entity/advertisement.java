package com.example.teptep.entity;

public class advertisement {
    private String updateTime;
    private String version;
    private String imageUrl;
    private String httpUrl;

    public void setHttlUrl(String httlUrl) {
        this.httpUrl = httlUrl;
    }

    public String getHttlUrl() {
        return httpUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
