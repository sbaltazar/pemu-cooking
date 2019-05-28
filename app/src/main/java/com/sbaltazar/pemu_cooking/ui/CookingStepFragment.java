package com.sbaltazar.pemu_cooking.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sbaltazar.pemu_cooking.R;
import com.sbaltazar.pemu_cooking.data.models.CookingStep;
import com.sbaltazar.pemu_cooking.databinding.FragmentCookingStepBinding;

public class CookingStepFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public CookingStepFragment() {
    }

    static CookingStepFragment newInstance(@NonNull CookingStep cookingStep) {
        CookingStepFragment fragment = new CookingStepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeDetailFragment.EXTRA_COOKING_STEP, cookingStep);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentCookingStepBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_cooking_step, container, false);

        if (getArguments() == null) return null;

        CookingStep cookingStep = getArguments().getParcelable(RecipeDetailFragment.EXTRA_COOKING_STEP);

        if (cookingStep == null) return null;

        Toast.makeText(getContext(), cookingStep.getCompleteDescription(), Toast.LENGTH_SHORT).show();

        return binding.getRoot();
    }

    // TODO: Rename method, update argument and hook method into UI event
    //public void onButtonPressed(Uri uri) {
    //    if (mListener != null) {
    //        mListener.onFragmentInteraction(uri);
    //    }
    //}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString()
              //      + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
