package com.sbaltazar.pemu_cooking.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sbaltazar.pemu_cooking.data.models.CookingStep;
import com.sbaltazar.pemu_cooking.databinding.ItemCookingStepBinding;

import java.util.List;

public class CookingStepAdapter extends RecyclerView.Adapter<CookingStepAdapter.CookingStepViewHolder> {

    final private LayoutInflater mInflater;
    private List<CookingStep> mCookingSteps;

    final private OnCookingStepClickListener mCookingStepClickListener;

    public interface OnCookingStepClickListener {
        void onCookingStepClick(View view, int position);
    }

    CookingStepAdapter(Context context, OnCookingStepClickListener listener) {
        mInflater = LayoutInflater.from(context);
        mCookingStepClickListener = listener;
    }

    @NonNull
    @Override
    public CookingStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemCookingStepBinding binding = ItemCookingStepBinding.inflate(mInflater, parent, false);

        return new CookingStepViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CookingStepViewHolder holder, int position) {
        if (mCookingSteps != null) {
            holder.bind(mCookingSteps.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (mCookingSteps != null) return mCookingSteps.size();
        return 0;
    }

    void setCookingSteps(List<CookingStep> cookingSteps) {
        mCookingSteps = cookingSteps;
        notifyDataSetChanged();
    }

    CookingStep getCookingStep(int position) {
        if (mCookingSteps != null) return mCookingSteps.get(position);
        return null;
    }

    List<CookingStep> getAllCookingSteps() {
        return mCookingSteps;
    }

    class CookingStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ItemCookingStepBinding mCookingStepBinding;

        CookingStepViewHolder(@NonNull ItemCookingStepBinding binding) {
            super(binding.getRoot());
            mCookingStepBinding = binding;
            mCookingStepBinding.getRoot().setOnClickListener(this);
        }

        void bind(CookingStep cookingStep) {
            mCookingStepBinding.tvCookingStepName.setText(cookingStep.getShortDescription());
        }

        @Override
        public void onClick(View v) {
            mCookingStepClickListener.onCookingStepClick(v, getAdapterPosition());
        }
    }
}
