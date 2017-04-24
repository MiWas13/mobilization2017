package com.example.user.mobilization.network;

import com.example.user.mobilization.model.Language;
import com.example.user.mobilization.model.Translation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by User on 20.04.17.
 */

public interface RestApi {
    @GET("/api/v1.5/tr.json/translate")
    Call<Translation> getTranslation(@Query("key") String key, @Query("text") String text, @Query("lang") String lang);

    @GET("/api/v1.5/tr.json/getLangs")
    Call<Language> getLanguages(@Query("key") String key, @Query("ui") String langCode);

}
