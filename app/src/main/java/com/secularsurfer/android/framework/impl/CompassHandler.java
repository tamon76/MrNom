package com.secularsurfer.android.framework.impl;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class CompassHandler implements SensorEventListener {

    private static final String TAG = CompassHandler.class.getSimpleName();

    private float[] gData = new float[3];
    private float[] mData = new float[3];
    private float[] rMat = new float[9];
    private float[] iMat = new float[9];
    private float[] orientation = new float[3];

    private int mAzimuth = 0;

    public CompassHandler(Context context) {
        boolean haveAccelerometer = false;
        boolean haveMagnetometer = false;

        Sensor mAccelerometer;
        Sensor mMagnetometer;

        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        mAccelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        haveAccelerometer = manager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);

        haveMagnetometer = manager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_GAME);

        if (haveAccelerometer && haveMagnetometer) {
            // ready to go
            Log.v(TAG, "compass is ready to go");
        } else {
            // unregister
            manager.unregisterListener(this, mAccelerometer);
            manager.unregisterListener(this, mMagnetometer);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] data;

        switch(event.sensor.getType()) {

            case Sensor.TYPE_ACCELEROMETER:
                gData = event.values.clone();
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                mData = event.values.clone();
                break;

            default:
                return;
        }

        if (SensorManager.getRotationMatrix( rMat, iMat, gData, mData)) {
            mAzimuth = (int) (Math.toDegrees(SensorManager.getOrientation(rMat, orientation)[0]) + 360) % 360;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nothing to do here
    }

    public int getAzimuth() {
        return mAzimuth;
    }
}
