package com.sbaltazar.pemu_cooking.data.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sbaltazar.pemu_cooking.network.NetworkUtils;
import com.sbaltazar.pemu_cooking.network.RecipeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RecipeViewModel extends AndroidViewModel {

    public RecipeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Recipe>> getRecipesFromApi() {

        final MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();

        RecipeService service = NetworkUtils.getRecipeService();
        Call<List<Recipe>> recipesCall = service.getRecipes();

        recipesCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    recipes.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                recipes.setValue(null);
                Timber.w(t, "Error when fetching recipes list");
            }
        });

        return recipes;
    }
}
