package com.example.figures;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    public int min, max, numberOfFigures;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getNumberOfFigures() {
        return numberOfFigures;
    }

    public void setNumberOfFigures(int numberOfFigures) {
        this.numberOfFigures = numberOfFigures;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.setMin(extras.getInt("min"));
            this.setMax(extras.getInt("max"));
            this.setNumberOfFigures(extras.getInt("numberOfFigures"));
            if (this.getMin() != -1) {
                EditText min_dimension = findViewById(R.id.minDimensionEditText);
                min_dimension.setText(Integer.toString(this.min));
            }
            if (this.getMax() != -1) {
                EditText max_dimension = findViewById(R.id.maxDimensionEditText);
                max_dimension.setText(Integer.toString(this.max));
            }
            if (this.getNumberOfFigures() != -1) {
                EditText numberOfFigures = findViewById(R.id.numberOfFiguresEditText);
                numberOfFigures.setText(Integer.toString(this.numberOfFigures));
            }
        }
    }

    public void buttonFunctionality(View view) {
        Intent intent = null;
        int requestCode = 1;
        String toastText = "";

        if (view.getId() == R.id.buttonApply) {
            intent = new Intent(this, MainActivity.class);
            EditText minDimensionEditText = findViewById(R.id.minDimensionEditText);
            String min = minDimensionEditText.getText().toString();
            EditText maxDimensionEditText = findViewById(R.id.maxDimensionEditText);
            String max = maxDimensionEditText.getText().toString();
            EditText numberOfFiguresEditText = findViewById(R.id.numberOfFiguresEditText);
            String numberOfFigures = numberOfFiguresEditText.getText().toString();

            boolean tester = true;

            try {
                if (Integer.parseInt(min) > 100 || Integer.parseInt(max) > 100) {
                    Toast.makeText(this, "Min and max values can't be grater than 100.", Toast.LENGTH_SHORT).show();
                    tester = false;
                }
                else if (Integer.parseInt(min) > Integer.parseInt(max)) {
                    Toast.makeText(this, "Min value can't be greater than max value!", Toast.LENGTH_SHORT).show();
                    tester = false;
                }
                else if (Integer.parseInt(min) == Integer.parseInt(max)){
                    Toast.makeText(this, "Min value can't be equal to max value!", Toast.LENGTH_SHORT).show();
                    tester = false;
                }
                else if (Integer.parseInt(numberOfFigures) > 100) {
                    Toast.makeText(this, "Too many figures! Max number is 100.", Toast.LENGTH_SHORT).show();
                    tester = false;
                }
            }
            catch (Exception ex) {
                System.out.println(ex);
            }

            if (min.matches("") || max.matches("")) {
                intent.putExtra("min", Integer.toString(0));
                intent.putExtra("max", Integer.toString(1));
                toastText = "Min and max can't be empty! Used default values: min = 0, max = 1. ";
            }
            else {
                intent.putExtra("min", min);
                intent.putExtra("max", max);
            }

            if (numberOfFigures.matches("")) {
                intent.putExtra("numberOfFigures", Integer.toString(10));
                toastText = toastText + "Number of figures was empty! It was set by default to 10.";
            }
            else {
                intent.putExtra("numberOfFigures", numberOfFigures);
            }

            if (tester && !toastText.matches("")) {
                Toast.makeText(this, toastText, Toast.LENGTH_LONG).show();
            }

            try {
                if (this.getMax() == Integer.parseInt(max) && this.getMin() == Integer.parseInt(min) && this.getNumberOfFigures() == Integer.parseInt(numberOfFigures)){
                    finish();
                }
            }
            catch (Exception ex) {
                System.out.println(ex);
            }

            if (tester) {
                requestCode = 1;
                startActivityForResult(intent, requestCode);
                finish();
            }
        }
    }
}