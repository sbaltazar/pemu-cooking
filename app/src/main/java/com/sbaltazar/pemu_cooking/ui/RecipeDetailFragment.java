package com.sbaltazar.pemu_cooking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sbaltazar.pemu_cooking.R;
import com.sbaltazar.pemu_cooking.data.models.Ingredient;
import com.sbaltazar.pemu_cooking.data.models.Recipe;
import com.sbaltazar.pemu_cooking.databinding.FragmentRecipeDetailBinding;

import java.util.ArrayList;
import java.util.Locale;

public class RecipeDetailFragment extends Fragment implements CookingStepAdapter.OnCookingStepClickListener {

    public static final String EXTRA_COOKING_STEP_POSITION = "extra_cooking_step_position";
    public static final String EXTRA_COOKING_STEP_LIST = "extra_cooking_step_list";

    private CookingStepAdapter mCookingStepAdapter;

    public RecipeDetailFragment() {
    }

    static RecipeDetailFragment newInstance(@NonNull Recipe recipe) {
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

        mCookingStepAdapter = new CookingStepAdapter(getContext(), this);
        mCookingStepAdapter.setCookingSteps(recipe.getCookingSteps());

        binding.rvRecipeCookingSteps.setHasFixedSize(true);
        binding.rvRecipeCookingSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvRecipeCookingSteps.setAdapter(mCookingStepAdapter);

        // Removes the scroll behavior from the RecyclerView to use the NestedScrollView instead
        ViewCompat.setNestedScrollingEnabled(binding.rvRecipeCookingSteps, false);

        return binding.getRoot();
    }

    @Override
    public void onCookingStepClick(View view, int position) {

        // TODO: When in small device start new activity
        Intent intent = new Intent(getContext(), CookingStepActivity.class);
        intent.putExtra(EXTRA_COOKING_STEP_POSITION, position);
        intent.putParcelableArrayListExtra(EXTRA_COOKING_STEP_LIST,
                new ArrayList<Parcelable>(mCookingStepAdapter.getAllCookingSteps()));

        startActivity(intent);

        // TODO: Otherwise load the fragment in the same host activity
    }
}
