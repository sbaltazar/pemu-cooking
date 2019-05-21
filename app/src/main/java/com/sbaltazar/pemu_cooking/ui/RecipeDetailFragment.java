package com.sbaltazar.pemu_cooking.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.sbaltazar.pemu_cooking.R;
import com.sbaltazar.pemu_cooking.data.models.Ingredient;
import com.sbaltazar.pemu_cooking.data.models.Recipe;
import com.sbaltazar.pemu_cooking.databinding.FragmentRecipeDetailBinding;

import java.security.KeyStore;
import java.util.Locale;

public class RecipeDetailFragment extends Fragment {

    public RecipeDetailFragment() {
    }

    public static RecipeDetailFragment newInstance(@NonNull Recipe recipe) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.EXTRA_RECIPE, recipe);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        FragmentRecipeDetailBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_recipe_detail, container, false);

        if (getArguments() == null) return null;

        Recipe recipe = getArguments().getParcelable(MainActivity.EXTRA_RECIPE);

        if (recipe == null) return null;

        // TODO: In case of image available, set image
        //binding.ivRecipeImage = recipe.getImage();
        // Recipe name
        binding.tvRecipeName.setText(recipe.getName());
        // Recipe servings
        String servingString = String.format(Locale.getDefault(), "%d servings", recipe.getServings());
        binding.tvRecipeServings.setText(servingString);

        StringBuilder ingredientString = new StringBuilder();

        for (Ingredient ingredient : recipe.getIngredients()) {
            String ingredientItem = String.format("\t• %s %s %s\n",
                    ingredient.getQuantity(), ingredient.getMeasureType(), ingredient.getName());
            ingredientString.append(ingredientItem);
        }

        // Recipe ingredients list
        binding.tvIngredientList.setText(ingredientString.toString());

        return binding.getRoot();
    }
}
