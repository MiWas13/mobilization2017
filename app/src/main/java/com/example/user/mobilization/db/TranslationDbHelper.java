package com.example.user.mobilization.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 20.04.17.
 */

public class TranslationDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TranslationContract.TranslationEntry.TABLE_NAME + " (" +
                    TranslationContract.TranslationEntry._ID + " INTEGER PRIMARY KEY," +
                    TranslationContract.TranslationEntry.COLUMN_NAME_TRANSLATED + TEXT_TYPE +
                    COMMA_SEP + TranslationContract.TranslationEntry.COLUMN_NAME_TRANSLATION +
                    TEXT_TYPE + COMMA_SEP + TranslationContract.TranslationEntry.COLUMN_NAME_STATE +
                    TEXT_TYPE + COMMA_SEP + TranslationContract.TranslationEntry.COLUMN_NAME_LANGUAGE +
                    TEXT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TranslationContract.TranslationEntry.TABLE_NAME;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Translation.db";

    public TranslationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
