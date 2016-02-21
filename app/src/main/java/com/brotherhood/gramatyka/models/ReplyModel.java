package com.brotherhood.gramatyka.models;

/**
 * Created by Wojtek on 2016-02-21.
 */
public class ReplyModel {
    private String reply;
    private boolean isCorrect;

    public ReplyModel(String reply) {
        this.reply = reply;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
