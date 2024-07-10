package com.example.HW2_frogy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.HW2_frogy.DataManager;
import com.example.HW2_frogy.R;
import com.example.HW2_frogy.callbacks.CallBack_List;
import com.example.HW2_frogy.callbacks.CallBack_Map;
import com.example.HW2_frogy.fragments.Fragment_List;
import com.example.HW2_frogy.fragments.Fragment_Map;
import com.example.HW2_frogy.extras.RecordsList;
import com.example.HW2_frogy.extras.Scores;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ScoreActivity extends AppCompatActivity {

    private Fragment_List fragmentList;
    private Fragment_Map fragmentMap;

    private Button exitButton;
    private Button menuButton;
    private ImageView backgroundImageView;

    private final CallBack_List callBackUserInfo = DataManager::getTopResults;

    private final CallBack_Map callBackMap = map -> {
        map.clear();
        RecordsList topResults = DataManager.getTopResults();
        if (topResults != null) {
            for (int i = 0; i < topResults.size(); i++) {
                Scores result = topResults.get(i);
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(result.getX(), result.getY()))
                        .title(String.valueOf(i)));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        fragmentList = new Fragment_List();
        fragmentMap = new Fragment_Map();

        findViews();
        initViews();

        fragmentList.setCallBackUserInfo(callBackUserInfo);
        fragmentMap.setCallBackMap(callBackMap);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.panel_LAY_list, fragmentList)
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.panel_LAY_map, fragmentMap)
                .commit();
    }

    private void findViews() {
        menuButton = findViewById(R.id.record_BTN_menu);
        exitButton = findViewById(R.id.record_BTN_exit);
        backgroundImageView = findViewById(R.id.record_IMG_background);
    }

    private void initViews() {
        menuButton.setOnClickListener(view -> navigateToStartMenu());
        exitButton.setOnClickListener(view -> finish());
    }

    private void navigateToStartMenu() {
        Intent intent = new Intent(ScoreActivity.this, StartMenuActivity.class);
        startActivity(intent);
        finish();
    }
}
