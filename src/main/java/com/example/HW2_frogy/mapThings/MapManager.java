package com.example.HW2_frogy.mapThings;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import im.delight.android.location.SimpleLocation;

public class MapManager {
    private static MapManager instance = null;

    private final Context context;
    private final SimpleLocation location;

    private MapManager(Context context) {
        this.context = context;
        this.location = new SimpleLocation(context);
    }

    public static MapManager getInstance() {
        return instance;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new MapManager(context);
        }
    }

    public void checkPreferences(AppCompatActivity activity) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
        }
    }

    public double getLatitude() {
        return location.getLatitude();
    }

    public double getLongitude() {
        return location.getLongitude();
    }

    public void start() {
        location.beginUpdates();
    }

    public void stop() {
        location.endUpdates();
    }
}
