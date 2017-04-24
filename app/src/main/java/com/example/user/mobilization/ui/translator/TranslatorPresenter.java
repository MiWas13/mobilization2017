package com.example.user.mobilization.ui.translator;

import android.content.Context;
import android.util.Log;

import com.example.user.mobilization.model.Language;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.example.user.mobilization.ui.Extras.EXTRA_LANGUAGE_API;
import static com.example.user.mobilization.ui.Extras.EXTRA_TRANSLATION_API;
import static com.example.user.mobilization.ui.Extras.NULL_STRING;

/**
 * Created by User on 12.04.17.
 */

class TranslatorPresenter extends MvpBasePresenter<TranslatorView> {

    private TranslationInteractor translationInteractor = new TranslationInteractor();
    private String lastWord;
    private String newWord;
    private String correctLanguage;
    private Map<String, String> languages;
    private String firstLangCode = "en";
    private String secondLangCode = "ru";

    void onViewCreated() {
        TranslatorView view = getView();
        view.createRequest(EXTRA_LANGUAGE_API);
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

    void onTextChanged(CharSequence s, String lang) {
        TranslatorView view = getView();
        newWord = s.toString();
        correctLanguage = lang;
        view.createRequest(EXTRA_TRANSLATION_API);
    }

    void getLanguagesResponse() {
        TranslatorView view = getView();
        view.getLanguagesResponse();
    }

    void getTranslationResponse() {
        TranslatorView view = getView();
        view.getTranslationResponse(newWord, correctLanguage);
    }

    void setTranslation(String translation) {
        TranslatorView view = getView();
        view.setTranslation(translation);
    }

    void writeToDb(Context context, String translated, String text, String lang) {
        translationInteractor.writeToDb(context, translated, text, lang);
    }

    void setLanguages(Language languages) {
        TranslatorView view = getView();
        this.languages = languages.getLanguages();
        Collection<String> languagesArray = languages.getLanguages().values();
        List<String> list = new ArrayList<>(languagesArray);
        Collections.sort(list);
        view.setSpinnersAdapter(list);
    }

    void updateFirstSpinnerPosition(String language) {
        firstLangCode = findLanguageCode(language);
        Log.e("first", firstLangCode);
    }

    void updateSecondSpinnerPosition(String language) {
        secondLangCode = findLanguageCode(language);
        Log.e("second", secondLangCode);
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
