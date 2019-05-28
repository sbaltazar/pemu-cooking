package com.sbaltazar.pemu_cooking.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.Toolbar;

import com.sbaltazar.pemu_cooking.R;
import com.sbaltazar.pemu_cooking.data.models.CookingStep;
import com.sbaltazar.pemu_cooking.databinding.ActivityCookingStepBinding;

public class CookingStepActivity extends AppCompatActivity {

    ActivityCookingStepBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cooking_step);

        if (getIntent() != null && getIntent().hasExtra(RecipeDetailFragment.EXTRA_COOKING_STEP)) {

            CookingStep cookingStep = getIntent().getParcelableExtra(RecipeDetailFragment.EXTRA_COOKING_STEP);

            CookingStepFragment fragment = CookingStepFragment.newInstance(cookingStep);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(mBinding.flCookingStepContainer.getId(), fragment)
                    .commit();

        }
    }
}
