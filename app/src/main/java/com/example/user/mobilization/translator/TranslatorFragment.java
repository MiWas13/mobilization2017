package com.example.user.mobilization.translator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.mobilization.R;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

/**
 * Created by User on 12.04.17.
 */

public class TranslatorFragment extends MvpFragment<TranslatorView, TranslatorPresenter> implements TranslatorView {

    private View view;

    @Override
    public TranslatorPresenter createPresenter() {
        return new TranslatorPresenter();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.translator_fragment, null);
        return view;
    }


}
