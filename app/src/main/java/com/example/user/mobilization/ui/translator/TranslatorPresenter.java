package com.example.user.mobilization.ui.translator;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

/**
 * Created by User on 12.04.17.
 */

class TranslatorPresenter extends MvpBasePresenter<TranslatorView> {

    void onViewCreated() {
        TranslatorView view = getView();
        view.initView();
    }

    void onDeleteButtonClick() {
        TranslatorView view = getView();
        view.deleteText();
    }

    void onVocalizerButtonClick() {

    }

    void onRecognizerButtonClick() {

    }


}
