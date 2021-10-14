package com.roblebob.ud801_bakingapp.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.roblebob.ud801_bakingapp.R;

public class StepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
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
}
