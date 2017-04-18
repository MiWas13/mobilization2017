package com.example.user.mobilization.model;

/**
 * Created by User on 12.04.17.
 */

public class BookmarkModel {
    private boolean state;
    private String translated;
    private String translation;
    private String languages;

    public BookmarkModel(boolean state, String translated, String translation, String languages) {
        this.state = state;
        this.translated = translated;
        this.translation = translation;
        this.languages = languages;
    }

    public String getTranslation() {
        return translation;
    }

    public String getLanguages() {
        return languages;
    }

    public String getTranslated() {
        return translated;
    }

    public boolean isState() {
        return state;
    }
}
