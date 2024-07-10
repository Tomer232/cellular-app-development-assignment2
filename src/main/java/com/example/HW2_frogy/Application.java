package com.example.HW2_frogy;

import com.example.HW2_frogy.mapThings.MapManager;
import com.example.HW2_frogy.mapThings.SharedPreferencesManager;
import com.example.HW2_frogy.mapThings.Signals;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Signals.init(this);
        SharedPreferencesManager.init(this);
        MapManager.init(this);

    }
}