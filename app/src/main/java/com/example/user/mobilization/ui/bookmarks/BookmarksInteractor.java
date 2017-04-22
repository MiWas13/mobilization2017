package com.example.user.mobilization.ui.bookmarks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.user.mobilization.db.TranslationContract;
import com.example.user.mobilization.db.TranslationDbHelper;


/**
 * Created by User on 21.04.17.
 */

public class BookmarksInteractor {

    private TranslationDbHelper mDbHelper;

    public Cursor readFromDb(Context context) {
        mDbHelper = new TranslationDbHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                TranslationContract.TranslationEntry.COLUMN_NAME_TRANSLATION,
                TranslationContract.TranslationEntry.COLUMN_NAME_TRANSLATED,
                TranslationContract.TranslationEntry.COLUMN_NAME_STATE,
                TranslationContract.TranslationEntry.COLUMN_NAME_LANGUAGE
        };

        String selection = TranslationContract.TranslationEntry._ID;

        String[] selectionArgs = {};
        String sortOrder = TranslationContract.TranslationEntry._ID;

        return db.query(
                TranslationContract.TranslationEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    public void updateDb(int position, String state) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TranslationContract.TranslationEntry.COLUMN_NAME_STATE, state);
        String selection = TranslationContract.TranslationEntry._ID + " =" + position;
        String[] selectionArgs = {};
        db.update(TranslationContract.TranslationEntry.TABLE_NAME, contentValues, selection, selectionArgs);
    }
}
