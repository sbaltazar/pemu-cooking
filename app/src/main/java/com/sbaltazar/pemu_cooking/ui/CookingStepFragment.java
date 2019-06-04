package com.sbaltazar.pemu_cooking.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.sbaltazar.pemu_cooking.R;
import com.sbaltazar.pemu_cooking.data.models.CookingStep;
import com.sbaltazar.pemu_cooking.databinding.FragmentCookingStepBinding;

public class CookingStepFragment extends Fragment {

    private OnFragmentActionListener mListener;
    private CookingStep mCookingStep;
    private Context mContext;

    FragmentCookingStepBinding mBinding;

    private SimpleExoPlayer mExoplayer;

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
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_cooking_step, container, false);

        if (getArguments() == null) return null;

        mCookingStep = getArguments().getParcelable(RecipeDetailFragment.EXTRA_COOKING_STEP);

        if (mCookingStep == null) return null;

        mBinding.tvCompleteDescription.setText(mCookingStep.getCompleteDescription());

        mBinding.btnPrevStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPrevButtonClick(v, mCookingStep);
            }
        });

        mBinding.btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNextButtonClick(v, mCookingStep);
            }
        });


        if (!TextUtils.isEmpty(mCookingStep.getVideoUrl())) {
            Uri videoUri = Uri.parse(mCookingStep.getVideoUrl());
            initPlayer(videoUri, mContext);
        } else {
            mBinding.pvPlayer.setVisibility(View.GONE);
        }

        return mBinding.getRoot();
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
        if (context instanceof OnFragmentActionListener) {
            mListener = (OnFragmentActionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentActionListener");
        }

        mContext = context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        releasePlayer();
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
    public interface OnFragmentActionListener {
        void onPrevButtonClick(View view, CookingStep step);

        void onNextButtonClick(View view, CookingStep step);
    }

    private void initPlayer(Uri videoUri, @NonNull Context context) {
        if (mExoplayer == null) {

            //TrackSelector trackSelector = new DefaultTrackSelector();
            //LoadControl loadControl = new DefaultLoadControl();
            mExoplayer = ExoPlayerFactory.newSimpleInstance(context); //, trackSelector, loadControl);
            mBinding.pvPlayer.setPlayer(mExoplayer);

            DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory(
                    Util.getUserAgent(context, "pemu-cooking"));

            MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);

            mExoplayer.prepare(videoSource);
            mBinding.pvPlayer.setVisibility(View.VISIBLE);
            mExoplayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (mExoplayer != null) {
            mExoplayer.stop();
            mExoplayer.release();
            mExoplayer = null;
        }
    }
}
