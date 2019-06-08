package com.sbaltazar.pemu_cooking;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.sbaltazar.pemu_cooking.data.models.Ingredient;
import com.sbaltazar.pemu_cooking.data.models.Recipe;

import java.util.Locale;

/**
 * Implementation of App Widget functionality.
 */
public class PemuCookingWidget extends AppWidgetProvider {

    private static Recipe mRecipe;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe recipe) {

        mRecipe = recipe;

        //CharSequence widgetText = context.getString(R.string.widget_name);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pemu_cooking_widget);

        if (recipe != null) {
            views.setTextViewText(R.id.appwidget_recipe_title, recipe.getName());

            StringBuilder ingredientString = new StringBuilder();

            for (Ingredient ingredient : recipe.getIngredients()) {
                String ingredientItem = String.format(Locale.getDefault(), "• %.0f %s %s\n",
                        ingredient.getQuantity(), ingredient.getMeasureType().getMeasure(), ingredient.getName());
                ingredientString.append(ingredientItem);
            }

            views.setTextViewText(R.id.appwidget_recipe_ingredients, ingredientString.toString());
        } else {
            views.setTextViewText(R.id.appwidget_recipe_title, context.getString(R.string.appwidget_recipe_title));
            views.setTextViewText(R.id.appwidget_recipe_ingredients, context.getString(R.string.appwidget_recipe_ingredients));
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, mRecipe);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

