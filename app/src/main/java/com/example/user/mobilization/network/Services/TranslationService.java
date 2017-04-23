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

import com.example.user.mobilization.network.RestApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.user.mobilization.utils.Constants.API_YANDEX_TRANSLATOR_BASE_URL;
import static com.example.user.mobilization.utils.Constants.BUNDLE;

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