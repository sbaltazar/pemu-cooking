package com.sbaltazar.pemu_cooking.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.sbaltazar.pemu_cooking.R;
import com.sbaltazar.pemu_cooking.data.models.CookingStep;
import com.sbaltazar.pemu_cooking.data.models.Recipe;
import com.sbaltazar.pemu_cooking.databinding.ActivityRecipeDetailBinding;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity implements CookingStepAdapter.OnCookingStepClickListener,
        CookingStepFragment.OnFragmentActionListener {

    public static final String EXTRA_COOKING_STEP_POSITION = "extra_cooking_step_position";
    public static final String EXTRA_COOKING_STEP_LIST = "extra_cooking_step_list";
    public static final String EXTRA_IS_TWO_PANE = "extra_cooking_is_two_pane";


    ActivityRecipeDetailBinding mBinding;

    private boolean mIsTwoPane;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);

        // Check if the screen allows two pane UI
        mIsTwoPane = mBinding.clTwoPaneContainer != null &&
                mBinding.clTwoPaneContainer.getVisibility() == View.VISIBLE;

        ActionBar bar = getSupportActionBar();


        if (getIntent() != null && getIntent().hasExtra(MainActivity.EXTRA_RECIPE)) {
            mRecipe = getIntent().getParcelableExtra(MainActivity.EXTRA_RECIPE);
        } else {
            Toast.makeText(this, "Error, no recipe found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Up navigation
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setTitle(mRecipe.getName());
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipeDetailFragment recipeDetailFragment = RecipeDetailFragment.newInstance(mRecipe);
        fragmentManager.beginTransaction().add(mBinding.flRecipeDetailContainer.getId(), recipeDetailFragment).commit();

        if (mIsTwoPane) {
            CookingStepFragment cookingStepFragment = CookingStepFragment
                    .newInstance(mRecipe.getCookingSteps().get(0), mRecipe.getCookingSteps().size(), mIsTwoPane);
            fragmentManager
                    .beginTransaction()
                    .add(mBinding.flCookingStepContainer.getId(), cookingStepFragment)
                    .commit();

        }

    }

    @Override
    public void onCookingStepClick(View view, int position) {

        if (!mIsTwoPane) {
            Intent intent = new Intent(this, CookingStepActivity.class);
            intent.putExtra(EXTRA_COOKING_STEP_POSITION, position);
            intent.putParcelableArrayListExtra(EXTRA_COOKING_STEP_LIST,
                    new ArrayList<Parcelable>(mRecipe.getCookingSteps()));

            startActivity(intent);
        } else {
            CookingStepFragment cookingStepFragment = CookingStepFragment
                    .newInstance(mRecipe.getCookingSteps().get(position), mRecipe.getCookingSteps().size(), mIsTwoPane);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(mBinding.flCookingStepContainer.getId(), cookingStepFragment)
                    .commit();
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof RecipeDetailFragment) {
            RecipeDetailFragment recipeDetailFragment = (RecipeDetailFragment) fragment;
            recipeDetailFragment.setOnCookingStepClickListener(this);
        }
    }

    @Override
    public void onPrevButtonClick(View view, CookingStep step) {
        if (step.getId() > 0) {
            CookingStep prevStep = mRecipe.getCookingSteps().get(step.getId() - 1);

            CookingStepFragment fragment = CookingStepFragment.newInstance(prevStep, mRecipe.getCookingSteps().size(), mIsTwoPane);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(mBinding.flCookingStepContainer.getId(), fragment)
                    .commit();
        }
    }

    @Override
    public void onNextButtonClick(View view, CookingStep step) {
        if (step.getId() >= 0) {
            CookingStep prevStep = mRecipe.getCookingSteps().get(step.getId() + 1);

            CookingStepFragment fragment = CookingStepFragment.newInstance(prevStep, mRecipe.getCookingSteps().size(), mIsTwoPane);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(mBinding.flCookingStepContainer.getId(), fragment)
                    .commit();
        }
    }
}
