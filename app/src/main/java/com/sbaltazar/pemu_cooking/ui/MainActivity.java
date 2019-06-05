package com.sbaltazar.pemu_cooking.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sbaltazar.pemu_cooking.R;
import com.sbaltazar.pemu_cooking.data.models.Recipe;
import com.sbaltazar.pemu_cooking.data.models.RecipeViewModel;
import com.sbaltazar.pemu_cooking.databinding.ActivityMainBinding;

import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {

    public static final String EXTRA_RECIPE = "extra_recipe";

    private RecipeViewModel mRecipeViewModel;
    private RecipeAdapter mRecipeAdapter;
    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.title_recipe);
        }

        mRecipeAdapter = new RecipeAdapter(this, this);

        // RecyclerView setup
        mBinding.rvRecipes.setHasFixedSize(true);
        mBinding.rvRecipes.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvRecipes.setAdapter(mRecipeAdapter);

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        mRecipeViewModel.getRecipesFromApi().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes != null && !recipes.isEmpty()) {
                    mRecipeAdapter.setRecipes(recipes);
                    setLoadingVisibility(false);
                }
            }
        });
    }

    @Override
    public void onRecipeClick(View view, int position) {

        Recipe recipe = mRecipeAdapter.getRecipe(position);

        Intent recipeDetailIntent = new Intent(this, RecipeDetailActivity.class);

        // Putting the recipe parcelable
        recipeDetailIntent.putExtra(EXTRA_RECIPE, recipe);
        startActivity(recipeDetailIntent);
    }

    private void setLoadingVisibility(boolean visibility){
        mBinding.pbLoading.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        mBinding.tvLoading.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }
}
