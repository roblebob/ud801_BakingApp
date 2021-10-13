package com.roblebob.ud801_bakingapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.data.AppDatabase;
import com.roblebob.ud801_bakingapp.model.Step;
import com.roblebob.ud801_bakingapp.viewmodels.DetailViewModel;
import com.roblebob.ud801_bakingapp.viewmodels.DetailViewModelFactory;
import com.roblebob.ud801_bakingapp.viewmodels.MasterViewModel;

import java.util.List;

public class RecipeFragment extends Fragment implements StepListRVAdapter.ItemClickListener{

    StepListRVAdapter mStepListRVAdapter;

    // Mandatory empty constructor
    public RecipeFragment() {
        Log.e(this.getClass().getSimpleName(), "was created");
    }

    String mRecipeName;
    public void setRecipeName(String recipeName) {
        mRecipeName = recipeName;
        Log.e(this.getClass().getSimpleName(), "recipeName: " + mRecipeName + " was set");
    }
    int mServings;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.fragment_recipe, container, false);

        if (savedInstanceState != null) { mRecipeName = savedInstanceState.getString("recipeName"); }

        Log.e(this.getClass().getSimpleName(), mRecipeName);

        RecyclerView stepListRV = rootview.findViewById(R.id.fragment_recipe_steps_rv);
        RecyclerView.LayoutManager stepListRVLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        stepListRV.setLayoutManager(stepListRVLayoutManager);
        mStepListRVAdapter = new StepListRVAdapter(this);
        stepListRV.setAdapter(mStepListRVAdapter);


        if (mRecipeName != null) {
            AppDatabase appDatabase = AppDatabase.getInstance(this.getContext());
            DetailViewModelFactory detailViewModelFactory = new DetailViewModelFactory(appDatabase, mRecipeName);
            final DetailViewModel detailViewModel = new ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel.class);

            detailViewModel.getStepListLive().observe(getViewLifecycleOwner(), new Observer<List<Step>>() {
                @Override
                public void onChanged(List<Step> steps) {
                    if (steps != null) {
                        Log.e(this.getClass().getSimpleName(), "size: " + steps.size());
                        mStepListRVAdapter.submit(steps);
                    }
                }
            });

        } else {
            Log.e(this.getClass().getSimpleName(), "recipeName is not present");
        }





        return rootview;
    }

    @Override
    public void onItemClickListener(Step step) {
        Log.e(this.getClass().getSimpleName(), "" + step.getId() + ".  " + step.getShortDescription());
    }


    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putString("recipeName", mRecipeName);
    }
}