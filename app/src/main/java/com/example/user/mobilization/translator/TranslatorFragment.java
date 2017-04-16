package com.example.user.mobilization.translator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.mobilization.R;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

/**
 * Created by User on 12.04.17.
 */

public class TranslatorFragment extends MvpFragment<TranslatorView, TranslatorPresenter> implements TranslatorView {

    private View view;
    private EditText editText;

    @Override
    public TranslatorPresenter createPresenter() {
        return new TranslatorPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
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
        editText = (EditText) view.findViewById(R.id.edit_view);

        deleteTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDeleteButtonClick();
            }
        });

        recognizerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRecognizerButtonClick();
            }
        });

        vocalizerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onVocalizerButtonClick();
            }
        });

    }

    @Override
    public void deleteText() {
        editText.setText(null);
    }

    @Override
    public void startRecognizerWork() {

    }

    @Override
    public void startVocalizerWork() {

    }
}
