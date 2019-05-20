package com.sbaltazar.pemu_cooking.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {

    private double quantity;
    @SerializedName("measure")
    private MeasureType measureType;
    @SerializedName("ingredient")
    private String name;

    public Ingredient(double quantity, MeasureType measureType, String name) {
        this.quantity = quantity;
        this.measureType = measureType;
        this.name = name;
    }

    private Ingredient(Parcel in) {
        quantity = in.readDouble();
        measureType = MeasureType.values()[in.readInt()];
        name = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeInt(measureType.ordinal());
        dest.writeString(name);
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
        @SerializedName("CUP")
        CUP,
        @SerializedName("TBLSP")
        TABLESPOON,
        @SerializedName("TSP")
        TEASPOON,
        @SerializedName("K")
        KILOGRAM,
        @SerializedName("G")
        GRAM,
        @SerializedName("OZ")
        OUNCE,
        @SerializedName("UNIT")
        UNIT
    }
}
