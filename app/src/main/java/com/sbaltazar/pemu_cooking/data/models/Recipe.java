package com.sbaltazar.pemu_cooking.data.models;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe {

    private int id;
    private String name;
    private List<Ingredient> ingredients;
    @SerializedName("steps")
    private List<CookingStep> cookingSteps;
    private int servings;
    @Nullable
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<CookingStep> getCookingSteps() {
        return cookingSteps;
    }

    public void setCookingSteps(List<CookingStep> cookingSteps) {
        this.cookingSteps = cookingSteps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    @Nullable
    public String getImage() {
        return image;
    }

    public void setImage(@Nullable String image) {
        this.image = image;
    }
}
