package com.example.figures;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Stats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        //wpisywanie wartosci z tablicy do odpowiadajacych im pol tabeli statystyk
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {
                TextView squareNumber = findViewById(R.id.squareNumber);
                squareNumber.setText(String.format("%.0f", extras.getFloat("squareNumber")));
                TextView squareAreas = findViewById(R.id.squareAreas);
                squareAreas.setText(String.format("%.3f", extras.getFloat("squareAreas")));
                TextView squareAttributes = findViewById(R.id.squareAttributes);
                squareAttributes.setText(String.format("%.3f", extras.getFloat("squareAttributes")));

                TextView circleNumber = findViewById(R.id.circleNumber);
                circleNumber.setText(String.format("%.0f", extras.getFloat("circleNumber")));
                TextView circleAreas = findViewById(R.id.circleAreas);
                circleAreas.setText(String.format("%.3f", extras.getFloat("circleAreas")));
                TextView circleAttributes = findViewById(R.id.circleAttributes);
                circleAttributes.setText(String.format("%.3f", extras.getFloat("circleAttributes")));

                TextView triangleNumber = findViewById(R.id.triangleNumber);
                triangleNumber.setText(String.format("%.0f", extras.getFloat("triangleNumber")));
                TextView triangleAreas = findViewById(R.id.triangleAreas);
                triangleAreas.setText(String.format("%.3f", extras.getFloat("triangleAreas")));
                TextView triangleAttributes = findViewById(R.id.triangleAttributes);
                triangleAttributes.setText(String.format("%.3f", extras.getFloat("triangleAttributes")));
            }
            catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    public void buttonFunctionality(View view) {
        if (view.getId() == R.id.buttonBack) {
            finish();
        }
    }
}