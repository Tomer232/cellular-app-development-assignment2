package com.example.HW2_frogy.callbacks;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorDetector {

    public interface CallBack_move {
        void move(int direction);
    }

    private final SensorManager sensorManager;
    private final Sensor accelerometer;
    private final CallBack_move callBackMove;

    public SensorDetector(Context context, CallBack_move callBackMove) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.callBackMove = callBackMove;
    }

    public void start() {
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        sensorManager.unregisterListener(sensorEventListener);
    }

    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            Log.d("Accelerometer", "X value: " + x);
            evaluateMovement(x);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // No action needed for accuracy changes
        }
    };

    private void evaluateMovement(float x) {
        if (x > 2.5) {
            callBackMove.move(1);
        } else if (x < -2.5) {
            callBackMove.move(-1);
        }
    }
}
