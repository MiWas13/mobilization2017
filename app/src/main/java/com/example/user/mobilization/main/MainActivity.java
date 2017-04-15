package com.example.user.mobilization.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.example.user.mobilization.R;
import com.example.user.mobilization.translator.TranslatorFragment;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

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
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.translator_button:
                                presenter.setFragment(new TranslatorFragment());
                                break;
                            case R.id.history_button:
//                                presenter.setFragment();
                                break;
                            case R.id.settings_button:
//                                presenter.setFragment();
                                break;
                        }
                        return true;
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
