package com.example.activaciondevozpormovimiento;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class SensorMovimiento implements SensorEventListener  {

    private String TAG = "SensorMovimiento";
    private float[] accelValues;
    private MovementDetector movementDetector;

    public SensorMovimiento(MovementDetector  movementDetector){
        this.movementDetector = movementDetector;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        accelValues = sensorEvent.values.clone();


        float x = accelValues[0];
        float y = accelValues[1];
        float z = accelValues[2];

        float aceleracionTotal = calcularAceleracionTotal(x, y, z);
        movementDetector.aceleracionTotal(aceleracionTotal);
        Log.i(TAG, aceleracionTotal+"");
    }

    private float calcularAceleracionTotal(float x, float y, float z) {
        float x2 = (float)Math.pow(x, 2);
        float y2 = (float)Math.pow(y, 2);
        float z2 = (float)Math.pow(z, 2);
        float aceleracionTotal = (float)Math.sqrt(x2 + y2 + z2);

        return aceleracionTotal;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }



}