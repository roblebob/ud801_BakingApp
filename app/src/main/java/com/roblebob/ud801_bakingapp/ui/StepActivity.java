package com.roblebob.ud801_bakingapp.ui;

import android.app.PictureInPictureParams;
import android.os.Bundle;
import android.util.Rational;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.roblebob.ud801_bakingapp.R;

public class StepActivity extends AppCompatActivity {

    String mRecipeName;
    StepFragment mStepFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if (savedInstanceState == null) {

            mRecipeName = getIntent().getStringExtra("recipeName");

            mStepFragment = new StepFragment();
            mStepFragment.setRecipeName( mRecipeName);
            mStepFragment.setStepNumber(getIntent().getIntExtra("stepNumber", 0));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.step_container, mStepFragment).commit();
        } else {
            mRecipeName = savedInstanceState.getString("recipeName");
        }

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (mRecipeName != null) {
            outState.putString("recipeName", mRecipeName);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onUserLeaveHint() {

        if (mStepFragment.hasVideoPlayable()) {

            enterPictureInPictureMode(
                    new PictureInPictureParams
                            .Builder()
                            .setAspectRatio(new Rational(16, 9))
                            .build()
            );
        } else {

            super.onUserLeaveHint();
        }
            
    }
}
