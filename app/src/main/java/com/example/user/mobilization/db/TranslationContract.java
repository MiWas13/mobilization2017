package com.example.user.mobilization.db;

import android.provider.BaseColumns;

/**
 * Created by User on 20.04.17.
 */

public final class TranslationContract {

    public static abstract class TranslationEntry implements BaseColumns {
        public static final String TABLE_NAME = "translation";
        public static final String COLUMN_NAME_TRANSLATED = "translated";
        public static final String COLUMN_NAME_TRANSLATION = "translation";
        public static final String COLUMN_NAME_STATE = "state";
        public static final String COLUMN_NAME_LANGUAGE = "lang";

    }

}
