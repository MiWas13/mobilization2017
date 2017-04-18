package com.example.user.mobilization.ui.bookmarks;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

/**
 * Created by User on 12.04.17.
 */

class BaseBookmarksPresenter extends MvpBasePresenter<BookmarksView> {
    void onViewCreated() {
        BookmarksView view = getView();
        view.initView();
    }

    void onHistoryTabClicked() {

    }

    void onBookmarksTabClicked() {

    }
}
