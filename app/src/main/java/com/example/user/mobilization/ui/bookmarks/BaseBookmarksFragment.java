package com.example.user.mobilization.ui.bookmarks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.mobilization.R;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

/**
 * Created by User on 18.04.17.
 */

public class BaseBookmarksFragment extends MvpFragment<BookmarksView, BaseBookmarksPresenter> implements BookmarksView {

    private View view;

    @Override
    public void initView() {

    }

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
}
