package com.example.user.mobilization.ui.translator;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.mobilization.R;
import com.example.user.mobilization.db.TranslationContract;
import com.example.user.mobilization.model.Translation;
import com.example.user.mobilization.network.Services.TranslationService;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import ru.yandex.speechkit.SpeechKit;

import static com.example.user.mobilization.network.Services.TranslationService.getRestApi;
import static com.example.user.mobilization.ui.Extras.API_YANDEX_SPEECH_KIT_KEY;
import static com.example.user.mobilization.ui.Extras.API_YANDEX_TRANSLATOR_KEY;
import static com.example.user.mobilization.ui.Extras.BUNDLE;
import static com.example.user.mobilization.ui.Extras.NULL_STRING;

/**
 * Created by User on 12.04.17.
 */

public class TranslatorFragment extends MvpFragment<TranslatorView, TranslatorPresenter> implements TranslatorView {

    private View view;
    private EditText editText;
    private Button bookmarkBtn;
    private TextView translationView;
    private TranslationInteractor translationInteractor = new TranslationInteractor();
    private Timer timer = new Timer();
    private final long DELAY = 1000;
    private String newLang;
    private String newText;

    @Override
    public TranslatorPresenter createPresenter() {
        return new TranslatorPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        SpeechKit.getInstance().configure(getActivity(), API_YANDEX_SPEECH_KIT_KEY);
        presenter.onViewCreated();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.translator_fragment, null);
        return view;
    }


    @Override
    public void initView() {
        Button deleteTextBtn = (Button) view.findViewById(R.id.delete_button);
        Button recognizerBtn = (Button) view.findViewById(R.id.recognizer_button);
        Button vocalizerBtn = (Button) view.findViewById(R.id.vocalizer_button);
        Button fullScreenBtn = (Button) view.findViewById(R.id.fullscreen_button);
        Button shareButton = (Button) view.findViewById(R.id.share_button);
        bookmarkBtn = (Button) view.findViewById(R.id.bookmark_button);
        editText = (EditText) view.findViewById(R.id.edit_view);
        translationView = (TextView) view.findViewById(R.id.translation_view);

        deleteTextBtn.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                presenter.onDeleteButtonClick();
                return true;
            }
            return false;
        });

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.onTextChanged(s, "ru");
                timer = new Timer();
                if (s.length() > 0) {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            writeToDb(newText, newLang);
                        }
                    }, DELAY);
                }
            }
        });

        recognizerBtn.setOnClickListener(v -> presenter.onRecognizerButtonClick());

        vocalizerBtn.setOnClickListener(v -> presenter.onVocalizerButtonClick());

        fullScreenBtn.setOnClickListener(v -> presenter.onFullScreenButtonClick(editText.getText().toString()));

        bookmarkBtn.setOnClickListener(v -> presenter.onBookmarkButtonClick(editText.getText().toString()));

        shareButton.setOnClickListener(v -> presenter.onShareButtonClick());

    }

    @Override
    public void deleteText() {
        editText.setText(null);
    }

    @Override
    public void startRecognizerWork() {
        Toast.makeText(getActivity(), "Функция скоро будет доступна", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startVocalizerWork() {
        Toast.makeText(getActivity(), "Функция скоро будет доступна", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fullScreenMode(String word) {
        Intent intent = new Intent(getActivity(), FullScreenActivity.class);
        intent.putExtra(BUNDLE, word);
        startActivity(intent);
    }

    @Override
    public void changeBookmarkState(Boolean state) {
        if (state) {
            bookmarkBtn.setBackgroundResource(R.drawable.ic_bookmark_grey);
        } else {
            bookmarkBtn.setBackgroundResource(R.drawable.ic_bookmark_yellow);
        }
    }

    @Override
    public void share() {
        Toast.makeText(getActivity(), "Функция скоро будет доступна", Toast.LENGTH_SHORT).show();
        readFromDb();
    }

    @Override
    public void createRequest() {
        MyTranslatorHandler myTranslatorHandler = new MyTranslatorHandler();
        Intent intent = new Intent(getActivity(), TranslationService.class);
        intent.putExtra(BUNDLE, new Messenger(myTranslatorHandler));
        getActivity().startService(intent);
    }

    @Override
    public void getResponse(String word, String lang) {
        getRestApi().getTranslation(API_YANDEX_TRANSLATOR_KEY, word, lang).enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, retrofit2.Response<Translation> response) {
                if (response.body() == null) {
                    presenter.setTranslation(NULL_STRING);
                } else {
                    newText = response.body().getText().get(0);
                    presenter.setTranslation(newText);
                    newLang = response.body().getLang();
                }
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private class MyTranslatorHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj.equals(true)) {
                presenter.getResponse();
            }
        }
    }

    @Override
    public void setTranslation(String translation) {
        translationView.setText(translation);
    }

    void readFromDb() {
        Cursor cursor = TranslationInteractor.readFromDb();
        cursor.moveToFirst();
        while (!cursor.isLast()) {
            String translate = cursor.getString(cursor.getColumnIndexOrThrow(TranslationContract.TranslationEntry.COLUMN_NAME_TRANSLATION));
            cursor.moveToNext();
            Log.e("DB", String.valueOf(translate));
        }
    }

    void writeToDb(String text, String language) {
        getActivity().runOnUiThread(() -> translationInteractor.writeToDb(getActivity(), editText.getText().toString(),
                text, language));
    }
}