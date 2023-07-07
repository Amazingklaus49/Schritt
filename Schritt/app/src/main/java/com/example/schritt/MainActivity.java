package com.example.schritt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    public int countSteps = 0;
    TextView TexVSteps;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    String dateNew;
    String dateOld;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);

        //Titel mit Datum
        TextView titel = findViewById(R.id.titel);
        dateNew = getDateNow();
        titel.setText(String.valueOf(dateNew));

        Button tabelleBtn = findViewById(R.id.tabelleBtn);
        TexVSteps = findViewById(R.id.schritte);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sharedPreferences = getSharedPreferences("counterPrefs", MODE_PRIVATE);
        resetStepCount();

        tabelleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(MainActivity.this, Tabelle.class);
                startActivity(newIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // StepCounter Sensor Listener wird registriert
        Sensor stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(MainActivity.this, "Pedometer nicht verfügbar!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Update step count bei Interaktion
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {

            String dateNew = getDateNow();
            String dateOld = getOldDate();

            if (!dateNew.equals(dateOld)) {
                resetStepCount();
            }

            countSteps = (int) event.values[0];
            TexVSteps.setText(String.valueOf(countSteps));
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            int progressBarValue = (int) ((float) countSteps / 10000 * 100);
            progressBar.setProgress(progressBarValue);

        }
    }

    private void resetStepCount() {
        countSteps = 0;
        TexVSteps.setText(String.valueOf(countSteps));
        dateNew = getDateNow();

        // Date Speichern
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lastRecordedDate", dateNew);
        editor.apply();

    }

    //Datum hier und jetzt
    String getDateNow() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    //altes Datum aus Preferences holen
    String getOldDate() {
        dateOld = sharedPreferences.getString("lastRecordedDate", "");
        if (!dateNew.equals(getDateNow())) {
            resetStepCount();
        }
        return dateOld;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}