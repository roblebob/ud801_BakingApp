package com.roblebob.ud801_bakingapp.ui;

import android.app.PictureInPictureParams;
import android.os.Build;
import android.os.Bundle;
import android.util.Rational;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.roblebob.ud801_bakingapp.R;

public class StepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if (savedInstanceState == null) {

            StepFragment stepFragment = new StepFragment();
            stepFragment.setRecipeName(getIntent().getStringExtra("recipeName"));
            stepFragment.setStepNumber(getIntent().getIntExtra("stepNumber", 0));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.step_container, stepFragment).commit();
        }
    }

    @Override
    protected void onUserLeaveHint() {

        enterPictureInPictureMode(
                new PictureInPictureParams
                        .Builder()
                        .setAspectRatio(new Rational(16,9))
                        .build()
        );

    }


}
