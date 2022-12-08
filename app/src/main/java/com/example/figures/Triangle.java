package com.example.figures;

import static java.lang.Math.*;

public class Triangle extends Figure {

    public Triangle(String name, float linearDimension) {
        this.setName(name);
        this.setLinearDimension(linearDimension);
    }

    //metoda do obliczenia wysokosci trojkata
    public double height() {
        return linearDimension * sqrt(3) / 2;
    }

    @Override
    public double getCharacteristicValue() {
        return this.height();
    }

    @Override
    public double area(float linearDimension) {
        return pow(linearDimension, 2) * sqrt(3) / 4;
    }

    @Override
    public int type() {
        return R.drawable.chocolate;
    }

    @Override
    public String printCharacteristicValue() {
        return ("Height: " + String.format("%.3f", this.getCharacteristicValue()));
    }
}