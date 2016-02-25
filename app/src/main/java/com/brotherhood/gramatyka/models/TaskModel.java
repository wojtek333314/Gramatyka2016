package com.brotherhood.gramatyka.models;

import com.brotherhood.gramatyka.models.enums.ReplyType;

import java.util.HashMap;

/**
 * Created by Wojtek on 2016-02-21.
 */
public class TaskModel {
    private String task;
    private String subTask;
    private String subTask2;
    private HashMap<ReplyType,ReplyModel> replies;
    private CategoryModel categoryModel;

    public TaskModel(String task) {
        this.task = task;
    }

    public void setSubTask(String subTask){
        this.subTask = subTask;
        this.replies = new HashMap<>();
    }

    public String getSubTask2() {
        return subTask2;
    }

    public void setSubTask2(String subTask2) {
        this.subTask2 = subTask2;
    }

    public void addReplyModel(ReplyType replyType,ReplyModel replyModel){
        replies.put(replyType, replyModel);
    }

    public ReplyModel getReply(ReplyType replyType){
        return replies.get(replyType);
    }

    public String getTask() {
        return task;
    }

    public String getSubTask() {
        return subTask;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }
}
