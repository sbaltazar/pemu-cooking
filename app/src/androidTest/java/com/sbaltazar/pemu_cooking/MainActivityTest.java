package com.sbaltazar.pemu_cooking;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.sbaltazar.pemu_cooking.ui.MainActivity;
import com.sbaltazar.pemu_cooking.ui.RecipeDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public IntentsTestRule<RecipeDetailActivity> mIntentTestRule = new IntentsTestRule<>(
            RecipeDetailActivity.class);


    /**
     * Checks if the recipe, when clicked, opens the RecipeDetailActivity that contains the
     * fragment RecipeDetailsFragment in the FrameLayout container with the specified id
     */
    @Test
    public void clickRecipeItem_ShowRecipeDetail() {

        // Click on the first item of the recipes list
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.fl_recipe_detail_container)).check(matches(isDisplayed()));
    }

    /**
     * Checks if the recipe, when clicked, sends an Intent with an extra "EXTRA_RECIPE"
     */
    @Test
    public void clickRecipeItem_SendIntent() {

        // Click on the first item of the recipes list
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(allOf(hasExtraWithKey(MainActivity.EXTRA_RECIPE)));

    }


}
