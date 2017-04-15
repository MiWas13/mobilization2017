package com.example.user.mobilization.ui.main;


import android.support.v4.app.Fragment;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

/**
 * Created by User on 12.04.17.
 */

class MainPresenter extends MvpBasePresenter<MainView> {
    void initView() {
        MainView view = getView();
        view.initView();
    }

    void setFragment(Fragment fragment) {
        MainView view = getView();
        view.setSelectedFragment(fragment);
    }
}
