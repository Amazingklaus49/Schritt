package com.example.schritt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Tabelle extends MainActivity {
    TextView schritteTxtV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabelle);

        schritteTxtV = findViewById(R.id.schritteTxtV);
        Button backBtn = findViewById(R.id.back);
        schritteTxtV.setText(String.valueOf(countSteps));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(Tabelle.this , MainActivity.class);
                startActivity(newIntent);
            }
        });
    }
}