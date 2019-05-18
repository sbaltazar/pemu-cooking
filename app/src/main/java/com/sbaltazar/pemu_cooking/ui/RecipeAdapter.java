package com.sbaltazar.pemu_cooking.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sbaltazar.pemu_cooking.data.models.Recipe;
import com.sbaltazar.pemu_cooking.databinding.ItemRecipeBinding;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final LayoutInflater mInflater;
    private List<Recipe> mRecipes;

    public RecipeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemRecipeBinding binding = ItemRecipeBinding.inflate(mInflater, parent, false);

        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        if (mRecipes != null) {
            holder.bind(mRecipes.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (mRecipes != null) return mRecipes.size();
        return 0;
    }

    public void setRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        private final ItemRecipeBinding mRecipeBinding;

        RecipeViewHolder(@NonNull ItemRecipeBinding binding) {
            super(binding.getRoot());
            mRecipeBinding = binding;
        }

        void bind(Recipe recipe) {
            mRecipeBinding.tvRecipeName.setText(recipe.getName());
            mRecipeBinding.tvRecipeServings.setText(String.format("%s servings", recipe.getServings()));

            mRecipeBinding.executePendingBindings();
        }
    }
}
