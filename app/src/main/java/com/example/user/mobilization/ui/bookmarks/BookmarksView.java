package com.example.user.mobilization.ui.bookmarks;

import com.example.user.mobilization.model.BookmarkModel;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.ArrayList;

/**
 * Created by User on 12.04.17.
 */

public interface BookmarksView extends MvpView {
    void initView();
    void setAdapter(ArrayList<BookmarkModel> data);
    void changeSearch(int searchHint);
    void changeBookmarkState();
}
