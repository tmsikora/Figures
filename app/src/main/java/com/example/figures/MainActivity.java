package com.example.figures;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Figure> figures;
    //wartosci minimalna i maksymalna zakresu wartosci wymiaru liniowego <min, max> oraz liczba figur
    private int min = -1, max = -1, numberOfFigures = -1;
    float[] stats = new float[9];   //tablica do przechowywania statystyk dotyczaccyh wygenerowanyh figur
    private int orderOfList = 0;    //zmienna do zapamietywania sposobu posortowania listy (0 oznacza brak posortowania)
    private View tempView, previousView;

    //generowanie listy figur
    public ArrayList<Figure> generateFigures(int numberOfFigures, int min, int max) {
        Random generator = new Random(); //generator liczb pseudolosowych
        ArrayList<Figure> figures = new ArrayList<Figure>();

        for (int i = 0; i < numberOfFigures; i += 1) {
            int figureType = generator.nextInt(3);  //generowanie jednego z trzech typow figury
            if (figureType == 0) {
                Square square = new Square("Square", min + generator.nextFloat() * (max - min));
                figures.add(square);    //dodanie kwdratu do listy
            }
            else if (figureType == 1) {
                Circle circle = new Circle("Circle", min + generator.nextFloat() * (max - min));
                figures.add(circle);    //dodanie okregu do listy
            }
            else if (figureType == 2) {
                Triangle triangle = new Triangle("Triangle", min + generator.nextFloat() * (max - min));
                figures.add(triangle);  //dodanie trojkata do listy
            }
        }

        return figures;
    }

    //wstawianie figur
    private void insertFigures(int orderBy) {
        LinearLayout ln = (LinearLayout) findViewById(R.id.LinearLayout1);

        //sortowanie listy figur alfabetycznie na podstawie nazwy figury
        if (orderBy == 1) {
            orderOfList = 1;
            this.figures.sort((figure1, figure2) -> figure1.getName().compareTo(figure2.getName()));
        }
        //sortowanie listy figur rosnaco na podstawie pola figury
        else if (orderBy == 2) {
            orderOfList = 2;
            Collections.sort(figures, new Comparator<Figure>() {
                @Override
                public int compare(Figure figure1, Figure figure2) {
                    return Double.compare(figure1.getArea(), figure2.getArea() );
                }
            });
        }
        //sortowanie listy figur rosnaco na podstawie wartosci cechy charakterystycznej figury
        else if (orderBy == 3) {
            orderOfList = 3;
            Collections.sort(figures, new Comparator<Figure>() {
                @Override
                public int compare(Figure figure1, Figure figure2) {
                    return Double.compare(figure1.getCharacteristicValue(), figure2.getCharacteristicValue());
                }
            });
        }

        //wyzerowanie tablicy statystyk
        for (int i = 0; i < stats.length ; i++) {
            stats[i] = 0;
        }

        for (Figure figure : figures) {
            //sumowanie liczby figur i ich wartosci do statystyk
            if (figure.getName().equals("Square")) {
                this.stats[0] += 1;
                this.stats[1] += figure.getArea();
                this.stats[2] += figure.getCharacteristicValue();
            } else if (figure.getName().equals("Circle")) {
                this.stats[3] += 1;
                this.stats[4] += figure.getArea();
                this.stats[5] += figure.getCharacteristicValue();
            } else if (figure.getName().equals("Triangle")) {
                this.stats[6] += 1;
                this.stats[7] += figure.getArea();
                this.stats[8] += figure.getCharacteristicValue();
            }

            View row = getLayoutInflater().inflate(R.layout.row, null);

            ImageView imageView1 = row.findViewById(R.id.figureIconImageView);
            TextView textView1 = row.findViewById(R.id.figureAreaTextView);
            TextView textView2 = row.findViewById(R.id.figureAttributeTextView);
            imageView1.setImageResource(figure.type());
            textView1.setText(String.format("%.3f", figure.getArea()));
            textView2.setText(figure.printCharacteristicValue());
            ln.addView(row);    //dodanie utworzonego rzędu do układu liniowego

            registerForContextMenu(row);    //zarejestrowanie utworzonego rzedu do menu kontekstowego
        }
    }

    //obsluga funkcjonalnosci przyciskow
    public void buttonFunctionality(View view) {
        Intent intent = null;
        int requestCode = 1;

        switch (view.getId()) {
            case R.id.buttonAuthor:
                intent = new Intent(this, Author.class);
                startActivityForResult(intent, requestCode);
                break;

            case R.id.buttonStats:
                intent = new Intent(this, Stats.class);
                requestCode = 1;
                intent.putExtra("squareNumber", this.stats[0]);
                intent.putExtra("squareAreas", this.stats[1]);
                intent.putExtra("squareAttributes", this.stats[2]);
                intent.putExtra("circleNumber", this.stats[3]);
                intent.putExtra("circleAreas", this.stats[4]);
                intent.putExtra("circleAttributes", this.stats[5]);
                intent.putExtra("triangleNumber", this.stats[6]);
                intent.putExtra("triangleAreas", this.stats[7]);
                intent.putExtra("triangleAttributes", this.stats[8]);
                startActivityForResult(intent, requestCode);
                break;

            case R.id.buttonSettings:
                intent = new Intent(this, Settings.class);
                requestCode = 2;
                intent.putExtra("min", this.min);
                intent.putExtra("max", this.max);
                intent.putExtra("numberOfFigures", this.numberOfFigures);
                startActivityForResult(intent, requestCode);
                break;

            case R.id.orderByFigures:
                LinearLayout ln = findViewById(R.id.LinearLayout1);
                ln.removeAllViews();
                this.insertFigures(1);
                break;

            case R.id.orderByAreas:
                LinearLayout ln1 = findViewById(R.id.LinearLayout1);
                ln1.removeAllViews();
                this.insertFigures(2);
                break;

            case R.id.orderByAttributes:
                LinearLayout ln2 = findViewById(R.id.LinearLayout1);
                ln2.removeAllViews();
                this.insertFigures(3);
                break;
        }
    }

    //generowanie pojedynczej figury do dodania do listy
    public static Figure generateOneFigure(float min, float max) {
        Random generator = new Random(); //generator liczb pseudolosowych

        Figure figure = null;
        int figureType = generator.nextInt(3);  //generowanie jednego z trzech typow figury
        if (figureType == 0) {
            figure = new Square("Square", min + generator.nextFloat() * (max - min));
        }
        else if (figureType == 1) {
            figure = new Circle("Circle", min + generator.nextFloat() * (max - min));
        }
        else if (figureType == 2) {
            figure = new Triangle("Triangle", min + generator.nextFloat() * (max - min));
        }

        return figure;
    }

    //usuniecie wyswietlanej listy figur i wstawienie nowej aktualnej listy figur
    public void reloadFigures(LinearLayout ln) {
        ln.removeAllViews();
        this.insertFigures(this.orderOfList);
    }

    //edytowanie wybranego elementu
    public void editElement(LinearLayout ln, String name, int type) {
        Random generator = new Random(); //generator liczb pseudolosowych
        int elementToEdit = ln.indexOfChild(tempView);
        Figure figure = figures.get(elementToEdit);
        int index = figures.indexOf(figure);

        if (figure.getName().equals(name)) {
            Toast.makeText(this, "This element already is a " + name + ". Any change wasn't made.", Toast.LENGTH_SHORT).show();
        }
        else {
            Figure newFigure = null;
            switch (type) {
                case 0: {
                    Square square = new Square("Square", min + generator.nextFloat() * max);
                    newFigure = square;
                    Toast.makeText(this, "Changed chosen element to a Square.", Toast.LENGTH_SHORT).show();
                    break;
                }
                case 1: {
                    Circle circle = new Circle("Circle", min + generator.nextFloat() * max);
                    newFigure = circle;
                    Toast.makeText(this, "Changed chosen element to a Circle.", Toast.LENGTH_SHORT).show();
                    break;
                }
                case 2: {
                    Triangle triangle = new Triangle("Triangle", min + generator.nextFloat() * max);
                    newFigure = triangle;
                    Toast.makeText(this, "Changed chosen element to a Triangle.", Toast.LENGTH_SHORT).show();
                    break;
                }
            }

            figures.set(index, newFigure); //zmiana obiektu w liscie figur na nowo wygenerowany
            reloadFigures(ln);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.min = Integer.parseInt(extras.getString("min"));
            this.max = Integer.parseInt(extras.getString("max"));
            this.numberOfFigures = Integer.parseInt(extras.getString("numberOfFigures"));
        }

        //generacja listy figur i wstawienie jej
        this.figures = generateFigures(this.numberOfFigures, this.min, this.max);
        insertFigures(0);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        if (tempView != null) {
            tempView.setBackgroundResource(R.color.transparent);
        }
        tempView = v;
        v.setBackgroundResource(R.color.gray);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        LinearLayout ln = findViewById(R.id.LinearLayout1);

        switch (item.getItemId()) {
            //dodanie losowego elementu do listy
            case R.id.addNewElement:
                Figure newFigure = generateOneFigure(this.min, this.max);
                figures.add(newFigure);
                reloadFigures(ln);
                Toast.makeText(this, "New " + newFigure.getName() + " added.", Toast.LENGTH_SHORT).show();
                return true;

            //duplikowanie wybranego elementu z listy
            case R.id.duplicateElement:
                int figureToCopy = ln.indexOfChild(tempView);
                Figure figureCopy = figures.get(figureToCopy);
                figures.add(figureCopy);
                reloadFigures(ln);
                Toast.makeText(this, figureCopy.getName() + " duplicated.", Toast.LENGTH_SHORT).show();
                return true;

            //usuniecie wszystkich figur z listy i ponowna generacja
            case R.id.deleteAllAndGenerateNew:
                ln.removeAllViews();
                numberOfFigures = figures.size();
                this.figures = generateFigures(numberOfFigures, min, max);
                insertFigures(0);
                Toast.makeText(this, "Deleted all figures and generated new list of figures.", Toast.LENGTH_SHORT).show();
                return true;

            //usuwanie wybranego elementu z listy
            case R.id.deleteElement:
                int figureToRemove = ln.indexOfChild(tempView);
                Figure figure = figures.get(figureToRemove);
                figures.remove(figureToRemove);
                ln.removeView(tempView);
                reloadFigures(ln);
                Toast.makeText(this, figure.getName() + " deleted.", Toast.LENGTH_SHORT).show();
                return true;

            //zmiana na kwadrat
            case R.id.replaceBySquare:
                editElement(ln, "Square", 0);
                return true;

            //zmiana na okrag
            case R.id.replaceByCircle:
                editElement(ln, "Circle", 1);
                return true;

            //zmiana na trojkat
            case R.id.replaceByTriangle:
                editElement(ln, "Triangle", 2);
                return true;

            default:
                tempView.setBackgroundResource(R.color.gray);
                return super.onContextItemSelected(item);
        }
    }
}