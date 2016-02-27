package com.brotherhood.gramatyka.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.brotherhood.gramatyka.models.CategoryModel;
import com.brotherhood.gramatyka.models.TaskModel;
import com.brotherhood.gramatyka.models.enums.ReplyType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Wojtek on 2016-02-21.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2; //wersja bazy danych wymagana od funkcji tworzacej baze
    //ponizej znajduja sie nazwy pól w bazie danych. Na ich podstawie jest tworzona baza danych i
    //parsowane sa dane do modelu CurrencyModel podczas odczytu  z bazy danych
    private static final String TABLE_NAME = "tasks";
    private static final String ID = "ID";
    private static final String CATEGORY = "CATEGORY";
    private static final String TASK = "TASK";
    private static final String SUBTASK = "SUBTASK";
    private static final String SUBTASK2 = "SUBTASK2";
    private static final String ANSWER_A = "ANSWER_A";
    private static final String ANSWER_B = "ANSWER_B";
    private static final String ANSWER_C = "ANSWER_C";
    private static final String ANSWER_D = "ANSWER_D";
    private static final String CORRECT_ANSWER = "CORRECT_ANSWER";


    private static final String TABLE_CREATE_EXPRESSION =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + ID + " INTEGER PRIMARY KEY, "
                    + CATEGORY + " TEXT, "
                    + TASK + " TEXT, "
                    + SUBTASK + " TEXT, "
                    + SUBTASK2 + " TEXT, "
                    + ANSWER_A + " TEXT, "
                    + ANSWER_B + " TEXT, "
                    + ANSWER_C + " TEXT, "
                    + ANSWER_D + " TEXT, "
                    + CORRECT_ANSWER + " TEXT ) ";


    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Metoda tworzaca baze danych
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_EXPRESSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<TaskModel> getAllTaskModels() {
        ArrayList<TaskModel> entityList = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("select * from " + TABLE_NAME + " ORDER BY "+ID, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String category = cursor.getString(cursor
                        .getColumnIndex(CATEGORY));
                String task = cursor.getString(cursor
                        .getColumnIndex(TASK));
                String subtask = cursor.getString(cursor
                        .getColumnIndex(SUBTASK));
                String subtask2 = cursor.getString(cursor
                        .getColumnIndex(SUBTASK2));
                String answerA = cursor.getString(cursor
                        .getColumnIndex(ANSWER_A));
                String answerB = cursor.getString(cursor
                        .getColumnIndex(ANSWER_B));
                String answerC = cursor.getString(cursor
                        .getColumnIndex(ANSWER_C));
                String answerD = cursor.getString(cursor
                        .getColumnIndex(ANSWER_D));
                String correctAnswer = cursor.getString(cursor
                        .getColumnIndex(CORRECT_ANSWER));


                TaskModel model = new TaskModel(task);
                model.setSubTask(subtask);
                model.setSubTask2(subtask2);
                model.addAnswer(answerA, ReplyType.A);
                model.addAnswer(answerB, ReplyType.B);
                model.addAnswer(answerC, ReplyType.C);
                model.addAnswer(answerD, ReplyType.D);

                if(correctAnswer.equals("a"))
                    model.setCorrectAnswer(answerA);
                if(correctAnswer.equals("b"))
                    model.setCorrectAnswer(answerB);
                if(correctAnswer.equals("c"))
                    model.setCorrectAnswer(answerC);
                if(correctAnswer.equals("d"))
                    model.setCorrectAnswer(answerD);


                model.setCategoryModel(new CategoryModel(category));
                entityList.add(model);
                cursor.moveToNext();
            }
            getReadableDatabase().close();
        }

        Collections.rotate(entityList,new Random().nextInt(300));
        return entityList;
    }

    public ArrayList<TaskModel> getAllTaskModelsByCategory(String categoryName) {
        ArrayList<TaskModel> entityList = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("select * from " + TABLE_NAME + " where category='" + categoryName + "' " +
                " ORDER BY "+ID, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String category = cursor.getString(cursor
                        .getColumnIndex(CATEGORY));
                String task = cursor.getString(cursor
                        .getColumnIndex(TASK));
                String subtask = cursor.getString(cursor
                        .getColumnIndex(SUBTASK));
                String subtask2 = cursor.getString(cursor
                        .getColumnIndex(SUBTASK2));
                String answerA = cursor.getString(cursor
                        .getColumnIndex(ANSWER_A));
                String answerB = cursor.getString(cursor
                        .getColumnIndex(ANSWER_B));
                String answerC = cursor.getString(cursor
                        .getColumnIndex(ANSWER_C));
                String answerD = cursor.getString(cursor
                        .getColumnIndex(ANSWER_D));
                String correctAnswer = cursor.getString(cursor
                        .getColumnIndex(CORRECT_ANSWER));

                TaskModel model = new TaskModel(task);
                model.setSubTask(subtask);
                model.setSubTask2(subtask2);
                model.addAnswer(answerA, ReplyType.A);
                model.addAnswer(answerB, ReplyType.B);
                model.addAnswer(answerC, ReplyType.C);
                model.addAnswer(answerD, ReplyType.D);
                if(correctAnswer.equals("a"))
                    model.setCorrectAnswer(answerA);
                if(correctAnswer.equals("b"))
                    model.setCorrectAnswer(answerB);
                if(correctAnswer.equals("c"))
                    model.setCorrectAnswer(answerC);
                if(correctAnswer.equals("d"))
                    model.setCorrectAnswer(answerD);
                model.setCategoryModel(new CategoryModel(category));
                if (category.equals(categoryName))
                    entityList.add(model);
                cursor.moveToNext();
            }
            getReadableDatabase().close();
        }

        return entityList;
    }

    public ArrayList<CategoryModel> getAllCategories() {
        ArrayList<CategoryModel> entityList = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("select * from " + TABLE_NAME + " ORDER BY "+ID, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String category = cursor.getString(cursor
                        .getColumnIndex(CATEGORY));

                boolean hasThatModelInArray = false;
                CategoryModel model = new CategoryModel(category);
                for (CategoryModel categoryModel : entityList)
                    if (categoryModel.getName().equals(category))
                        hasThatModelInArray = true;

                if (!hasThatModelInArray)
                    entityList.add(model);

                cursor.moveToNext();
            }
            getReadableDatabase().close();
        }

        return entityList;
    }


    public void saveSingleTaskModel(JSONObject json) throws JSONException {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, Integer.parseInt(json.getString("id")));
        values.put(CATEGORY, json.getString("category"));
        values.put(ANSWER_A, json.getString("answerA"));
        values.put(ANSWER_B, json.getString("answerB"));
        values.put(ANSWER_C, json.getString("answerC"));
        values.put(ANSWER_D, json.getString("answerD"));
        values.put(CORRECT_ANSWER, json.getString("correctAnswer"));
        values.put(SUBTASK, json.getString("subtask"));
        values.put(SUBTASK2, json.getString("subtask2"));
        values.put(TASK, json.getString("task"));

        db.insert(TABLE_NAME, null, values);//wrzuca do bazy danych,
    }


    public void clearDatabaseAndRecreate(){
        getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        getWritableDatabase().execSQL(TABLE_CREATE_EXPRESSION);
    }

    /**
     * Zapobiega wyciekowi z SQLLiteConnection
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}