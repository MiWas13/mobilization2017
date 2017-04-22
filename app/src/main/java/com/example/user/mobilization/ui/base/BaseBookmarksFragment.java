package com.example.user.mobilization.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.mobilization.R;
import com.example.user.mobilization.model.BookmarkModel;
import com.example.user.mobilization.ui.bookmarks.BookmarksAdapter;
import com.example.user.mobilization.ui.bookmarks.BookmarksView;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.lapism.searchview.SearchAdapter;
import com.lapism.searchview.SearchItem;
import com.lapism.searchview.SearchView;

import java.util.ArrayList;

import static com.example.user.mobilization.ui.Extras.BUNDLE;
import static com.example.user.mobilization.ui.Extras.HISTORY_TAB_ID;
import static com.example.user.mobilization.ui.Extras.NULL_STRING;

/**
 * Created by User on 18.04.17.
 */

public class BaseBookmarksFragment extends MvpFragment<BookmarksView, BaseBookmarksPresenter> implements BookmarksView {

    private View view;
    private BookmarksAdapter bookmarksAdapter = new BookmarksAdapter();
    private String tabId = HISTORY_TAB_ID;
    private SearchView searchView;

    private void getArgs() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            tabId = bundle.getString(BUNDLE, NULL_STRING);
        }
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        getArgs();
        presenter.onViewCreated();
    }

    @Override
    public void initView() {
        searchView = (SearchView) view.findViewById(R.id.search_view);
        presenter.setAdapter(getActivity(), tabId);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(bookmarksAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                bookmarksAdapter.searchFilter(newText);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });
    }

    @Override
    public void setAdapter(ArrayList<BookmarkModel> data) {
        bookmarksAdapter.setData(data);
    }

    @Override
    public void changeSearch(int searchHint) {
        searchView.setHint(searchHint);
    }
}