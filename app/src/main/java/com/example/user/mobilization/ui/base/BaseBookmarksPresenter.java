package com.example.user.mobilization.ui.base;

import android.content.Context;
import android.database.Cursor;

import com.example.user.mobilization.R;
import com.example.user.mobilization.db.TranslationContract;
import com.example.user.mobilization.model.BookmarkModel;
import com.example.user.mobilization.ui.bookmarks.BookmarksInteractor;
import com.example.user.mobilization.ui.bookmarks.BookmarksView;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;

import static com.example.user.mobilization.utils.Constants.BOOKMARKS_TAB_ID;


/**
 * Created by User on 12.04.17.
 */

public class BaseBookmarksPresenter extends MvpBasePresenter<BookmarksView> {
    private ArrayList<BookmarkModel> data = new ArrayList<>();
    private BookmarksInteractor bookmarksInteractor = new BookmarksInteractor();
    private ArrayList<BookmarkModel> bookmarksData = new ArrayList<>();
    private int numberOfLastElement;

    void onViewCreated() {
        BookmarksView view = getView();
        view.initView();
    }

    void setAdapter(Context context, String tabId) {
        setData(context);
        BookmarksView view = getView();
        if (tabId.equals(BOOKMARKS_TAB_ID)) {
            view.setAdapter(bookmarksData);
            view.changeSearch(R.string.search_in_bookmarks);
        } else {
            view.setAdapter(data);
            view.changeSearch(R.string.search_in_history);
        }
    }

    private void setData(Context context) {
        Cursor cursor = bookmarksInteractor.readFromDb(context);
        cursor.moveToLast();
        int i = 0;
        while (!cursor.isBeforeFirst()) {
            String translated = cursor.getString(cursor.getColumnIndexOrThrow(TranslationContract.TranslationEntry.COLUMN_NAME_TRANSLATED));
            String translation = cursor.getString(cursor.getColumnIndexOrThrow(TranslationContract.TranslationEntry.COLUMN_NAME_TRANSLATION));
            String language = cursor.getString(cursor.getColumnIndexOrThrow(TranslationContract.TranslationEntry.COLUMN_NAME_LANGUAGE));
            Boolean state = Boolean.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(TranslationContract.TranslationEntry.COLUMN_NAME_STATE)));
            data.add(i, new BookmarkModel(state, translated, translation, language));
            if (state) {
                bookmarksData.add(new BookmarkModel(true, translated, translation, language));
            }
            cursor.moveToPrevious();
            i++;
        }
        numberOfLastElement = i;
    }

    void onBookmarkClick(int position) {
        bookmarksInteractor.updateDb(numberOfLastElement - position, String.valueOf(!(data.get(position).isState())));
    }

    void deleteHistory() {
        BookmarksView view = getView();
        bookmarksInteractor.deleteFromDb();
        view.deleteHistory();
    }
}
