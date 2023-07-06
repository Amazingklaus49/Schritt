package com.example.schritt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.SensorListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    int stepCount  = 0;
    TextView stepCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Titel mit Datum
        TextView titel = findViewById(R.id.titel);
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = date.format(formatter);
        titel.setText(formattedDate);

        Button tabelleBtn = findViewById(R.id.tabelleBtn);

        stepCountTextView = findViewById(R.id.schritte);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);



        tabelleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(MainActivity.this , Tabelle.class);
                startActivity(newIntent);
            }
        });
    }

    

}