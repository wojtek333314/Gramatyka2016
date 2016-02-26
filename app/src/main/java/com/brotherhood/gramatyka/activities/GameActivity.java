package com.brotherhood.gramatyka.activities;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.brotherhood.gramatyka.R;
import com.brotherhood.gramatyka.activities.enums.GameMode;
import com.brotherhood.gramatyka.models.TaskModel;
import com.brotherhood.gramatyka.models.enums.ReplyType;
import com.brotherhood.gramatyka.utils.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

/**
 * Created by Wojtek on 2016-02-21.
 */
public class GameActivity extends BaseActivity {
    private final int QUIZ_COUNT_OF_TASKS = 30;

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
    private LinkedHashMap<TaskModel, Boolean> tasks;
    private int counterValue = 0;
    private int goodAnswers = 0;
    private GameAnimationHelper gameAnimationHelper;

    @Override
    protected void customOnCreate() {
        setContentView(R.layout.activity_game);
        gameAnimationHelper = new GameAnimationHelper(this);

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
        loadTasks();
        if (GAME_MODE == GameMode.CATEGORY_MODE && getIntent().getExtras().getBoolean("session")) {
            counterValue = Integer.parseInt(getSharedPrefsHelper()
                    .getString(getIntent().getExtras().getString("category") + "_status") == null ? "0" : getSharedPrefsHelper()
                    .getString(getIntent().getExtras().getString("category") + "_status"));
            goodAnswers = Integer.parseInt(getSharedPrefsHelper()
                    .getString(getIntent().getExtras().getString("category") + "_goodAnswers") == null ? "0" : getSharedPrefsHelper()
                    .getString(getIntent().getExtras().getString("category") + "_goodAnswers"));
            if (counterValue >= tasks.size()) {
                counterValue = 0;
                goodAnswers = 0;
            }

        }
        if (counterValue > 0)
            counterValue--;
        nextTask();
        refreshUIbyCurrentTask();
    }


    private void refreshUIbyCurrentTask() {
        HashMap<ReplyType, Boolean> permute = new HashMap<>();
        permute.put(ReplyType.A, false);
        permute.put(ReplyType.B, false);
        permute.put(ReplyType.C, false);
        permute.put(ReplyType.D, false);

        ArrayList<String> arrayList = new ArrayList<>();

        while (!permute.get(ReplyType.A) || !permute.get(ReplyType.B) || !permute.get(ReplyType.C) || !permute.get(ReplyType.D)) {
            ReplyType replyType = ReplyType.values()[new Random().nextInt(ReplyType.values().length)];
            if (!permute.get(replyType)) {
                permute.put(replyType, true);
                arrayList.add(currentTask.getAnswer(replyType));
            }
        }


        answerA.setText(arrayList.get(0));
        answerB.setText(arrayList.get(1));
        answerC.setText(arrayList.get(2));
        answerD.setText(arrayList.get(3));

        answerA.setVisibility(answerA.getText().equals("null") ? View.GONE : View.VISIBLE);
        answerB.setVisibility(answerB.getText().equals("null") ? View.GONE : View.VISIBLE);
        answerC.setVisibility(answerC.getText().equals("null") ? View.GONE : View.VISIBLE);
        answerD.setVisibility(answerD.getText().equals("null") ? View.GONE : View.VISIBLE);

        task.setText(currentTask.getTask());
        subTask.setText(currentTask.getSubTask() == null ? "null" : currentTask.getSubTask());
        subTask2.setText(currentTask.getSubTask2() == null ? "null" : currentTask.getSubTask2());

        subTask.setVisibility(subTask.getText().equals("null") ? View.INVISIBLE : View.VISIBLE);
        subTask2.setVisibility(subTask2.getText().equals("null") ? View.INVISIBLE : View.VISIBLE);
        counter.setText(getString(R.string.counter) + " " + String.valueOf(counterValue) + "/" + tasks.size());

    }

    private void nextTask() {
        if (counterValue >= tasks.size()) {
            onRoundEnd();
            return;
        }
        currentTask = ((TaskModel) tasks.keySet().toArray()[counterValue]);
        counterValue++;
    }


    private void loadTasks() {
        tasks = new LinkedHashMap<>();
        int quizCount = 0;

        switch (GAME_MODE) {
            case QUIZ:
                for (TaskModel taskModel : getDatabaseHelper().getAllTaskModels()) {
                    if (quizCount >= QUIZ_COUNT_OF_TASKS)
                        break;
                    tasks.put(taskModel, false);
                    quizCount++;
                }
                break;
            case CATEGORY_MODE:
                for (TaskModel taskModel : getDatabaseHelper().getAllTaskModelsByCategory(getIntent().getExtras().getString("category")))
                    tasks.put(taskModel, false);
                break;
        }
    }

    private void onRoundEnd() {
        Intent intent = new Intent(this, SummaryActivity.class);
        intent.putExtra("allAnswers", tasks.size());
        intent.putExtra("goodAnswers", goodAnswers);
        if (getIntent().getExtras().getString("category") != null)
            intent.putExtra("category", getIntent().getExtras().getString("category"));
        startActivity(intent);
    }


    private void initClickListeners() {
        View.OnClickListener answersButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextTask.getVisibility() == View.VISIBLE)
                    return;

                if (v.equals(answerA))
                    onAnswerChoose(answerA);
                if (v.equals(answerB))
                    onAnswerChoose(answerB);
                if (v.equals(answerC))
                    onAnswerChoose(answerC);
                if (v.equals(answerD))
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
                if (gameAnimationHelper.isNextTaskAnimating() || !gameAnimationHelper.isAnyAnswerIsMarked())
                    return;

                gameAnimationHelper.resetAllButtonColors();
                gameAnimationHelper.onNextTaskClick();
                nextTask();
                refreshUIbyCurrentTask();
            }
        });
    }

    private void onAnswerChoose(Button button) {
        if (getGoodAnswerButton().equals(button)) {
            gameAnimationHelper.onButtonAnim(button, true);
            goodAnswers++;
        } else {
            gameAnimationHelper.onButtonAnim(button, false);
            gameAnimationHelper.markAllButtons(getGoodAnswerButton(), button);
        }

    }

    private Button getGoodAnswerButton() {
        if (answerA.getText().toString().equals(currentTask.getCorrectAnswer()))
            return answerA;
        if (answerB.getText().toString().equals(currentTask.getCorrectAnswer()))
            return answerB;
        if (answerC.getText().toString().equals(currentTask.getCorrectAnswer()))
            return answerC;
        if (answerD.getText().toString().equals(currentTask.getCorrectAnswer()))
            return answerD;

        return null;
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


    @Override
    protected void onPause() {
        if (GAME_MODE == GameMode.CATEGORY_MODE) {
            if (counterValue < tasks.size() && counterValue > 1) {
                getSharedPrefsHelper().putString(getIntent().getExtras().getString("category") + "_status", Integer.toString(counterValue));
                getSharedPrefsHelper().putString(getIntent().getExtras().getString("category") + "_goodAnswers", Integer.toString(goodAnswers));
            }
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (GAME_MODE == GameMode.CATEGORY_MODE) {
            if (counterValue < tasks.size() && counterValue > 1) {
                getSharedPrefsHelper().putString(getIntent().getExtras().getString("category") + "_status", Integer.toString(counterValue));
                getSharedPrefsHelper().putString(getIntent().getExtras().getString("category") + "_goodAnswers", Integer.toString(goodAnswers));
            }
        }
        super.onDestroy();
    }
}
