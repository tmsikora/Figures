package com.example.figures;

import static java.lang.Math.*;

public class Square extends Figure {

    public Square(String name, float linearDimension) {
        this.setName(name);
        this.setLinearDimension(linearDimension);
    }

    //metoda do obliczenia przekatnej kwadratu
    public double diagonal() {
        return linearDimension * sqrt(2);
    }

    @Override
    public double getCharacteristicValue() {
        return this.diagonal();
    }

    @Override
    public double area(float linearDimension) {
        return linearDimension * linearDimension;
    }

    @Override
    public int type() {
        return R.drawable.biscuit;
    }

    @Override
    public String printCharacteristicValue() {
        return ("Diagonal: " + String.format("%.3f", this.getCharacteristicValue()));
    }
}