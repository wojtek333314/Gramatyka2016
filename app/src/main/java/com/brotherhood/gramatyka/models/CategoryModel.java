package com.brotherhood.gramatyka.models;

import java.util.ArrayList;

/**
 * Created by Wojtek on 2016-02-21.
 */
public class CategoryModel {
    private ArrayList<TaskModel> taskModels;
    private String name;
    private float rate;

    public CategoryModel(String name) {
        this.name = name;
        taskModels = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<TaskModel> getTaskModels() {
        return taskModels;
    }

    public void addTaskModel(TaskModel taskModel){
        taskModels.add(taskModel);
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
