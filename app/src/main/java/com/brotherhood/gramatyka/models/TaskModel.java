package com.brotherhood.gramatyka.models;

import java.util.ArrayList;

/**
 * Created by Wojtek on 2016-02-21.
 */
public class TaskModel {
    private String task;
    private String subTask;//optional
    private ArrayList<ReplyModel> replies;

    public TaskModel(String task) {
        this.task = task;
    }

    public void setSubTask(String subTask){
        this.subTask = subTask;
        this.replies = new ArrayList<>();
    }

    public void addReplyModel(ReplyModel replyModel){
        replies.add(replyModel);
    }

    public ArrayList<ReplyModel> getReplies() {
        return replies;
    }

    public String getTask() {
        return task;
    }

    public String getSubTask() {
        return subTask;
    }
}
