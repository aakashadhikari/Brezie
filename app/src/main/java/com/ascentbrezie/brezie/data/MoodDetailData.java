package com.ascentbrezie.brezie.data;

import java.util.List;

/**
 * Created by ADMIN on 09-11-2015.
 */
public class MoodDetailData {

    String imageId,imageUrl,likesCounter,commentsCounter,usedAsCounter;
    List<CommentsData> commentsData;

    public MoodDetailData(String imageId, String imageUrl, String likesCounter, String commentsCounter, String usedAsCounter, List<CommentsData> commentsData) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.likesCounter = likesCounter;
        this.commentsCounter = commentsCounter;
        this.usedAsCounter = usedAsCounter;
        this.commentsData = commentsData;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLikesCounter() {
        return likesCounter;
    }

    public void setLikesCounter(String likesCounter) {
        this.likesCounter = likesCounter;
    }

    public String getCommentsCounter() {
        return commentsCounter;
    }

    public void setCommentsCounter(String commentsCounter) {
        this.commentsCounter = commentsCounter;
    }

    public String getUsedAsCounter() {
        return usedAsCounter;
    }

    public void setUsedAsCounter(String usedAsCounter) {
        this.usedAsCounter = usedAsCounter;
    }

    public List<CommentsData> getCommentsData() {
        return commentsData;
    }

    public void setCommentsData(List<CommentsData> commentsData) {
        this.commentsData = commentsData;
    }
}
