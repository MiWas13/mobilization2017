package com.example.user.mobilization.ui.main;


import android.support.v4.app.Fragment;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by User on 12.04.17.
 */

interface MainView extends MvpView {
    void initView();
    void setSelectedFragment(Fragment fragment);
}
