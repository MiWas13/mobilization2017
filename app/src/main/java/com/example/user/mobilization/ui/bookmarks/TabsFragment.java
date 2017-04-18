package com.example.user.mobilization.ui.bookmarks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.example.user.mobilization.R;
import com.example.user.mobilization.ui.base.BaseBookmarksFragment;
import com.example.user.mobilization.ui.base.BaseBookmarksPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import static com.example.user.mobilization.ui.Extras.BOOKMARKS_TAB_ID;
import static com.example.user.mobilization.ui.Extras.HISTORY_TAB_ID;

/**
 * Created by User on 12.04.17.
 */

public class TabsFragment extends MvpFragment<BookmarksView, BaseBookmarksPresenter> implements BookmarksView {

    private View view;

    @Override
    public BaseBookmarksPresenter createPresenter() {
        return new BaseBookmarksPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        presenter.onViewCreated();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_bookmarks_fragment, null);
        return view;
    }

    @Override
    public void initView() {
        FragmentTabHost mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec(HISTORY_TAB_ID).setIndicator("История"),
                BaseBookmarksFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(BOOKMARKS_TAB_ID).setIndicator("Избранное"),
                BaseBookmarksFragment.class, null);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                presenter.onTabSelected(tabId);
            }
        });
    }
}
