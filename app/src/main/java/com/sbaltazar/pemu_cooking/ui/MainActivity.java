package com.sbaltazar.pemu_cooking.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.sbaltazar.pemu_cooking.R;
import com.sbaltazar.pemu_cooking.data.models.Recipe;
import com.sbaltazar.pemu_cooking.data.models.RecipeViewModel;
import com.sbaltazar.pemu_cooking.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecipeViewModel mRecipeViewModel;
    private RecipeAdapter mRecipeAdapter;
    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mRecipeAdapter = new RecipeAdapter(this);

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
                }
            }
        });
    }
}
