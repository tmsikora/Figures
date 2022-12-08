package com.example.figures;

import static java.lang.Math.*;

public class Circle extends Figure {

    public Circle(String name, float linearDimension) {
        this.setName(name);
        this.setLinearDimension(linearDimension);
    }

    //metoda do obliczenia srednicy kola
    public double diameter() {
        return linearDimension * 2;
    }

    @Override
    public double getCharacteristicValue() {
        return this.diameter();
    }

    @Override
    public double area(float linearDimension) {
        return PI * pow(linearDimension, 2);
    }

    @Override
    public int type() {
        return R.drawable.donut;
    }

    @Override
    public String printCharacteristicValue() {
        return ("Diameter: " + String.format("%.3f", this.getCharacteristicValue()));
    }
}