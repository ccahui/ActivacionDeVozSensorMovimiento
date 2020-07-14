package com.example.activaciondevozpormovimiento;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

class MovementDetector {
    private static final float ACELERACION_TOTAL_MAXIMA_ACEPTADA = 10f;

    MovementDetectionListener movementDetectionListener;
    private SensorManager sensorManager;
    private SensorMovimiento sensorMovimiento;
    private  Context context;
    public MovementDetector(Context context){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorMovimiento= new SensorMovimiento(this);
    }

    public void startReadingAccelerationData(MovementDetectionListener movementDetectionListener){
        this.movementDetectionListener = movementDetectionListener;
        registerListener();
    }
    public void stopReadingAccelerationData(){
        unregisterListener();
    }
    public void aceleracionTotal(float aceleracionTotal){
        if(aceleracionTotal >= ACELERACION_TOTAL_MAXIMA_ACEPTADA){
            movementDetectionListener.movementDetected(true);
        }
    }



    public void registerListener() {
        listenerSensorAcelerometro();
    }

    public void unregisterListener() {
        sensorManager.unregisterListener(sensorMovimiento);
    }

    private void listenerSensorAcelerometro(){
        Sensor sensor1 = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(
                sensorMovimiento,
                sensor1,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
}
