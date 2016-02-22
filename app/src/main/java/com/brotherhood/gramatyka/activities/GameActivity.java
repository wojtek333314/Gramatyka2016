package com.brotherhood.gramatyka.activities;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.brotherhood.gramatyka.R;
import com.brotherhood.gramatyka.activities.enums.GameMode;
import com.brotherhood.gramatyka.models.TaskModel;
import com.brotherhood.gramatyka.models.enums.ReplyType;
import com.brotherhood.gramatyka.utils.BaseActivity;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Wojtek on 2016-02-21.
 */
public class GameActivity extends BaseActivity {
    private Button answerA,
            answerB,
            answerC,
            answerD;
    private Button nextTask;
    private TextView task;
    private TextView subTask;
    private TextView subTask2;
    private TextView counter;

    private static GameMode GAME_MODE;
    private TaskModel currentTask;
    private HashMap<TaskModel, Boolean> tasks;
    private int counterValue = 1;

    @Override
    protected void customOnCreate() {
        setContentView(R.layout.activity_game);

        answerA = (Button) findViewById(R.id.answerA);
        answerB = (Button) findViewById(R.id.answerB);
        answerC = (Button) findViewById(R.id.answerC);
        answerD = (Button) findViewById(R.id.answerD);

        nextTask = (Button) findViewById(R.id.nextTaskButton);
        task = (TextView) findViewById(R.id.taskText);
        subTask = (TextView) findViewById(R.id.subTaskText);
        subTask2 = (TextView) findViewById(R.id.subTask2Text);
        counter = (TextView) findViewById(R.id.counter);

        initClickListeners();
    }


    private void refreshUIbyCurrentTask() {
        answerA.setText(currentTask.getReply(ReplyType.A).getReply());
        answerB.setText(currentTask.getReply(ReplyType.B).getReply());
        answerC.setText(currentTask.getReply(ReplyType.C).getReply());
        answerD.setText(currentTask.getReply(ReplyType.D).getReply());

        answerA.setVisibility(answerA.getText().equals("null") ? View.INVISIBLE : View.VISIBLE);
        answerB.setVisibility(answerB.getText().equals("null") ? View.INVISIBLE : View.VISIBLE);
        answerC.setVisibility(answerC.getText().equals("null") ? View.INVISIBLE : View.VISIBLE);
        answerD.setVisibility(answerD.getText().equals("null") ? View.INVISIBLE : View.VISIBLE);

        task.setText(currentTask.getTask());
        subTask.setText(currentTask.getSubTask() == null ? "null" : currentTask.getSubTask());
        subTask2.setText(currentTask.getSubTask2() == null ? "null" : currentTask.getSubTask2());

        subTask.setVisibility(subTask.getText().equals("null") ? View.INVISIBLE : View.VISIBLE);
        subTask2.setVisibility(subTask2.getText().equals("null") ? View.INVISIBLE : View.VISIBLE);
        counter.setText(getString(R.string.counter) + " " + String.valueOf(counterValue));

    }

    private void nextTask() {
        Random random = new Random();
        if (allTasksWasShowed()) {
            onRoundEnd();
        } else {
            while (tasks.get(currentTask))
                currentTask = ((TaskModel) tasks.keySet().toArray()[random.nextInt(tasks.keySet().size())]);

            counterValue++;
        }
    }

    private boolean allTasksWasShowed() {
        for (TaskModel key : tasks.keySet())
            if (tasks.get(key).equals(false))
                return false;
        return true;
    }

    private void loadTasks() {
        //todo getFromDatabase depend on GAME_MODE
    }

    private void onRoundEnd() {
        //todo show summary
    }

    private void onAnswerChoose(Button button){
        if(currentTask.getReply(ReplyType.A).isCorrect())
        {
            //animate and choose next task
        }else
        {
            //animate and choose next task
        }
    }


    private void initClickListeners(){
        View.OnClickListener answersButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.equals(answerA))
                   onAnswerChoose(answerA);
                if(v.equals(answerB))
                    onAnswerChoose(answerB);
                if(v.equals(answerC))
                    onAnswerChoose(answerC);
                if(v.equals(answerD))
                    onAnswerChoose(answerD);
            }
        };

        answerA.setOnClickListener(answersButtonListener);
        answerB.setOnClickListener(answersButtonListener);
        answerC.setOnClickListener(answersButtonListener);
        answerD.setOnClickListener(answersButtonListener);

        nextTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextTask();
                refreshUIbyCurrentTask();
            }
        });
    }

    public Button getAnswerA() {
        return answerA;
    }

    public Button getAnswerB() {
        return answerB;
    }

    public Button getAnswerC() {
        return answerC;
    }

    public Button getAnswerD() {
        return answerD;
    }

    public Button getNextTask() {
        return nextTask;
    }

    public static void setGameMode(GameMode gameMode) {
        GAME_MODE = gameMode;
    }
}
