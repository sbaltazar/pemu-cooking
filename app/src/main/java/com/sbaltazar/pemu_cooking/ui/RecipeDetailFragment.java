package com.sbaltazar.pemu_cooking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sbaltazar.pemu_cooking.R;
import com.sbaltazar.pemu_cooking.data.models.Ingredient;
import com.sbaltazar.pemu_cooking.data.models.Recipe;
import com.sbaltazar.pemu_cooking.databinding.FragmentRecipeDetailBinding;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class RecipeDetailFragment extends Fragment {

    private CookingStepAdapter.OnCookingStepClickListener mListener;

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

    void setOnCookingStepClickListener(CookingStepAdapter.OnCookingStepClickListener listener) {
        mListener = listener;
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

        // Recipe image
        if (!TextUtils.isEmpty(recipe.getImageUrl())) {
            Picasso.get().load(recipe.getImageUrl()).into(binding.ivRecipeImage);
        }

        // Recipe name
        binding.tvRecipeName.setText(recipe.getName());
        // Recipe servings
        String servingString = String.format(Locale.getDefault(), "(%d servings)", recipe.getServings());
        binding.tvRecipeServings.setText(servingString);

        StringBuilder ingredientString = new StringBuilder();

        for (Ingredient ingredient : recipe.getIngredients()) {
            String ingredientItem = String.format(Locale.getDefault(), "• %.0f %s %s\n",
                    ingredient.getQuantity(), ingredient.getMeasureType().getMeasure(), ingredient.getName());
            ingredientString.append(ingredientItem);
        }

        // Recipe ingredients list
        binding.tvRecipeIngredients.setText(ingredientString.toString());

        mCookingStepAdapter = new CookingStepAdapter(getContext(), mListener);
        mCookingStepAdapter.setCookingSteps(recipe.getCookingSteps());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        DividerItemDecoration divider = new DividerItemDecoration(binding.rvRecipeCookingSteps.getContext(),
                layoutManager.getOrientation());

        binding.rvRecipeCookingSteps.setHasFixedSize(true);
        binding.rvRecipeCookingSteps.setLayoutManager(layoutManager);
        binding.rvRecipeCookingSteps.addItemDecoration(divider);
        binding.rvRecipeCookingSteps.setAdapter(mCookingStepAdapter);


        // Removes the scroll behavior from the RecyclerView to use the NestedScrollView instead
        ViewCompat.setNestedScrollingEnabled(binding.rvRecipeCookingSteps, false);

        return binding.getRoot();
    }


}
