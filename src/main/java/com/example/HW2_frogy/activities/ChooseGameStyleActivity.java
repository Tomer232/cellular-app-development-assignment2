package com.example.HW2_frogy.activities;

import static com.example.HW2_frogy.settings.settings.KEY_MODE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.HW2_frogy.R;
import com.google.android.material.button.MaterialButton;

public class ChooseGameStyleActivity extends AppCompatActivity {

    private Button sensorButton;
    private Button buttonButton;
    private MaterialButton goBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_menu);

        findViews();
        initViews();
    }

    private void findViews() {
        sensorButton = findViewById(R.id.chooseMenu_BTN_SensorMode);
        buttonButton = findViewById(R.id.chooseMenu_BTN_ButtonMode);
        goBack = findViewById(R.id.buttonMenu_BTN_goBack);
    }

    private void initViews() {
        goBack.setOnClickListener(view -> onBackPressed());

        sensorButton.setOnClickListener(view -> navigateToActivity(1));
        buttonButton.setOnClickListener(view -> navigateToActivity(0));
    }

    private void navigateToActivity(int mode) {
        Intent intent = new Intent(ChooseGameStyleActivity.this, MainActivity.class);
        if (mode == 0) {
            intent.setClass(this, SpeedOptionActivity.class);
        }
        intent.putExtra(KEY_MODE, mode);
        startActivity(intent);
    }
}
