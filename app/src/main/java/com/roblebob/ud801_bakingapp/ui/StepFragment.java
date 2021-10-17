package com.roblebob.ud801_bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.ExtractorUtil;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;

import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


import com.google.android.exoplayer2.DefaultRenderersFactory;







import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.data.AppDatabase;
import com.roblebob.ud801_bakingapp.model.Step;
import com.roblebob.ud801_bakingapp.viewmodels.DetailViewModel;
import com.roblebob.ud801_bakingapp.viewmodels.DetailViewModelFactory;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class StepFragment extends Fragment implements Player.Listener{

    public StepFragment() {}

    String mRecipeName;
    public void setRecipeName(String recipeName) { mRecipeName = recipeName; }

    int mStepNumber;
    public void setStepNumber(int stepNumber) { mStepNumber = stepNumber; }


    List<Step> mStepList = new ArrayList<>();
    public void setStepList(List<Step> stepList) {

        mStepList = new ArrayList<>(stepList);
        Log.e(this.getClass().getSimpleName(), "size: " + mStepList.size());
    }

    TextView mShortDescriptionTv;
    TextView mDescriptionTv;



    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.fragment_step, container, false);

        if (savedInstanceState != null) {
            mRecipeName = savedInstanceState.getString("recipeName");
            mStepNumber = savedInstanceState.getInt("stepNumber");
        }

        if (mRecipeName != null) {
            AppDatabase appDatabase = AppDatabase.getInstance(this.getContext());
            DetailViewModelFactory detailViewModelFactory = new DetailViewModelFactory(appDatabase, mRecipeName);
            final DetailViewModel detailViewModel = new ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel.class);

            detailViewModel.getStepListLive().observe(getViewLifecycleOwner(), new Observer<List<Step>>() {
                @Override
                public void onChanged(List<Step> steps) {
                    detailViewModel.getStepListLive().removeObserver(this);
                    setStepList(steps);
                    if (mStepList.size() > 0) {
                        setup(mStepList.get(mStepNumber));
                    } else {
                        Log.e(this.getClass().getSimpleName(), "size is 0 --->  ERROR");
                    }
                }
            });
        }



        ((View) rootview.findViewById(R.id.fragment_step_navigation_left)).setOnClickListener(view -> {
            if (mStepNumber > 0) {
                mStepNumber = mStepNumber - 1;
                setup(mStepList.get(mStepNumber));
            }
        });

        ((View) rootview.findViewById(R.id.fragment_step_navigation_right)).setOnClickListener(view -> {
            if (mStepNumber < mStepList.size() - 1) {
                mStepNumber = mStepNumber + 1;
                setup(mStepList.get(mStepNumber));
            }
        });


        mShortDescriptionTv = rootview.findViewById(R.id.fragment_step_shortDescription);
        mDescriptionTv = rootview.findViewById(R.id.fragment_step_description);


        mExoPlayerView = (PlayerView) rootview.findViewById(R.id.fragment_step_video);

        initializedMediaSession();

        return rootview;
    }



    void setup(Step step) {
        mShortDescriptionTv.setText(step.getShortDescription());
        mDescriptionTv.setText(step.getDescription());
        initializePlayer( Uri.parse( !step.getVideoURL().equals("") ? step.getVideoURL()  : step.getThumbnailURL() ));
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


        if (mExoPlayer == null) {

            mExoPlayer = new SimpleExoPlayer
                    .Builder(this.getContext(), new DefaultRenderersFactory( this.getContext()))
                    .setTrackSelector( (TrackSelector) new DefaultTrackSelector( this.getContext()))
                    .setLoadControl( (LoadControl) new DefaultLoadControl())
                    .build();
            mExoPlayerView.setPlayer( mExoPlayer);
            mExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent( this.getContext(), getString(R.string.app_name));
            MediaSource mediaSource = new ProgressiveMediaSource
                    .Factory( new DefaultDataSourceFactory( this.getContext(), userAgent ))
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

        mMediaSession = new MediaSessionCompat(this.getContext(), this.getClass().getSimpleName());

        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS );

        mMediaSession.setMediaButtonReceiver( null);

        mStateBuilder = new PlaybackStateCompat.Builder().setActions(
                PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PAUSE | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS );

        mMediaSession.setPlaybackState(mStateBuilder.build());

        mMediaSession.setCallback( new MySessionCallBack());

        mMediaSession.setActive(true);
    }

    private class MySessionCallBack extends MediaSessionCompat.Callback {
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


