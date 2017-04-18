package com.example.user.mobilization.ui.base;

import android.os.Build;
import android.util.Log;

import com.example.user.mobilization.model.BookmarkModel;
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
    private ArrayList<BookmarkModel> bookmarksData = new ArrayList<>();

    void onViewCreated() {
        BookmarksView view = getView();
        view.initView();
    }

    private ArrayList<BookmarkModel> setBookmarksData() {
        bookmarksData = data;
        for (int i = 0; i < bookmarksData.size(); i++) {
            if (!bookmarksData.get(i).isState()) {
                bookmarksData.remove(i);
            }
        }
        return bookmarksData;
    }

    void setAdapter(String tabId) {
        setData();
        BookmarksView view = getView();
        if (tabId.equals(BOOKMARKS_TAB_ID)) {
            view.setAdapter(setBookmarksData());
        } else {
            view.setAdapter(data);
        }
    }

    private void setData() {
        data.add(0, new BookmarkModel(true, "Привет", "Hi", "EN->RU"));
        data.add(1, new BookmarkModel(false, "Как дела?", "How are you?", "EN->RU"));
        data.add(2, new BookmarkModel(true, "Я тут", "I'm here", "EN->RU"));
        data.add(3, new BookmarkModel(false, "Мама", "Mother", "EN->RU"));
    }
}
