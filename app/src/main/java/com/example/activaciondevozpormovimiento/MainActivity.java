package com.example.activaciondevozpormovimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SpeechActivationListener {
    private static String TAG = "SpeechActivatorStartStop";
    private boolean isListeningForActivation;
    private boolean wasListeningForActivation;
    private SpeechActivator speechActivator;
    /**
     * for saving {@link #wasListeningForActivation}
     * in the saved instance state
     */
    private static final String WAS_LISTENING_STATE = "WAS_LISTENING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isListeningForActivation = false;
        speechActivator = new MovementActivator(this, this);
        // start and stop buttons
        Button start = (Button) findViewById(R.id.btn_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivator();
            }
        });
        Button stop = (Button) findViewById(R.id.btn_stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopActivator();
            }
        });
    }

    private void startActivator() {
        if (isListeningForActivation) {
            Toast.makeText(this, "Not started: already started",
                    Toast.LENGTH_SHORT).show();
            Log.d(TAG, "not started, already started");
// only activate once
            return;
        }
        if (speechActivator != null) {
            isListeningForActivation = true;
            Toast.makeText(this, "Started movement activator", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "started");
            speechActivator.detectActivation();
        }
    }


    private void stopActivator() {
        if (speechActivator != null) {
            Toast.makeText(this, "Stopped", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "stopped");
            speechActivator.stop();
        }
        isListeningForActivation = false;
    }


    @Override
    public void activated(boolean success) {
        Log.d(TAG, "activated...");
//don't allow multiple activations
        if (!isListeningForActivation) {
            Toast.makeText(this, "Not activated because stopped",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (success) {
            Toast.makeText(this, "Activated, no longer listening",
                    Toast.LENGTH_SHORT).show();
            //start speech recognition here
            startSpeechRecognition();
        } else {
            Toast.makeText(this, "activation failed, no longer listening",
                    Toast.LENGTH_SHORT).show();
        }
        isListeningForActivation = false;
    }

    public void startSpeechRecognition() {
        Intent recognizerIntent =
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla");
        try {
            startActivityForResult(recognizerIntent, 800);
        } catch (Exception e) {
            Log.i("ERROR", "Error " + e.getMessage());
        }
    }
}