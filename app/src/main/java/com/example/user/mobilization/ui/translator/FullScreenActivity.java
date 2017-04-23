package com.example.user.mobilization.ui.translator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.mobilization.R;

import static com.example.user.mobilization.utils.Constants.BUNDLE;

/**
 * Created by User on 18.04.17.
 */

public class FullScreenActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fullscreen);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        TextView textView = (TextView) findViewById(R.id.word);
        textView.setText(bundle.getString(BUNDLE));
        ImageView closeActivity = (ImageView) findViewById(R.id.close_activity);
        closeActivity.setOnClickListener(v -> finish());
    }
}
