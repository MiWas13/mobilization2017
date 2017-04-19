package com.example.user.mobilization.ui.translator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.mobilization.R;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import ru.yandex.speechkit.SpeechKit;

import static com.example.user.mobilization.ui.Extras.API_SPEECH_KIT_YANDEX_KEY;
import static com.example.user.mobilization.ui.Extras.BUNDLE;

/**
 * Created by User on 12.04.17.
 */

public class TranslatorFragment extends MvpFragment<TranslatorView, TranslatorPresenter> implements TranslatorView {

    private View view;
    private EditText editText;
    private Button bookmarkBtn;

    @Override
    public TranslatorPresenter createPresenter() {
        return new TranslatorPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        SpeechKit.getInstance().configure(getActivity(), API_SPEECH_KIT_YANDEX_KEY);
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

        deleteTextBtn.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                presenter.onDeleteButtonClick();
                return true;
            }
            return false;
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
    }

}
