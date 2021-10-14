package com.roblebob.ud801_bakingapp.ui;

import android.os.Bundle;
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

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.data.AppDatabase;
import com.roblebob.ud801_bakingapp.model.Step;
import com.roblebob.ud801_bakingapp.viewmodels.DetailViewModel;
import com.roblebob.ud801_bakingapp.viewmodels.DetailViewModelFactory;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class StepFragment extends Fragment {

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
            mStepNumber = savedInstanceState.getInt("stepNumer");
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



        return rootview;
    }

    void setup(Step step) {
        mShortDescriptionTv.setText(step.getShortDescription());
        mDescriptionTv.setText(step.getDescription());
    }






    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putString("recipeName", mRecipeName);
        currentState.putInt("stepNumber", mStepNumber);
    }
}
