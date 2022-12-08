package com.example.figures;

public abstract class Figure {

    //pola charakteryzujace wspolne wlasnosci wszystkich figur
    private String name;    //nazwa figury
    protected float linearDimension;    //wymiar liniowy
    private float area;     //pole powierzchni

    //metoda do pozyskania wymiaru liniowego figury
    public float getLinearDimension() {
        return linearDimension;
    }

    //metoda do ustawienia nazwy przekazanej jako parametr
    public void setName(String name) {
        this.name = name;
    }

    //metoda do pozyskania nazwy figury
    public String getName() {
        return name;
    }

    //metoda do ustawienia wartosci wymiaru liniowego
    public void setLinearDimension(float linearDimension) {
        this.linearDimension = linearDimension;
    }

    //metoda do pozyskania pola powierzchni figury
    public double getArea() { return this.area(linearDimension); }

    //metoda do pozyskania wartosci charakteryzujacej ceche zalezna od typu figury
    public abstract double getCharacteristicValue();

    //metoda do obliczenia pola powierzchni figury
    public abstract double area(float linearDimension);

    //metoda do zwracania grafiki danej figury
    public abstract int type();

    //metoda do zwracania nazwy cechy charakterystycznej figury oraz jej wartosci
    public abstract String printCharacteristicValue();
}