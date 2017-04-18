package com.example.user.mobilization.ui.base;

import com.example.user.mobilization.ui.bookmarks.BookmarksView;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

/**
 * Created by User on 12.04.17.
 */

public class BaseBookmarksPresenter extends MvpBasePresenter<BookmarksView> {
    public void onViewCreated() {
        BookmarksView view = getView();
        view.initView();
    }

    void onHistoryTabClicked() {

    }

    void onBookmarksTabClicked() {

    }
}
