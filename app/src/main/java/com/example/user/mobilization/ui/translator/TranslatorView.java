package com.example.user.mobilization.ui.translator;

import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by User on 12.04.17.
 */

interface TranslatorView extends MvpView {
    void initView();
    void deleteText();
    void startRecognizerWork();
    void startVocalizerWork();
}
