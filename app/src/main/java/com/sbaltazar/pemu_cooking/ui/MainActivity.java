package com.sbaltazar.pemu_cooking.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.sbaltazar.pemu_cooking.R;
import com.sbaltazar.pemu_cooking.data.models.Recipe;
import com.sbaltazar.pemu_cooking.data.models.RecipeViewModel;
import com.sbaltazar.pemu_cooking.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {

    public static final String EXTRA_RECIPE = "extra_recipe";

    private RecipeViewModel mRecipeViewModel;
    private RecipeAdapter mRecipeAdapter;
    ActivityMainBinding mBinding;

    private boolean mIsTabletLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_recipe);
        }

        mIsTabletLayout = mBinding.vTabletView != null &&
                mBinding.vTabletView.getVisibility() == View.VISIBLE;

        mRecipeAdapter = new RecipeAdapter(this, this);

        // RecyclerView setup
        mBinding.rvRecipes.setHasFixedSize(true);

        if (mIsTabletLayout) {
            mBinding.rvRecipes.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            mBinding.rvRecipes.setLayoutManager(new LinearLayoutManager(this));
        }

        mBinding.rvRecipes.setAdapter(mRecipeAdapter);

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        if (!deviceHasInternetConnection()) {
            showNoInternetHelp(true);
            setLoadingVisibility(false);
        } else {
            setLoadingVisibility(true);
            mRecipeViewModel.getRecipesFromApi().observe(this, new Observer<List<Recipe>>() {
                @Override
                public void onChanged(List<Recipe> recipes) {
                    if (recipes != null && !recipes.isEmpty()) {
                        mRecipeAdapter.setRecipes(recipes);
                        setLoadingVisibility(false);
                        showNoInternetHelp(false);
                    }
                }
            });
        }

        mBinding.btnRetryConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deviceHasInternetConnection()) {
                    setLoadingVisibility(true);
                    showNoInternetHelp(false);
                    mRecipeViewModel.getRecipesFromApi().observe(MainActivity.this, new Observer<List<Recipe>>() {
                        @Override
                        public void onChanged(List<Recipe> recipes) {
                            if (recipes != null && !recipes.isEmpty()) {
                                mRecipeAdapter.setRecipes(recipes);
                                setLoadingVisibility(false);
                            }
                        }
                    });
                } else {
                    showNoInternetHelp(true);
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

    private void setLoadingVisibility(boolean visibility) {
        mBinding.pbLoading.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        mBinding.tvLoading.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

    private boolean deviceHasInternetConnection() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (manager != null) {
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            return (activeNetwork != null && activeNetwork.isConnected());
        }
        return false;
    }

    private void showNoInternetHelp(boolean state) {

        mBinding.tvNoInternet.setVisibility(state ? View.VISIBLE : View.INVISIBLE);
        mBinding.btnRetryConnection.setVisibility(state ? View.VISIBLE : View.INVISIBLE);
        mBinding.rvRecipes.setVisibility(state ? View.INVISIBLE : View.VISIBLE);
    }
}
