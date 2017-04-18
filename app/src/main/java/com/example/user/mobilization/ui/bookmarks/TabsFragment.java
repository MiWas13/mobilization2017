package com.example.user.mobilization.ui.bookmarks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.mobilization.R;
import com.example.user.mobilization.ui.base.BaseBookmarksFragment;
import com.example.user.mobilization.ui.base.BaseBookmarksPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import static com.example.user.mobilization.ui.Extras.BOOKMARKS_TAB_ID;
import static com.example.user.mobilization.ui.Extras.BUNDLE;
import static com.example.user.mobilization.ui.Extras.HISTORY_TAB_ID;

/**
 * Created by User on 12.04.17.
 */

public class TabsFragment extends MvpFragment<BookmarksView, BaseBookmarksPresenter> {

    private View view;

    @Override
    public BaseBookmarksPresenter createPresenter() {
        return new BaseBookmarksPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        initView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_bookmarks_fragment, null);
        return view;
    }

    public void initView() {
        Bundle historyBundle = new Bundle();
        historyBundle.putString(BUNDLE, HISTORY_TAB_ID);
        Bundle bookmarksBundle = new Bundle();
        bookmarksBundle.putString(BUNDLE, BOOKMARKS_TAB_ID);
        FragmentTabHost mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec(HISTORY_TAB_ID).setIndicator("История"),
                BaseBookmarksFragment.class, historyBundle);
        mTabHost.addTab(mTabHost.newTabSpec(BOOKMARKS_TAB_ID).setIndicator("Избранное"),
                BaseBookmarksFragment.class, bookmarksBundle);

//        mTabHost.setOnTabChangedListener(tabId -> presenter.onTabSelected(tabId));
    }


}
