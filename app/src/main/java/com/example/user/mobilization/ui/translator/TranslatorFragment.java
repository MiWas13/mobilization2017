package com.example.user.mobilization.ui.translator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.mobilization.R;
import com.example.user.mobilization.model.Language;
import com.example.user.mobilization.model.Translation;
import com.example.user.mobilization.network.Services.TranslationService;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.yandex.speechkit.SpeechKit;

import static com.example.user.mobilization.network.Services.TranslationService.getRestApi;
import static com.example.user.mobilization.utils.Constants.API_YANDEX_SPEECH_KIT_KEY;
import static com.example.user.mobilization.utils.Constants.API_YANDEX_TRANSLATOR_KEY;
import static com.example.user.mobilization.utils.Constants.BUNDLE;
import static com.example.user.mobilization.utils.Constants.ENGLISH_LANGUAGE;
import static com.example.user.mobilization.utils.Constants.EXTRA_LANGUAGE_API;
import static com.example.user.mobilization.utils.Constants.EXTRA_TRANSLATION_API;
import static com.example.user.mobilization.utils.Constants.NULL_STRING;
import static com.example.user.mobilization.utils.Constants.RUSSIAN_LANGUAGE;
import static com.example.user.mobilization.utils.Constants.RUSSIAN_LANGUAGE_CODE;
import static com.example.user.mobilization.utils.Constants.TRANSLATOR_TIMER_DELAY;

/**
 * Created by User on 12.04.17.
 */

public class TranslatorFragment extends MvpFragment<TranslatorView, TranslatorPresenter> implements TranslatorView {

    private View view;
    private EditText editText;
    private TextView yandexView;
    private Button bookmarkBtn;
    private TextView translationView;
    private Timer timer = new Timer();
    private String newLang, newText;
    private Spinner firstLanguageSpinner, secondLanguageSpinner;
    private LinearLayout translationLayout;
    private ProgressBar progressBar;

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
        progressBar = (ProgressBar) view.findViewById(R.id.progress_view);
        translationLayout = (LinearLayout) view.findViewById(R.id.translation_layout);
        Button deleteTextBtn = (Button) view.findViewById(R.id.delete_button);
        Button recognizerBtn = (Button) view.findViewById(R.id.recognizer_button);
        Button vocalizerBtn = (Button) view.findViewById(R.id.vocalizer_button);
        Button fullScreenBtn = (Button) view.findViewById(R.id.fullscreen_button);
        Button shareButton = (Button) view.findViewById(R.id.share_button);
        Button changeLanguagesBtn = (Button) view.findViewById(R.id.change_languages_button);
        bookmarkBtn = (Button) view.findViewById(R.id.bookmark_button);
        editText = (EditText) view.findViewById(R.id.edit_view);
        translationView = (TextView) view.findViewById(R.id.translation_view);
        yandexView = (TextView) view.findViewById(R.id.yandex_work);
        firstLanguageSpinner = (Spinner) view.findViewById(R.id.first_language_spinner);
        secondLanguageSpinner = (Spinner) view.findViewById(R.id.second_language_spinner);
        yandexView.setVisibility(View.GONE);
        translationLayout.setVisibility(View.GONE);
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
                //Таймер сбрасывается во время набора текста
                if (timer != null)
                    timer.cancel();
                yandexView.setVisibility(View.GONE);
                translationLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //После завершения набора текста создается таймер, чтобы расчитать закончил ли юзер ввод
                //или еще нет. В textView с переводом мы выводим ответ в любом случае, а пишем его в БД только
                //если пользователь закончил ввод
                presenter.onTextChanged(s);
                timer = new Timer();
                if (s.length() > 0) {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            presenter.writeToDb(getActivity(), editText.getText().toString(), newText, newLang);
                        }
                    }, TRANSLATOR_TIMER_DELAY);
                } else {
                    yandexView.setVisibility(View.GONE);
                }
            }
        });

        changeLanguagesBtn.setOnClickListener(v -> presenter.onChangeLanguagesButtonClick());

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
    }

    @Override
    public void createRequest(String requestType) {
        //В зависимости от переданной строки(типа запроса) выполняем запрос либо на получение языков, либо на перевод
        Intent intent = new Intent(getActivity(), TranslationService.class);
        if (requestType.equals(EXTRA_LANGUAGE_API)) {
            intent.putExtra(BUNDLE, new Messenger(new MyLanguagesHandler()));
        } else if (requestType.equals(EXTRA_TRANSLATION_API)) {
            intent.putExtra(BUNDLE, new Messenger(new MyTranslationHandler()));
        }

        getActivity().startService(intent);
    }

    @Override
    public void getLanguagesResponse() {
        getRestApi().getLanguages(API_YANDEX_TRANSLATOR_KEY, RUSSIAN_LANGUAGE_CODE).enqueue(new Callback<Language>() {

            @Override
            public void onResponse(Call<Language> call, Response<Language> response) {
                //TODO: Write request response to DB
                presenter.setLanguages(response.body());
            }

            @Override
            public void onFailure(Call<Language> call, Throwable t) {
                showFailSnackBar();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void getTranslationResponse(String word, String lang) {
        getRestApi().getTranslation(API_YANDEX_TRANSLATOR_KEY, word, lang).enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, retrofit2.Response<Translation> response) {
                haveResponse();
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
                haveResponse();
                showFailSnackBar();
                t.printStackTrace();
            }
        });
    }

    //Handler'ы, которые отслеживают статус выполнения запроса
    private class MyTranslationHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj.equals(true)) {
                presenter.getTranslationResponse();
            }
        }
    }

    private class MyLanguagesHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj.equals(true)) {
                presenter.getLanguagesResponse();
            }
        }
    }

    @Override
    public void setTranslation(String translation) {
        translationView.setText(translation);
    }

    @Override
    public void setSpinnersAdapter(List<String> languages) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstLanguageSpinner.setAdapter(adapter);
        secondLanguageSpinner.setAdapter(adapter);
        firstLanguageSpinner.setSelection(adapter.getPosition(ENGLISH_LANGUAGE));
        secondLanguageSpinner.setSelection(adapter.getPosition(RUSSIAN_LANGUAGE));
        firstLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.updateFirstSpinnerPosition(adapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        secondLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.updateSecondSpinnerPosition(adapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void changeLanguages() {
        //Используем третью переменную для замены(не придумал ничего лучше :()
        String helpStringArg;
        int helpIntArg;
        helpIntArg = firstLanguageSpinner.getSelectedItemPosition();
        firstLanguageSpinner.setSelection(secondLanguageSpinner.getSelectedItemPosition());
        secondLanguageSpinner.setSelection(helpIntArg);
        helpStringArg = String.valueOf(editText.getText());
        editText.setText(translationView.getText());
        translationView.setText(helpStringArg);
    }

    void haveResponse() {
        //При получении ответа скрываем ProgressBar и показываем перевод
        progressBar.setVisibility(View.GONE);
        translationLayout.setVisibility(View.VISIBLE);
        yandexView.setVisibility(View.VISIBLE);
    }

    void showFailSnackBar() {
        Snackbar.make(view, R.string.problems, Snackbar.LENGTH_SHORT).show();
    }
}