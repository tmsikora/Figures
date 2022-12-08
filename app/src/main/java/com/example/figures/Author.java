package com.example.figures;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class Author extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
    }

    //obsluga funckjonalnosci przyciskow
    public void buttonFunctionality(View view) {
        if (view.getId() == R.id.buttonBack) {
            finish();
        }
    }
}
