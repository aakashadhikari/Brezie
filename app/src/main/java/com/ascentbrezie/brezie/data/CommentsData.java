package com.ascentbrezie.brezie.data;

/**
 * Created by ADMIN on 09-11-2015.
 */
public class CommentsData {

    String comment,handle;

    public CommentsData(String comment, String handle) {
        this.comment = comment;
        this.handle = handle;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }
}
