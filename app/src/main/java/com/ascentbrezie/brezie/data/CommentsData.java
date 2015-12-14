package com.ascentbrezie.brezie.data;

/**
 * Created by ADMIN on 09-11-2015.
 */
public class CommentsData {

    String comment,nickName;

    public CommentsData(String comment, String nickName) {
        this.comment = comment;
        this.nickName = nickName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
