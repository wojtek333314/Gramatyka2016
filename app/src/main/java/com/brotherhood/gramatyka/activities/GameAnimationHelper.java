package com.brotherhood.gramatyka.activities;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.brotherhood.gramatyka.R;

/**
 * Created by Wojtek on 2016-02-22.
 */
public class GameAnimationHelper {
    private GameActivity gameActivity;
    private Animation nextTaskShow;
    private Animation nextTaskHide;
    private Animation answerButtonAnimation;
    private boolean nextTaskAnimating = false;
    private boolean anyAnswerIsMarked = false;


    public GameAnimationHelper(final GameActivity gameActivity) {
        this.gameActivity = gameActivity;

        nextTaskHide = AnimationUtils.loadAnimation(gameActivity, R.anim.fade_out);
        nextTaskShow = AnimationUtils.loadAnimation(gameActivity, R.anim.fade_in);

        nextTaskHide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                nextTaskAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                gameActivity.getNextTask().setVisibility(View.INVISIBLE);
                nextTaskAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        nextTaskShow.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                gameActivity.getNextTask().setVisibility(View.VISIBLE);
                nextTaskAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                nextTaskAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public boolean isNextTaskAnimating() {
        return nextTaskAnimating;
    }

    public void onButtonAnim(Button button, boolean answerGood) {
        anyAnswerIsMarked = true;
        gameActivity.getNextTask().startAnimation(nextTaskShow);

        answerButtonAnimation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
        answerButtonAnimation.setDuration(350); // duration - half a second
        answerButtonAnimation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        answerButtonAnimation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        answerButtonAnimation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
        answerButtonAnimation.setRepeatCount(4);
        if (answerGood)
            button.setBackground(gameActivity.getResources().getDrawable(R.drawable.answer_button_good));
        else
            button.setBackground(gameActivity.getResources().getDrawable(R.drawable.answer_button_bad));

        button.startAnimation(answerButtonAnimation);
    }

    public void resetAllButtonColors() {
        gameActivity.getAnswerA().setBackground(gameActivity.getResources().getDrawable(R.drawable.answer_button));
        gameActivity.getAnswerB().setBackground(gameActivity.getResources().getDrawable(R.drawable.answer_button));
        gameActivity.getAnswerC().setBackground(gameActivity.getResources().getDrawable(R.drawable.answer_button));
        gameActivity.getAnswerD().setBackground(gameActivity.getResources().getDrawable(R.drawable.answer_button));
        anyAnswerIsMarked = false;
    }

    public void markAllButtons(Button goodButton,Button clicked) {
        goodButton.setBackground(gameActivity.getResources().getDrawable(R.drawable.answer_button_good));
        clicked.setBackground(gameActivity.getResources().getDrawable(R.drawable.answer_button_bad));
    }

    public void onNextTaskClick() {
        stopAnswerAnimation();
        gameActivity.getNextTask().startAnimation(nextTaskHide);
    }

    public boolean isAnyAnswerIsMarked() {
        return anyAnswerIsMarked;
    }

    private void stopAnswerAnimation() {
        gameActivity.getAnswerA().clearAnimation();
        gameActivity.getAnswerB().clearAnimation();
        gameActivity.getAnswerC().clearAnimation();
        gameActivity.getAnswerD().clearAnimation();
    }
}
