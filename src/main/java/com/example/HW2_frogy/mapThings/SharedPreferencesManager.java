package com.example.HW2_frogy.mapThings;

import static com.example.HW2_frogy.settings.settings.DB_FILE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static SharedPreferencesManager instance = null;
    private final SharedPreferences preferences;

    private SharedPreferencesManager(Context context) {
        preferences = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesManager getInstance() {
        return instance;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesManager(context);
        }
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String def) {
        return preferences.getString(key, def);
    }
}
