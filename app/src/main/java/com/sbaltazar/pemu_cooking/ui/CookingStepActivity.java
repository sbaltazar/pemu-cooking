package com.sbaltazar.pemu_cooking.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sbaltazar.pemu_cooking.R;
import com.sbaltazar.pemu_cooking.data.models.CookingStep;
import com.sbaltazar.pemu_cooking.databinding.ActivityCookingStepBinding;

import java.util.List;

public class CookingStepActivity extends AppCompatActivity implements CookingStepFragment.OnFragmentActionListener {

    public static final String EXTRA_COOKING_STEP = "extra_cooking_step";
    public static final String EXTRA_COOKING_STEP_LIST_SIZE = "extra_cooking_step_list_size";

    ActivityCookingStepBinding mBinding;
    private List<CookingStep> mCookingStepList;

    private ActionBar mBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cooking_step);

        mBar = getSupportActionBar();

        if (getIntent() != null
                && getIntent().hasExtra(RecipeDetailActivity.EXTRA_COOKING_STEP_POSITION)
                && getIntent().hasExtra(RecipeDetailActivity.EXTRA_COOKING_STEP_LIST)) {

            mCookingStepList = getIntent().getParcelableArrayListExtra(RecipeDetailActivity.EXTRA_COOKING_STEP_LIST);

            int cookingStepIndex = getIntent().getIntExtra(RecipeDetailActivity.EXTRA_COOKING_STEP_POSITION, -1);

            if (cookingStepIndex == -1) {
                Toast.makeText(this, "Invalid cooking step selected", Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            CookingStep cookingStep = mCookingStepList.get(cookingStepIndex);

            // Up navigation
            if (mBar != null) {
                mBar.setDisplayHomeAsUpEnabled(true);
                mBar.setTitle(cookingStep.getShortDescription());
            }

            int cookingStepListSize = mCookingStepList.size();

            CookingStepFragment fragment = CookingStepFragment.newInstance(cookingStep, cookingStepListSize, false);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(mBinding.flCookingStepContainer.getId(), fragment)
                    .commit();

        }
    }

    @Override
    public void onPrevButtonClick(View view, CookingStep step) {

        if (step.getId() > 0) {
            CookingStep prevStep = mCookingStepList.get(step.getId() - 1);

            mBar.setTitle(prevStep.getShortDescription());

            CookingStepFragment fragment = CookingStepFragment.newInstance(prevStep, mCookingStepList.size(), false);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(mBinding.flCookingStepContainer.getId(), fragment)
                    .commit();
        }

    }

    @Override
    public void onNextButtonClick(View view, CookingStep step) {

        if (step.getId() < mCookingStepList.size() - 1) {
            CookingStep prevStep = mCookingStepList.get(step.getId() + 1);

            mBar.setTitle(prevStep.getShortDescription());

            CookingStepFragment fragment = CookingStepFragment.newInstance(prevStep, mCookingStepList.size(), false);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(mBinding.flCookingStepContainer.getId(), fragment)
                    .commit();
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }
}
