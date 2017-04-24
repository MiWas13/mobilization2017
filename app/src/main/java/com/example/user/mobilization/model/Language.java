package com.example.user.mobilization.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;


/**
 * Created by User on 23.04.17.
 */

public class Language {
    @SerializedName("langs")
    @Expose
    private Map<String, String> languages;

    public Map<String, String> getLanguages() {
        return languages;
    }
}
