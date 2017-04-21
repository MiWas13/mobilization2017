package com.example.user.mobilization.network.Services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.user.mobilization.model.Translation;
import com.example.user.mobilization.network.RestApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.user.mobilization.ui.Extras.API_YANDEX_TRANSLATOR_BASE_URL;
import static com.example.user.mobilization.ui.Extras.API_YANDEX_TRANSLATOR_KEY;
import static com.example.user.mobilization.ui.Extras.BUNDLE;

public class TranslationService extends Service {
    public TranslationService() {
    }

    private static RestApi restApi;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle arguments = intent.getExtras();
        final Messenger messenger = (Messenger) arguments.get(BUNDLE);
        final Notification notification = new Notification.Builder(this).build();
        startForeground(1, notification);

        new Thread(() -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_YANDEX_TRANSLATOR_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            restApi = retrofit.create(RestApi.class);
            Message message = Message.obtain();
            message.obj = true;
//            getRestApi().getTranslation(API_YANDEX_TRANSLATOR_KEY, "Hello", "ru").enqueue(new Callback<Translation>() {
//                @Override
//                public void onResponse(Call<Translation> call, retrofit2.Response<Translation> response) {
//                    message.obj = true;
//                }
//
//                @Override
//                public void onFailure(Call<Translation> call, Throwable t) {
//                    message.obj = false;
//                }
//
//            });
            try {
                assert messenger != null;
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            stopForeground(true);
            stopSelf();
        }).start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static RestApi getRestApi() {
        return restApi;
    }
}