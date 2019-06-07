package com.sbaltazar.pemu_cooking.ui;

import android.content.Context;
import android.content.res.Configuration;
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
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
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

    static CookingStepFragment newInstance(@NonNull CookingStep cookingStep, int cookingStepListSize, boolean isTwoPane) {
        CookingStepFragment fragment = new CookingStepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CookingStepActivity.EXTRA_COOKING_STEP, cookingStep);
        bundle.putInt(CookingStepActivity.EXTRA_COOKING_STEP_LIST_SIZE, cookingStepListSize);
        bundle.putBoolean(RecipeDetailActivity.EXTRA_IS_TWO_PANE, isTwoPane);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_cooking_step, container, false);

        if (getArguments() == null) return null;

        mCookingStep = getArguments().getParcelable(CookingStepActivity.EXTRA_COOKING_STEP);

        boolean isTwoPane = getArguments().getBoolean(RecipeDetailActivity.EXTRA_IS_TWO_PANE);

        int cookingStepListSize = getArguments()
                .getInt(CookingStepActivity.EXTRA_COOKING_STEP_LIST_SIZE);

        if (mCookingStep == null) return null;

        int orientation = getResources().getConfiguration().orientation;
        if (isTwoPane || orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (mCookingStep.getId() == 0) mBinding.btnPrevStep.setVisibility(View.INVISIBLE);

            if (mCookingStep.getId() >= cookingStepListSize - 1)
                mBinding.btnNextStep.setVisibility(View.INVISIBLE);

            mBinding.tvShortDescription.setText(mCookingStep.getShortDescription());
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
        }

        if (!TextUtils.isEmpty(mCookingStep.getVideoUrl())) {
            Uri videoUri = Uri.parse(mCookingStep.getVideoUrl());
            initPlayer(videoUri, mContext);
        } else {
            mBinding.pvPlayer.setVisibility(View.GONE);
        }

        return mBinding.getRoot();
    }

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
     * Interface to be implemented on host activity
     */
    public interface OnFragmentActionListener {
        void onPrevButtonClick(View view, CookingStep step);

        void onNextButtonClick(View view, CookingStep step);
    }

    private void initPlayer(Uri videoUri, @NonNull Context context) {
        if (mExoplayer == null) {

            mExoplayer = ExoPlayerFactory.newSimpleInstance(context);
            mBinding.pvPlayer.setPlayer(mExoplayer);

            DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory(
                    Util.getUserAgent(context, "pemu-cooking"));

            MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);

            mExoplayer.prepare(videoSource);
            mBinding.pvPlayer.setVisibility(View.VISIBLE);

            mExoplayer.addListener(new Player.EventListener() {
                @Override
                public void onPlayerError(ExoPlaybackException error) {
                    Toast.makeText(CookingStepFragment.this.mContext, "Error when loading the video", Toast.LENGTH_SHORT).show();
                }
            });
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
