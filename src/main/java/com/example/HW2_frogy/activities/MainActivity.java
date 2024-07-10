package com.example.HW2_frogy.activities;

import static com.example.HW2_frogy.settings.settings.ADD_SCORE;
import static com.example.HW2_frogy.settings.settings.GAME_OVER;
import static com.example.HW2_frogy.settings.settings.KEY_DELAY;
import static com.example.HW2_frogy.settings.settings.KEY_LEFT;
import static com.example.HW2_frogy.settings.settings.KEY_MODE;
import static com.example.HW2_frogy.settings.settings.KEY_RIGHT;
import static com.example.HW2_frogy.settings.settings.LOST_LIFE;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.HW2_frogy.GameManager;
import com.example.HW2_frogy.R;
import com.example.HW2_frogy.extras.Type;
import com.example.HW2_frogy.callbacks.SensorDetector;
import com.example.HW2_frogy.extras.ImgHandle;
import com.example.HW2_frogy.mapThings.MapManager;
import com.example.HW2_frogy.mapThings.Signals;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final int ROWS = 10;
    private static final int COLS = 5;
    private int mode = 1;
    private AppCompatImageView[] main_IMG_hearts;
    private MaterialButton backButton;

    private MediaPlayer contactSound;
    private TextView main_LBL_score;
    private int delay = 1000;
    private GameManager gameManager;
    private SensorDetector sensorDetector;
    private AppCompatImageView[][] main_MATRIX_objects;
    private AppCompatImageView[] main_IMG_player;
    private ExtendedFloatingActionButton main_BTN_left;
    private ExtendedFloatingActionButton main_BTN_right;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        gameManager = new GameManager(main_IMG_hearts.length, ROWS, COLS);
        sensorDetector = new SensorDetector(this, callBack_move);
        initViews();
        getDataFromPrevIntent();
        MapManager.init(MainActivity.this);

        backButton.setOnClickListener(view -> finish());
    }

    private void getDataFromPrevIntent() {
        Intent prevIntent = getIntent();
        this.mode = prevIntent.getExtras().getInt(KEY_MODE);
        this.delay = prevIntent.getExtras().getInt(KEY_DELAY, 1000);
    }

    private final SensorDetector.CallBack_move callBack_move = new SensorDetector.CallBack_move() {
        @Override
        public void move(int direction) {
            if (direction == 1 && gameManager.move(KEY_LEFT)) {
                updateVisibility(direction);
            } else if (direction == -1 && gameManager.move(KEY_RIGHT)) {
                updateVisibility(direction);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
        MapManager.getInstance().start();

        if (mode == 1) {
            sensorDetector.start();
            main_BTN_left.setVisibility(View.INVISIBLE);
            main_BTN_right.setVisibility(View.INVISIBLE);
        } else {
            main_BTN_left.setVisibility(View.VISIBLE);
            main_BTN_right.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (contactSound != null) {
            contactSound.pause();
        }
        if (mode == 1) {
            sensorDetector.stop();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTimer();
        MapManager.getInstance().stop();
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> updateTimeUI());
            }
        }, delay, delay);
    }

    private void stopTimer() {
        timer.cancel();
    }

    private void updateTimeUI() {
        if (gameManager.isEndGame()) {
            gameOver();
        } else {
            updateUI();
        }
    }

    private void updateUI() {
        gameManager.updateTable();

        if (gameManager.checkIsWrong()) {
            renderHearts();
            toast(LOST_LIFE);
            playSound(R.raw.crash_sound);
            Signals.getInstance().vibrate();
        }
        if (gameManager.checkIsCoin()) {
            toast(ADD_SCORE);
            playSound(R.raw.coin_sound);
            Signals.getInstance().vibrate();
        }
        updateScore();
        renderMatrix(gameManager.getMatrix());
    }

    private void updateScore() {
        main_LBL_score.setText("Score: " + gameManager.getScore());
    }

    private void gameOver() {
        toast(GAME_OVER);
        navigateToRecordActivity();
        saveResults();
    }

    private void saveResults() {
        gameManager.saveResults();
    }

    private void navigateToRecordActivity() {
        Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
        startActivity(intent);
        finish();
    }

    private void toast(String text) {
        Signals.getInstance().showToast(text);
    }

    private void renderMatrix(ImgHandle[][] elements) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Type type = elements[i][j].getType();
                if (type == Type.VISIBLE) {
                    elements[i][j].getImg().setVisibility(View.VISIBLE);
                    elements[i][j].getImg().setImageResource(R.drawable.log);
                } else if (type == Type.COIN) {
                    elements[i][j].getImg().setVisibility(View.VISIBLE);
                    elements[i][j].getImg().setImageResource(R.drawable.img_coin);
                } else {
                    elements[i][j].getImg().setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void renderHearts() {
        int index = gameManager.getWrong() - 1;
        main_IMG_hearts[index].setVisibility(View.INVISIBLE);
    }

    private void initObstaclesMatrix() {
        main_MATRIX_objects = new AppCompatImageView[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                String name = "main_IMG_log_" + (i + 1) + "_" + (j + 1);
                int id = getResources().getIdentifier(name, "id", getPackageName());
                main_MATRIX_objects[i][j] = findViewById(id);
            }
        }
    }

    private void initViews() {
        main_BTN_left.setOnClickListener(view -> {
            if (gameManager.move(KEY_LEFT)) {
                updateVisibility(1);
            }
        });

        main_BTN_right.setOnClickListener(view -> {
            if (gameManager.move(KEY_RIGHT)) {
                updateVisibility(-1);
            }
        });

        gameManager.initMatrix(main_MATRIX_objects);
    }

    private void updateVisibility(int direction) {
        int place = gameManager.getCurrentPlace();
        main_IMG_player[place].setVisibility(View.VISIBLE);
        main_IMG_player[place + direction].setVisibility(View.INVISIBLE);
    }

    private void findHearts() {
        main_IMG_hearts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3),
        };
    }

    private void findPlanes() {
        main_IMG_player = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_frog1),
                findViewById(R.id.main_IMG_frog2),
                findViewById(R.id.main_IMG_frog3),
                findViewById(R.id.main_IMG_frog4),
                findViewById(R.id.main_IMG_frog5)
        };
    }

    private void findButtons() {
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);
        backButton = findViewById(R.id.mainActivity_BTN_goBack);
    }

    private void findViews() {
        findHearts();
        findPlanes();
        findButtons();
        initObstaclesMatrix();
        main_LBL_score = findViewById(R.id.main_LBL_score);
    }

    private void playSound(int soundResource) {
        contactSound = MediaPlayer.create(MainActivity.this, soundResource);
        contactSound.start();
    }
}
