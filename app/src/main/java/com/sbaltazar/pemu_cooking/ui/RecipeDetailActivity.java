package com.sbaltazar.pemu_cooking.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;

import com.sbaltazar.pemu_cooking.R;
import com.sbaltazar.pemu_cooking.data.models.Recipe;
import com.sbaltazar.pemu_cooking.databinding.ActivityRecipeDetailBinding;

public class RecipeDetailActivity extends AppCompatActivity {

    ActivityRecipeDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);

        ActionBar bar = getSupportActionBar();

        Recipe recipe;

        if (getIntent() != null && getIntent().hasExtra(MainActivity.EXTRA_RECIPE)) {
            recipe = getIntent().getParcelableExtra(MainActivity.EXTRA_RECIPE);
        } else {
            Toast.makeText(this, "Error, no recipe found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Up navigation
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setTitle(recipe.getName());
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeDetailFragment fragment = RecipeDetailFragment.newInstance(recipe);
        fragmentManager.beginTransaction().add(R.id.fl_recipe_detail_container, fragment).commit();

    }
}
