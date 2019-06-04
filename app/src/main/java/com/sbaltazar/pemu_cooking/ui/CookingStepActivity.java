package com.sbaltazar.pemu_cooking.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.sbaltazar.pemu_cooking.R;
import com.sbaltazar.pemu_cooking.data.models.CookingStep;
import com.sbaltazar.pemu_cooking.databinding.ActivityCookingStepBinding;

import java.util.List;

import timber.log.Timber;

public class CookingStepActivity extends AppCompatActivity implements CookingStepFragment.OnFragmentActionListener {

    ActivityCookingStepBinding mBinding;
    private CookingStep mCookingStep;
    private List<CookingStep> mCookingStepList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cooking_step);

        ActionBar bar = getSupportActionBar();

        if (getIntent() != null && getIntent().hasExtra(RecipeDetailFragment.EXTRA_COOKING_STEP)) {

            mCookingStep = getIntent().getParcelableExtra(RecipeDetailFragment.EXTRA_COOKING_STEP);

            // Up navigation
            if (bar != null) {
                bar.setDisplayHomeAsUpEnabled(true);
                bar.setTitle(mCookingStep.getShortDescription());
            }

            CookingStepFragment fragment = CookingStepFragment.newInstance(mCookingStep);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(mBinding.flCookingStepContainer.getId(), fragment)
                    .commit();

            // if it has the cooking steps for the recipe
            if (getIntent().hasExtra(RecipeDetailFragment.EXTRA_COOKING_STEP_LIST)) {
                mCookingStepList = getIntent().getParcelableArrayListExtra(RecipeDetailFragment.EXTRA_COOKING_STEP_LIST);
            }

        }
    }

    @Override
    public void onPrevButtonClick(View view, CookingStep step) {

        if (step.getId() > 0) {
            CookingStep prevStep = mCookingStepList.get(step.getId() - 1);

            CookingStepFragment fragment = CookingStepFragment.newInstance(prevStep);

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

            CookingStepFragment fragment = CookingStepFragment.newInstance(prevStep);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(mBinding.flCookingStepContainer.getId(), fragment)
                    .commit();
        }
    }
}
