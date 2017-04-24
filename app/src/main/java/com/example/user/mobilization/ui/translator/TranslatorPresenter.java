package com.example.user.mobilization.ui.translator;

import android.content.Context;

import com.example.user.mobilization.model.Language;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.example.user.mobilization.utils.Constants.EXTRA_LANGUAGE_API;
import static com.example.user.mobilization.utils.Constants.EXTRA_TRANSLATION_API;
import static com.example.user.mobilization.utils.Constants.LANGUAGE_DIVIDER;
import static com.example.user.mobilization.utils.Constants.NULL_STRING;

/**
 * Created by User on 12.04.17.
 */

class TranslatorPresenter extends MvpBasePresenter<TranslatorView> {

    private TranslatorView view;
    private TranslationInteractor translationInteractor = new TranslationInteractor();
    private String lastWord;
    private String newWord;
    private Map<String, String> languages;
    private String firstLangCode = "en";
    private String secondLangCode = "ru";

    void onViewCreated() {
        view = getView();
        view.createRequest(EXTRA_LANGUAGE_API);
        view.initView();
    }

    void onDeleteButtonClick() {
        view.deleteText();
    }

    void onVocalizerButtonClick() {
        view.startVocalizerWork();
    }

    void onRecognizerButtonClick() {
        view.startRecognizerWork();
    }

    void onBookmarkButtonClick(String translatedWord) {
        view.changeBookmarkState(translatedWord.equals(lastWord));
        lastWord = translatedWord;
    }

    void onFullScreenButtonClick(String word) {
        view.fullScreenMode(word);
    }

    void onShareButtonClick() {
        view.share();
    }

    void onTextChanged(CharSequence s) {
        newWord = s.toString();
        view.createRequest(EXTRA_TRANSLATION_API);
    }

    void getLanguagesResponse() {
        view.getLanguagesResponse();
    }

    void getTranslationResponse() {
        String correctLanguage = firstLangCode + LANGUAGE_DIVIDER + secondLangCode;
        view.getTranslationResponse(newWord, correctLanguage);
    }

    void setTranslation(String translation) {
        view.setTranslation(translation);
    }

    void writeToDb(Context context, String translated, String text, String lang) {
        translationInteractor.writeToDb(context, translated, text, lang);
    }

    void setLanguages(Language languages) {
        this.languages = languages.getLanguages();
        Collection<String> languagesArray = languages.getLanguages().values();
        List<String> list = new ArrayList<>(languagesArray);
        Collections.sort(list);
        view.setSpinnersAdapter(list);
    }

    void onChangeLanguagesButtonClick() {
        String helpArg;
        TranslatorView view = getView();
        helpArg = firstLangCode;
        firstLangCode = secondLangCode;
        secondLangCode = helpArg;
        view.changeLanguages();
    }

    void updateFirstSpinnerPosition(String language) {
        firstLangCode = findLanguageCode(language);
    }

    void updateSecondSpinnerPosition(String language) {
        secondLangCode = findLanguageCode(language);
    }

    String findLanguageCode(String language) {
        String code = NULL_STRING;
        for (String key : languages.keySet()) {
            Object obj = languages.get(key);
            if (key != null) {
                if (language.equals(obj)) {
                    code = key;
                }
            }
        }
        return code;
    }
}
