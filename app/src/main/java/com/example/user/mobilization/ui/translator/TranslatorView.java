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
    void fullScreenMode(String word);
    void changeBookmarkState(Boolean state);
    void share();
    void createRequest();
    void getResponse(String word, String lang);
    void setTranslation(String translation);
}
