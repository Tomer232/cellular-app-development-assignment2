package com.example.HW2_frogy;

import com.example.HW2_frogy.extras.RecordsList;
import com.example.HW2_frogy.extras.Scores;
import com.example.HW2_frogy.mapThings.SharedPreferencesManager;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DataManager {

    public static RecordsList getTopResults() {
        RecordsList listOfResults = fetchResults();
        if (listOfResults != null) {
            ArrayList<Scores> sortedResults = sortResultsByScore(listOfResults);
            RecordsList topResults = new RecordsList();
            int topCount = Math.min(sortedResults.size(), 10);
            for (int i = 0; i < topCount; i++) {
                topResults.add(sortedResults.get(i));
            }
            return topResults;
        }
        return listOfResults;
    }

    private static RecordsList fetchResults() {
        String jsonResults = SharedPreferencesManager.getInstance().getString("records", "");
        return new Gson().fromJson(jsonResults, RecordsList.class);
    }

    private static ArrayList<Scores> sortResultsByScore(RecordsList listOfResults) {
        ArrayList<Scores> results = listOfResults.getResults();
        results.sort((r1, r2) -> Integer.compare(r2.getScore(), r1.getScore()));
        return results;
    }
}
