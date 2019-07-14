package com.sbaltazar.pemu_cooking.network;

import com.sbaltazar.pemu_cooking.data.models.Recipe;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {

    @GET("/android-baking-app-json")
    Call<List<Recipe>> getRecipes();

    @GET("/android-baking-app-json")
    Single<List<Recipe>> getRecipesRx();

}
