package com.example.user.mobilization.ui.main;


import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.inputmethod.InputMethodManager;

import com.example.user.mobilization.R;
import com.example.user.mobilization.ui.bookmarks.TabsFragment;
import com.example.user.mobilization.ui.translator.TranslatorFragment;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends MvpActivity<MainView, MainPresenter> implements MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.initView();
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void initView() {
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottom_navigation);
        bottomBar.setOnTabSelectListener(tabId -> {
            switch (tabId) {
                case R.id.translator_button:
                    presenter.setFragment(new TranslatorFragment());
                    break;
                case R.id.bookmarks_button:
                    presenter.setFragment(new TabsFragment());
                    break;
                case R.id.settings_button:
//                      presenter.setFragment();
                    break;
            }
        });
    }

    @Override
    public void setSelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction
                .replace(R.id.container, fragment)
                .commit();
    }
}
