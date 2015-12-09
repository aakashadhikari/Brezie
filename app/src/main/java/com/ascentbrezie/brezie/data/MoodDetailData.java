package com.ascentbrezie.brezie.data;

import java.util.List;

/**
 * Created by ADMIN on 09-11-2015.
 */
public class MoodDetailData {

    String quoteId,likesCounter,commentsCounter,usedAsCounter,backgroundUrl,commentsCount;
    List<CommentsData> commentsData;
    boolean isLiked;

    public MoodDetailData(String quoteId,String commentsCounter,String likesCounter,String shareCounter,String usedAsCounter,List<CommentsData> commentsData,String commentsCount){

        this.quoteId = quoteId;
        this.commentsCounter = commentsCounter;
        this.likesCounter = likesCounter;
        this.usedAsCounter = usedAsCounter;
        this.commentsData = commentsData;
        this.commentsCount = commentsCount;

    }

    // This will be used when backgroundUrl is available
    public MoodDetailData(String quoteId,String commentsCounter,String likesCounter,String shareCounter,String usedAsCounter,String backgroundUrl,List<CommentsData> commentsData,String commentsCount){

        this.quoteId = quoteId;
        this.commentsCounter = commentsCounter;
        this.likesCounter = likesCounter;
        this.usedAsCounter = usedAsCounter;
        this.backgroundUrl = backgroundUrl;
        this.commentsData = commentsData;

    }

    public String getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(String commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
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
