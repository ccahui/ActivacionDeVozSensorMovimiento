package com.example.activaciondevozpormovimiento;

import android.content.Context;

public class MovementActivator implements SpeechActivator, MovementDetectionListener
{
    private MovementDetector detector;
    private SpeechActivationListener resultListener;
    public MovementActivator(Context context,
                             SpeechActivationListener resultListener)
    {
        detector = new MovementDetector(context);
        this.resultListener = resultListener;
    }
    @Override
    public void detectActivation()
    {
        detector.startReadingAccelerationData(this);
    }
    @Override
    public void stop()
    {
        detector.stopReadingAccelerationData();
    }
    @Override
    public void movementDetected(boolean success)
    {
        stop();
        resultListener.activated(success);
    }

}