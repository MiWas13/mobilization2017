package com.example.user.mobilization.ui.main;


import android.support.v4.app.Fragment;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

/**
 * Created by User on 12.04.17.
 */

class MainPresenter extends MvpBasePresenter<MainView> {
    private MainView view;

    void initView() {
        view = getView();
        view.initView();
    }

    void setFragment(Fragment fragment) {
        view.setSelectedFragment(fragment);
    }
}
