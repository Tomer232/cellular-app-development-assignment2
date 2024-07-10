package com.example.HW2_frogy.activities;

import static com.example.HW2_frogy.settings.settings.KEY_DELAY;
import static com.example.HW2_frogy.settings.settings.KEY_MODE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.HW2_frogy.R;
import com.google.android.material.button.MaterialButton;

public class SpeedOptionActivity extends AppCompatActivity {
    private Button fastButton;
    private Button slowButton;
    private MaterialButton backButton;

    private static final int FAST = 500;
    private static final int SLOW = 1000;

    private ImageView background;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_mode);

        findViews();
        initViews();
    }

    private void findViews() {
        fastButton = findViewById(R.id.buttonMenu_BTN_FastMode);
        slowButton = findViewById(R.id.buttonMenu_BTN_SlowMode);
        background = findViewById(R.id.buttonMenu_IMG_background);
        backButton = findViewById(R.id.buttonMenu_BTN_goBack);
    }

    private void initViews() {
        backButton.setOnClickListener(view -> onBackPressed());

        fastButton.setOnClickListener(view -> launchMainActivityWithDelay(FAST));
        slowButton.setOnClickListener(view -> launchMainActivityWithDelay(SLOW));
    }

    private void launchMainActivityWithDelay(int delay) {
        Intent intent = new Intent(SpeedOptionActivity.this, MainActivity.class);
        intent.putExtra(KEY_MODE, getIntentMode());
        intent.putExtra(KEY_DELAY, delay);
        startActivity(intent);
    }

    private int getIntentMode() {
        return getIntent().getIntExtra(KEY_MODE, 0);
    }
}
