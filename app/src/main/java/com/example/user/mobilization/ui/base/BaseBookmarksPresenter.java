package com.example.user.mobilization.ui.base;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;

import com.example.user.mobilization.db.TranslationContract;
import com.example.user.mobilization.model.BookmarkModel;
import com.example.user.mobilization.ui.bookmarks.BookmarksInteractor;
import com.example.user.mobilization.ui.bookmarks.BookmarksView;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.example.user.mobilization.ui.Extras.BOOKMARKS_TAB_ID;


/**
 * Created by User on 12.04.17.
 */

public class BaseBookmarksPresenter extends MvpBasePresenter<BookmarksView> {
    private ArrayList<BookmarkModel> data = new ArrayList<>();
    private BookmarksInteractor bookmarksInteractor = new BookmarksInteractor();

    void onViewCreated() {
        BookmarksView view = getView();
        view.initView();
    }

    private ArrayList<BookmarkModel> setBookmarksData() {
        ArrayList<BookmarkModel> bookmarksData = data;
        for (int i = 0; i < bookmarksData.size(); i++) {
            if (!bookmarksData.get(i).isState()) {
                bookmarksData.remove(i);
            }
        }
        return bookmarksData;
    }

    void setAdapter(Context context, String tabId) {
        setData(context);
        BookmarksView view = getView();
        if (tabId.equals(BOOKMARKS_TAB_ID)) {
            view.setAdapter(setBookmarksData());
        } else {
            view.setAdapter(data);
        }
    }

    private void setData(Context context) {
        Cursor cursor = bookmarksInteractor.readFromDb(context);
        cursor.moveToLast();
        while (!cursor.isBeforeFirst()) {
            String translated = cursor.getString(cursor.getColumnIndexOrThrow(TranslationContract.TranslationEntry.COLUMN_NAME_TRANSLATED));
            String translation = cursor.getString(cursor.getColumnIndexOrThrow(TranslationContract.TranslationEntry.COLUMN_NAME_TRANSLATION));
            String language = cursor.getString(cursor.getColumnIndexOrThrow(TranslationContract.TranslationEntry.COLUMN_NAME_LANGUAGE));
            Boolean state = Boolean.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(TranslationContract.TranslationEntry.COLUMN_NAME_STATE)));
            data.add(new BookmarkModel(state, translated, translation, language));
            cursor.moveToPrevious();
        }
    }
}
