package com.roblebob.ud801_bakingapp.ui;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
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
    public static final String TAG = StepFragment.class.getSimpleName();
    public static final String RECIPE_NAME = "com.roblebob.ud801_bakingapp.ui.recipe_name";
    public static final String STEP_NUMBER = "com.roblebob.ud801_bakingapp.ui.step_number";
    public static final String EXOPLAYER_CURRENT_POSITION = "com.roblebob.ud801_bakingapp.ui.exoplayer_current_position";
    private static final String EXOPLAYER_PLAY_WHEN_READY = "com.roblebob.ud801_bakingapp.ui.exoplayer_play_when_ready";


    String mRecipeName;
    int mStepNumber;
    List<Step> mStepList = new ArrayList<>();

    boolean mIsConnected;
    public boolean mIsInPictureInPictureMode;

    TextView mShortDescriptionTv;
    TextView mDescriptionTv;
    ImageView mBackwardArrow;
    ImageView mForwardArrow;
    Group uiSet;
    FrameLayout mVideoFrame;

    PlayerView mExoPlayerView;
    ExoPlayer mExoPlayer;
    long mExoPlayerCurrentPosition;
    boolean mExoPlayerPlayWhenReady = true;

    MediaSessionCompat mMediaSession;
    PlaybackStateCompat.Builder mStateBuilder;


    // Mandatory empty constructor
    public StepFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            mRecipeName = StepFragmentArgs.fromBundle(getArguments()).getRecipeName();
            mStepNumber = StepFragmentArgs.fromBundle(getArguments()).getStepNumber();
        } else {
            mRecipeName = savedInstanceState.getString(RECIPE_NAME);
            mStepNumber = savedInstanceState.getInt(STEP_NUMBER);
            mExoPlayerCurrentPosition = savedInstanceState.getLong(EXOPLAYER_CURRENT_POSITION);
            mExoPlayerPlayWhenReady = savedInstanceState.getBoolean(EXOPLAYER_PLAY_WHEN_READY);
            mIsInPictureInPictureMode = savedInstanceState.getBoolean("is_in_picture_in_picture_mode");
        }

        Log.e(TAG,">>>>>>>> recipeName: " + mRecipeName + " ,   stepNumber: " + mStepNumber);

        final View rootview = inflater.inflate(R.layout.fragment_step, container, false);

        mShortDescriptionTv = rootview.findViewById(R.id.fragment_step_short_description);
        mDescriptionTv = rootview.findViewById(R.id.fragment_step_description);
        mBackwardArrow = rootview.findViewById(R.id.fragment_step_backward_arrow);
        mForwardArrow = rootview.findViewById(R.id.fragment_step_forward_arrow);
        mExoPlayerView = rootview.findViewById(R.id.fragment_step_video);
        uiSet = rootview.findViewById(R.id.fragment_step_group);
        mVideoFrame = rootview.findViewById(R.id.fragment_step_video_frame);

        new AppConnectivity( this.getContext()) .observe( getViewLifecycleOwner(), aBoolean -> mIsConnected = aBoolean);

        if (mRecipeName != null) {
            DetailViewModelFactory detailViewModelFactory = new DetailViewModelFactory(this.getContext(), mRecipeName);
            final DetailViewModel detailViewModel = new ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel.class);

            detailViewModel.getStepListLive().observe(getViewLifecycleOwner(), steps -> {
                mStepList = new ArrayList<>(steps);
                setup();
            });
        }


        // handles navigation by arrows

        rootview.findViewById(R.id.fragment_step_navigation_left).setOnClickListener(view -> {
            if (mStepNumber > 0) {
                mStepNumber -= 1;
                mExoPlayerCurrentPosition = 0;
                setup();
            }
        });
        rootview.findViewById(R.id.fragment_step_navigation_right).setOnClickListener(view -> {
            if (mStepNumber < mStepList.size() - 1) {
                mStepNumber += 1;
                mExoPlayerCurrentPosition = 0;
                setup();
            }
        });

        initializedMediaSession();

        return rootview;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
        Log.e(TAG, "----> onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24) || mExoPlayer == null) {
            initializePlayer();
        }
        Log.e(TAG, "----> onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
        Log.e(TAG, "----> onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
        Log.e(TAG, "----> onStop()");
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putString(RECIPE_NAME, mRecipeName);
        currentState.putInt(STEP_NUMBER, mStepNumber);
        currentState.putLong(EXOPLAYER_CURRENT_POSITION, mExoPlayerCurrentPosition);
        currentState.putBoolean(EXOPLAYER_PLAY_WHEN_READY, mExoPlayerPlayWhenReady);
        currentState.putBoolean("is_in_picture_in_picture_mode", mIsInPictureInPictureMode);
        Log.e(TAG, "----> onSaveInstanceState()");
    }


    @Override
    public void onDestroyView() {
        mMediaSession.setActive(false);
        super.onDestroyView();
        releasePlayer();
        Log.e(TAG, "----> onDestroyView()");
    }


    /**
     * sets up the views with the corresponding step data
     */
    void setup() {

        if (mStepNumber >= mStepList.size()) return;
        Step step = mStepList.get(mStepNumber);

        mShortDescriptionTv.setText(step.getShortDescription());
        mDescriptionTv.setText( (step.getDescription().equals(step.getShortDescription())) ? "" : step.getDescription());

        if (step.getStepNumber() == 0) {
            mBackwardArrow.setColorFilter(this.requireContext().getColor(R.color.nav_arrow_off));
            mForwardArrow.setColorFilter(this.requireContext().getColor(R.color.nav_arrow_on));
        } else if (step.getStepNumber() == mStepList.size() - 1) {
            mBackwardArrow.setColorFilter(this.requireContext().getColor(R.color.nav_arrow_on));
            mForwardArrow.setColorFilter(this.requireContext().getColor(R.color.nav_arrow_off));
        } else {
            mBackwardArrow.setColorFilter(this.requireContext().getColor(R.color.nav_arrow_on));
            mForwardArrow.setColorFilter(this.requireContext().getColor(R.color.nav_arrow_on));
        }

        initializePlayer();
    }






    /**
     *  ExoPlayer
     */

    void initializePlayer() {
        releasePlayer();

        if (mStepNumber >= mStepList.size()) return;
        Step step = mStepList.get(mStepNumber);
        String videoUrl = (!step.getVideoURL().equals(""))  ?  step.getVideoURL()  :  step.getThumbnailURL();

        // ExoPlayer needs to be null, to go on
        if (mExoPlayer == null && mIsConnected && !videoUrl.equals("")) {

            mExoPlayerView.setVisibility(View.VISIBLE);
            if (mVideoFrame != null) {
                mVideoFrame.setVisibility(View.VISIBLE);
            }

            mExoPlayer = new ExoPlayer
                    .Builder(this.requireContext(), new DefaultRenderersFactory( this.getContext()))
                    .setTrackSelector(new DefaultTrackSelector( this.getContext()))
                    .setLoadControl(new DefaultLoadControl())
                    .build();
            mExoPlayerView.setPlayer( mExoPlayer);
            mExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent( this.requireContext(), getString(R.string.app_name));
            MediaSource mediaSource = new ProgressiveMediaSource
                    .Factory( new DefaultDataSourceFactory( this.requireContext(), userAgent ))
                    .createMediaSource(MediaItem.fromUri(Uri.parse(videoUrl)));
            mExoPlayer.setMediaSource(mediaSource);
            mExoPlayer.prepare();
            mExoPlayer.setPlayWhenReady(mExoPlayerPlayWhenReady);
            mExoPlayer.seekTo(mExoPlayerCurrentPosition);

        } else {
            mExoPlayerView.setVisibility(View.INVISIBLE);
            if (mVideoFrame != null) {
                mVideoFrame.setVisibility(View.GONE);
            }
        }

    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayerCurrentPosition = mExoPlayer.getCurrentPosition();
            mExoPlayerPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

















    /**
     *  MediaSession
     */


    @Override
    public void onIsPlayingChanged(boolean isPlaying) {
        if (isPlaying) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, mExoPlayer.getCurrentPosition(), 1f);
        } else if (mExoPlayer.getPlaybackState() == Player.STATE_READY) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState( mStateBuilder.build());
        Log.e(TAG, "----> onIsPlayerChanged( " + isPlaying +" )");
    }


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

    private class MySessionCallBack extends MediaSessionCompat.Callback {
        @Override public void onPlay() { mExoPlayer.setPlayWhenReady(true);}
        @Override public void onPause() { mExoPlayer.setPlayWhenReady(false);}
        @Override public void onStop() { mExoPlayer.seekTo(0); }
    }












    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {

        super.onPictureInPictureModeChanged(isInPictureInPictureMode);

        Log.e(TAG, "------> onPictureInPictureModeChanged( " + isInPictureInPictureMode + " )");

        mIsInPictureInPictureMode = isInPictureInPictureMode;


        mExoPlayerView.setUseController(!isInPictureInPictureMode);

        if (isInPictureInPictureMode) {
            if (uiSet != null) uiSet.setVisibility(View.GONE);
            mExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
            //mExoPlayerView
        } else {
            if (uiSet != null) uiSet.setVisibility(View.VISIBLE);
        }


    }


    public boolean hasVideoPlayable() {
        if (!mIsConnected)  return false;
        if (mStepNumber >= mStepList.size()) return false;
        Step step = mStepList.get( mStepNumber);
        String videoUrl = (!step.getVideoURL().equals(""))  ?  step.getVideoURL()  :  step.getThumbnailURL();
        return !videoUrl.equals("");
    }



}


