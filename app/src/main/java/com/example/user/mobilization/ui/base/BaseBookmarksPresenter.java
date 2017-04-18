package com.example.user.mobilization.ui.base;

import com.example.user.mobilization.model.BookmarkModel;
import com.example.user.mobilization.ui.bookmarks.BookmarksView;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;

import static com.example.user.mobilization.ui.Extras.BOOKMARKS_TAB_ID;
import static com.example.user.mobilization.ui.Extras.HISTORY_TAB_ID;


/**
 * Created by User on 12.04.17.
 */

public class BaseBookmarksPresenter extends MvpBasePresenter<BookmarksView> {
    private ArrayList<BookmarkModel> data = new ArrayList<>();

    public void onViewCreated() {
        BookmarksView view = getView();
        view.initView();
    }

    public void onTabSelected(String tabId) {
        if (tabId.equals(HISTORY_TAB_ID)) {

        } else if (tabId.equals(BOOKMARKS_TAB_ID)) {

        }
    }

    private void setBookmarksData() {

    }

    private void setData() {

    }
}
