package com.sbaltazar.pemu_cooking.data.models;

import android.graphics.Bitmap;

import java.util.List;

public class Recipe {

    private int id;
    private String name;
    private List<Ingredient> ingredients;
    private List<CookingStep> cookingSteps;
    private int servings;
    private Bitmap image;

}
