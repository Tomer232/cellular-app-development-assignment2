package com.example.HW2_frogy.extras;

import java.util.ArrayList;

public class RecordsList {

    private ArrayList<Scores> results;

    public RecordsList() {
        results = new ArrayList<>();
    }

    public ArrayList<Scores> getResults() {
        return results;
    }

    public RecordsList setResults(ArrayList<Scores> results) {
        this.results = results;
        return this;
    }

    public int size() {
        return results.size();
    }

    public void add(Scores result) {
        results.add(result);
    }

    public Scores get(int index) {
        return results.get(index);
    }
}
