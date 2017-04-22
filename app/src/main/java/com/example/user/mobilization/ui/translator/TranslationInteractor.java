package com.example.user.mobilization.ui.translator;

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

class TranslationInteractor {

    void writeToDb(Context context, String translated, String translation, String language) {
        TranslationDbHelper mDbHelper = new TranslationDbHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TranslationContract.TranslationEntry.COLUMN_NAME_TRANSLATED, translated);
        values.put(TranslationContract.TranslationEntry.COLUMN_NAME_TRANSLATION, translation);
        values.put(TranslationContract.TranslationEntry.COLUMN_NAME_STATE, false);
        values.put(TranslationContract.TranslationEntry.COLUMN_NAME_LANGUAGE, language);
        long rowid;
        rowid = db.insert(TranslationContract.TranslationEntry.TABLE_NAME, null, values);
        Log.e("row", String.valueOf(rowid));
    }
}
