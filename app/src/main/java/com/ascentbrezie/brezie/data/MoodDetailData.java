package com.ascentbrezie.brezie.data;

import java.util.List;

/**
 * Created by ADMIN on 09-11-2015.
 */
public class MoodDetailData {

    String quoteId,imageId,imageUrl,likesCounter,commentsCounter,usedAsCounter;
    List<CommentsData> commentsData;
    boolean isLiked;

    public MoodDetailData(String quoteId,String commentsCounter,String likesCounter,String shareCounter,String usedAsCounter,List<CommentsData> commentsData){

        this.quoteId = quoteId;
        this.commentsCounter = commentsCounter;
        this.likesCounter = likesCounter;
        this.usedAsCounter = usedAsCounter;
        this.commentsData = commentsData;

    }

    public MoodDetailData(String likesCounter, String commentsCounter, String usedAsCounter, List<CommentsData> commentsData) {
        this.likesCounter = likesCounter;
        this.commentsCounter = commentsCounter;
        this.usedAsCounter = usedAsCounter;
        this.commentsData = commentsData;
    }


    public MoodDetailData(String imageId, String imageUrl, String likesCounter, String commentsCounter, String usedAsCounter, List<CommentsData> commentsData, boolean isLiked) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.likesCounter = likesCounter;
        this.commentsCounter = commentsCounter;
        this.usedAsCounter = usedAsCounter;
        this.commentsData = commentsData;
        this.isLiked = isLiked;
    }


    public boolean isLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
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
