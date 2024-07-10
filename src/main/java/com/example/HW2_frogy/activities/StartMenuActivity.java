package com.example.HW2_frogy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.HW2_frogy.R;
import com.example.HW2_frogy.mapThings.MapManager;

public class StartMenuActivity extends AppCompatActivity {

    private Button startGameButton;
    private Button top10Button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startmenu);

        findViews();
        initViews();
        MapManager.getInstance().checkPreferences(this);
    }

    private void findViews() {
        startGameButton = findViewById(R.id.startMenu_BTN_startGame);
        top10Button = findViewById(R.id.startMenu_BTN_top10);
    }

    private void initViews() {
        startGameButton.setOnClickListener(view -> startActivity(new Intent(StartMenuActivity.this, ChooseGameStyleActivity.class)));
        top10Button.setOnClickListener(view -> navigateToRecordActivity());
    }

    private void navigateToRecordActivity() {
        Intent intent = new Intent(StartMenuActivity.this, ScoreActivity.class);
        startActivity(intent);
        finish();
    }
}
