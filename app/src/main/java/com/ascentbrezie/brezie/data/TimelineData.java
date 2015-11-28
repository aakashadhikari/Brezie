package com.ascentbrezie.brezie.data;

/**
 * Created by ADMIN on 24-10-2015.
 */
public class TimelineData {

    String status,image;

    public TimelineData(String status, String image) {
        this.status = status;
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
