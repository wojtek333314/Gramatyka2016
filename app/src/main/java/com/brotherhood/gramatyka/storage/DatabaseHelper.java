package com.brotherhood.gramatyka.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wojtek on 2016-02-21.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2; //wersja bazy danych wymagana od funkcji tworzacej baze
    //ponizej znajduja sie nazwy pól w bazie danych. Na ich podstawie jest tworzona baza danych i
    //parsowane sa dane do modelu CurrencyModel podczas odczytu  z bazy danych
    private static final String TABLE_NAME = "fiches";
    private static final String ID = "id";
    private static final String PL_VALUE = "pl";
    private static final String ENG_VALUE = "eng";
    private static final String CATEGORY_VALUE = "category";
    private static final String SOUND_PATH = "soundPath";
    private static final String STATUS = "status";


    private static final String TABLE_CREATE_EXPRESSION =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + ID + " INTEGER PRIMARY KEY, "
                    + PL_VALUE + " TEXT, "
                    + ENG_VALUE + " TEXT, "
                    + CATEGORY_VALUE + " TEXT, "
                    + STATUS + " INT, "
                    + SOUND_PATH + " TEXT )";


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