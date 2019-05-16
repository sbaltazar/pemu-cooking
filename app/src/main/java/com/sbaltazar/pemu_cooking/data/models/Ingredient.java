package com.sbaltazar.pemu_cooking.data.models;

public class Ingredient {

    private double quantity;
    private MeasureType measureType;
    private String name;

    public Ingredient(double quantity, MeasureType measureType, String name) {
        this.quantity = quantity;
        this.measureType = measureType;
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public MeasureType getMeasureType() {
        return measureType;
    }

    public void setMeasureType(MeasureType measureType) {
        this.measureType = measureType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private enum MeasureType {
        CUP("CUP"),
        TABLESPOON("TBSP"),
        TEASPOON("TSP"),
        KILOGRAM("K"),
        GRAM("G"),
        OUNCE("OZ"),
        UNIT("UNIT");

        private final String measureAbbr;

        MeasureType(String measureAbbr) {
            this.measureAbbr = measureAbbr;
        }

        public String getMeasureAbbr() {
            return this.measureAbbr;
        }
    }
}
