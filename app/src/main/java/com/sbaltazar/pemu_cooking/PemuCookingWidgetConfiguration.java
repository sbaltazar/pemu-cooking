package com.sbaltazar.pemu_cooking;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sbaltazar.pemu_cooking.data.models.Recipe;
import com.sbaltazar.pemu_cooking.data.models.RecipeViewModel;
import com.sbaltazar.pemu_cooking.databinding.ConfigurationPemuCookingWidgetBinding;
import com.sbaltazar.pemu_cooking.ui.RecipeAdapter;

import java.util.List;

public class PemuCookingWidgetConfiguration extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;


    private RecipeViewModel mRecipeViewModel;
    private RecipeAdapter mRecipeAdapter;

    private ConfigurationPemuCookingWidgetBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(RESULT_CANCELED);

        mBinding = DataBindingUtil.setContentView(this, R.layout.configuration_pemu_cooking_widget);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.appwidget_select_recipe);
        }

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        mRecipeAdapter = new RecipeAdapter(this, this);

        // RecyclerView setup
        mBinding.rvRecipes.setHasFixedSize(true);
        mBinding.rvRecipes.setLayoutManager(new LinearLayoutManager(this));

        mBinding.rvRecipes.setAdapter(mRecipeAdapter);

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        if (!Utils.deviceHasInternetConnection(this)) {
            showNoInternetHelp(true);
            setLoadingVisibility(false);
        } else {
            setLoadingVisibility(true);
            mRecipeViewModel.getRecipesFromApi().observe(PemuCookingWidgetConfiguration.this, new Observer<List<Recipe>>() {
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
                if (Utils.deviceHasInternetConnection(PemuCookingWidgetConfiguration.this)) {
                    setLoadingVisibility(true);
                    showNoInternetHelp(false);
                    mRecipeViewModel.getRecipesFromApi().observe(PemuCookingWidgetConfiguration.this, new Observer<List<Recipe>>() {
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

        // Push widget update to surface with newly set prefix
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        PemuCookingWidget.updateAppWidget(this, appWidgetManager,
                mAppWidgetId, mRecipeAdapter.getRecipe(position));

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();

    }

    private void setLoadingVisibility(boolean visibility) {
        mBinding.pbLoading.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        mBinding.tvLoading.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }

    private void showNoInternetHelp(boolean state) {

        mBinding.tvNoInternet.setVisibility(state ? View.VISIBLE : View.INVISIBLE);
        mBinding.btnRetryConnection.setVisibility(state ? View.VISIBLE : View.INVISIBLE);
        mBinding.rvRecipes.setVisibility(state ? View.INVISIBLE : View.VISIBLE);
    }

}
