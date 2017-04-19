package com.example.user.mobilization.ui.translator;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

/**
 * Created by User on 12.04.17.
 */

class TranslatorPresenter extends MvpBasePresenter<TranslatorView> {

    private String lastWord;

    void onViewCreated() {
        TranslatorView view = getView();
        view.initView();
    }

    void onDeleteButtonClick() {
        TranslatorView view = getView();
        view.deleteText();
    }

    void onVocalizerButtonClick() {
        TranslatorView view = getView();
        view.startVocalizerWork();
    }

    void onRecognizerButtonClick() {
        TranslatorView view = getView();
        view.startRecognizerWork();
    }

    void onBookmarkButtonClick(String translatedWord) {
        TranslatorView view = getView();
        view.changeBookmarkState(translatedWord.equals(lastWord));
        lastWord = translatedWord;
    }

    void onFullScreenButtonClick(String word) {
        TranslatorView view = getView();
        view.fullScreenMode(word);
    }

    void onShareButtonClick() {
        TranslatorView view = getView();
        view.share();
    }
}
