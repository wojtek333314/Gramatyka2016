package com.brotherhood.gramatyka.models;

/**
 * Created by Wojtek on 2016-02-21.
 */
public class CategoryModel {
     private String name;
    private float rate;

    public CategoryModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
