package com.example.user.mobilization.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.mobilization.R;
import com.example.user.mobilization.ui.bookmarks.BookmarksAdapter;
import com.example.user.mobilization.ui.bookmarks.BookmarksView;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

/**
 * Created by User on 18.04.17.
 */

public class BaseBookmarksFragment extends MvpFragment<BookmarksView, BaseBookmarksPresenter> implements BookmarksView {

    private View view;

    @Override
    public BaseBookmarksPresenter createPresenter() {
        return new BaseBookmarksPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bookmarks_fragment, null);
        return view;
    }

    @Override
    public void initView() {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        BookmarksAdapter bookmarksAdapter = new BookmarksAdapter();
        recyclerView.setAdapter(bookmarksAdapter);
    }
}
