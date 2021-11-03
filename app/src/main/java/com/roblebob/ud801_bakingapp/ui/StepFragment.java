package com.roblebob.ud801_bakingapp.ui;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.DefaultRenderersFactory;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.repository.AppConnectivity;
import com.roblebob.ud801_bakingapp.model.Step;
import com.roblebob.ud801_bakingapp.viewmodels.DetailViewModel;
import com.roblebob.ud801_bakingapp.viewmodels.DetailViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class StepFragment extends Fragment implements Player.Listener{

    public StepFragment() { }

    String mRecipeName;
    public void setRecipeName(String recipeName) { mRecipeName = recipeName; }

    int mStepNumber;
    public void setStepNumber(int stepNumber) { mStepNumber = stepNumber; }

    List<Step> mStepList = new ArrayList<>();
    public void setStepList(List<Step> stepList) { mStepList = new ArrayList<>(stepList); }

    TextView mShortDescriptionTv;
    TextView mDescriptionTv;

    boolean mIsConnected;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.fragment_step, container, false);

        if (savedInstanceState != null) {
            mRecipeName = savedInstanceState.getString("recipeName");
            mStepNumber = savedInstanceState.getInt("stepNumber");
        }

        new AppConnectivity( this.getContext()) .observe( getViewLifecycleOwner(), aBoolean -> mIsConnected = aBoolean);



        if (mRecipeName != null) {
            DetailViewModelFactory detailViewModelFactory = new DetailViewModelFactory(this.getContext(), mRecipeName);
            final DetailViewModel detailViewModel = new ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel.class);

            detailViewModel.getStepListLive().observe(getViewLifecycleOwner(), new Observer<List<Step>>() {
                @Override
                public void onChanged(List<Step> steps) {
                    //detailViewModel.getStepListLive().removeObserver(this);
                    setStepList(steps);
                    if (mStepList.size() > 0) {
                        setup(mStepList.get(mStepNumber));
                    }
                }
            });
        }


        // handles navigation by arrows

        rootview.findViewById(R.id.fragment_step_navigation_left).setOnClickListener(view -> {
            if (mStepNumber > 0) {
                mStepNumber = mStepNumber - 1;
                setup(mStepList.get(mStepNumber));
            }
        });

        rootview.findViewById(R.id.fragment_step_navigation_right).setOnClickListener(view -> {
            if (mStepNumber < mStepList.size() - 1) {
                mStepNumber = mStepNumber + 1;
                setup(mStepList.get(mStepNumber));
            }
        });





        mShortDescriptionTv = rootview.findViewById(R.id.fragment_step_short_description);
        mDescriptionTv = rootview.findViewById(R.id.fragment_step_description);

        mBackwardArrow = rootview.findViewById(R.id.fragment_step_backward_arrow);
        mForwardArrow = rootview.findViewById(R.id.fragment_step_forward_arrow);

        mExoPlayerView = rootview.findViewById(R.id.fragment_step_video);

        initializedMediaSession();

        return rootview;
    }


    ImageView mBackwardArrow;
    ImageView mForwardArrow;


    /**
     * sets up the views with the corresponding step data
     * @param step
     */
    void setup(Step step) {
        if (step.getStepNumber() == 0) {
            mBackwardArrow.setColorFilter(this.requireContext().getColor(R.color.divider));
            mForwardArrow.setColorFilter(this.requireContext().getColor(R.color.secondary_text));
        } else if (step.getStepNumber() == mStepList.size() - 1) {
            mBackwardArrow.setColorFilter(this.requireContext().getColor(R.color.secondary_text));
            mForwardArrow.setColorFilter(this.requireContext().getColor(R.color.divider));
        } else {
            mBackwardArrow.setColorFilter(this.requireContext().getColor(R.color.secondary_text));
            mForwardArrow.setColorFilter(this.requireContext().getColor(R.color.secondary_text));
        }

        mShortDescriptionTv.setText(step.getShortDescription());
        mDescriptionTv.setText( (step.getDescription().equals(step.getShortDescription())) ? "" : step.getDescription());

        String videoUrl = !step.getVideoURL().equals("") ? step.getVideoURL()  : step.getThumbnailURL();

        if (videoUrl == null || videoUrl.equals("") || !mIsConnected) {
            mExoPlayerView.setVisibility(View.INVISIBLE);
        } else {
            mExoPlayerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(videoUrl));
        }
    }


    /**
     *  ExoPlayer
     */

    PlayerView mExoPlayerView;
    SimpleExoPlayer mExoPlayer;

    void initializePlayer(Uri uri) {

        if (mExoPlayer != null) {
            releasePlayer();
        }

        // needs to be null, to go on

        if (mExoPlayer == null) {

            mExoPlayer = new SimpleExoPlayer
                    .Builder(this.requireContext(), new DefaultRenderersFactory( this.getContext()))
                    .setTrackSelector(new DefaultTrackSelector( this.getContext()))
                    .setLoadControl(new DefaultLoadControl())
                    .build();
            mExoPlayerView.setPlayer( mExoPlayer);
            mExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent( this.requireContext(), getString(R.string.app_name));
            MediaSource mediaSource = new ProgressiveMediaSource
                    .Factory( new DefaultDataSourceFactory( this.requireContext(), userAgent ))
                    .createMediaSource(MediaItem.fromUri(uri));
            mExoPlayer.setMediaSource(mediaSource);
            mExoPlayer.prepare();
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }


    @Override
    public void onIsPlayingChanged(boolean isPlaying) {
        if (isPlaying) {
            Log.e(this.getClass().getSimpleName(), "PLAYING");
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, mExoPlayer.getCurrentPosition(), 1f);
        } else if (mExoPlayer.getPlaybackState() == Player.STATE_READY) {
            Log.e(this.getClass().getSimpleName(), "PAUSED");
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState( mStateBuilder.build());
    }








    /**
     *  MediaSession
     */

    MediaSessionCompat mMediaSession;
    PlaybackStateCompat.Builder mStateBuilder;

    public void initializedMediaSession() {

        mMediaSession = new MediaSessionCompat(this.requireContext(), this.getClass().getSimpleName());

        //mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS );

        mMediaSession.setMediaButtonReceiver( null);

        mStateBuilder = new PlaybackStateCompat.Builder().setActions(
                PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PAUSE | PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS );

        mMediaSession.setPlaybackState(mStateBuilder.build());

        mMediaSession.setCallback(new MySessionCallBack());

        mMediaSession.setActive(true);
    }

    private static class MySessionCallBack extends MediaSessionCompat.Callback {
        @Override public void onPlay() {}
        @Override public void onPause() {}
        @Override public void onStop() {}
    }










    @Override
    public void onDestroyView() {
        mMediaSession.setActive(false);
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putString("recipeName", mRecipeName);
        currentState.putInt("stepNumber", mStepNumber);
    }
}


