package com.example.HW2_frogy;

import static com.example.HW2_frogy.settings.settings.KEY_LEFT;
import static com.example.HW2_frogy.settings.settings.KEY_RIGHT;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.HW2_frogy.extras.ImgHandle;
import com.example.HW2_frogy.extras.RecordsList;
import com.example.HW2_frogy.extras.Scores;
import com.example.HW2_frogy.extras.Type;
import com.example.HW2_frogy.mapThings.MapManager;
import com.example.HW2_frogy.mapThings.SharedPreferencesManager;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameManager {

    private final int lives;
    private final int rows;
    private final int cols;
    private int placeOfPlayer = 2;
    private ImgHandle[][] mat;
    private int lastIteration = -1;
    private int temp;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private int score = 0;
    private int coinIndex = -1;
    private int placeholder = 0;

    public GameManager(int lives, int rows, int cols) {
        this.lives = lives;
        this.rows = rows;
        this.cols = cols;
        this.mat = new ImgHandle[rows][cols];
    }

    public int getRandomIndex() {
        return (int) (Math.random() * cols);
    }

    public ImgHandle[][] getMatrix() {
        return mat;
    }

    private void shiftMatrix() {
        for (int i = rows - 1; i >= 0; i--) {
            for (int j = 0; j < cols; j++) {
                if (i == rows - 1) {
                    if (mat[i][j].getType() == Type.VISIBLE) {
                        mat[i][j].setType(Type.INVISIBLE);
                        lastIteration = j;
                        coinIndex = -1;
                    } else if (mat[i][j].getType() == Type.COIN) {
                        lastIteration = -1;
                        coinIndex = j;
                    }
                } else {
                    mat[i + 1][j].setType(mat[i][j].getType());
                }
            }
        }
    }

    public void updateTable() {
        tick();
        shiftMatrix();
        if (placeholder == 5) {
            placeRandomCoin();
            placeholder = 0;
        } else {
            placeRandomObstacle();
        }
        incrementScore();
    }

    private void tick() {
        placeholder++;
    }

    private void incrementScore() {
        this.score++;
    }

    private void placeRandomObstacle() {
        int randomIndex = getRandomIndex();
        for (int i = 0; i < cols; i++) {
            mat[0][i].setType(i == randomIndex ? Type.VISIBLE : Type.INVISIBLE);
        }
    }

    private void placeRandomCoin() {
        int randomIndex = getRandomIndex();
        for (int i = 0; i < cols; i++) {
            mat[0][i].setType(i == randomIndex ? Type.COIN : Type.INVISIBLE);
        }
    }

    public boolean move(String direction) {
        if (KEY_LEFT.equals(direction) && placeOfPlayer > 0) {
            placeOfPlayer--;
            return true;
        } else if (KEY_RIGHT.equals(direction) && placeOfPlayer < cols - 1) {
            placeOfPlayer++;
            return true;
        }
        return false;
    }

    public int getCurrentPlace() {
        return placeOfPlayer;
    }

    public void initMatrix(AppCompatImageView[][] matrixView) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mat[i][j] = new ImgHandle()
                        .setImg(matrixView[i][j])
                        .setType(Type.INVISIBLE);
            }
        }
    }

    public boolean checkIsWrong() {
        if (placeOfPlayer == lastIteration && temp < lives) {
            temp++;
            return true;
        }
        return false;
    }

    public boolean checkIsCoin() {
        if (placeOfPlayer == coinIndex) {
            score += 10;
            return true;
        }
        return false;
    }

    public int getWrong() {
        return temp;
    }

    public int getScore() {
        return score;
    }

    public boolean isEndGame() {
        return temp == lives;
    }

    public void saveResults() {
        RecordsList listOfResults;
        String jsonData = SharedPreferencesManager.getInstance().getString("records", "");
        listOfResults = new Gson().fromJson(jsonData, RecordsList.class);
        if (listOfResults == null) {
            listOfResults = new RecordsList();
        }
        listOfResults.getResults().add(createResult());
        SharedPreferencesManager.getInstance().putString("records", new Gson().toJson(listOfResults));
    }

    private Scores createResult() {
        return new Scores()
                .setTime(dateTimeFormatter.format(LocalDateTime.now()))
                .setScore(score)
                .setX(MapManager.getInstance().getLatitude())
                .setY(MapManager.getInstance().getLongitude());
    }
}
