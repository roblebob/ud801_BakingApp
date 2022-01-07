package com.roblebob.ud801_bakingapp.ui;

import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.model.Step;
import com.roblebob.ud801_bakingapp.viewmodels.DetailViewModel;
import com.roblebob.ud801_bakingapp.viewmodels.DetailViewModelFactory;

import java.util.List;

public class RecipeFragment extends Fragment implements RecipeStepListRVAdapter.ItemClickListener{

    String mRecipeName;
    public void setRecipeName(String recipeName) { mRecipeName = recipeName; }

    int mServings;
    public void setServings(int servings) { mServings = servings; }

    RecipeStepListRVAdapter mRecipeStepListRVAdapter;


    public RecipeFragment() {}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.fragment_recipe, container, false);

        RecipeFragmentArgs safeArgs;

        if (savedInstanceState == null)
        {
            //safeArgs =
        } else {
            mRecipeName = savedInstanceState.getString("recipeName");
        }


        ((TextView) rootview.findViewById(R.id.fragment_recipe_heading_tv)).setText(mRecipeName);
        ((TextView) rootview.findViewById(R.id.fragment_recipe_servings_value_tv)).setText(String.valueOf(mServings));

        RecyclerView stepListRV = rootview.findViewById(R.id.fragment_recipe_steps_rv);
        RecyclerView.LayoutManager stepListRVLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        stepListRV.setLayoutManager(stepListRVLayoutManager);
        mRecipeStepListRVAdapter = new RecipeStepListRVAdapter(this);
        stepListRV.setAdapter(mRecipeStepListRVAdapter);

        DetailViewModelFactory detailViewModelFactory = new DetailViewModelFactory(this.getContext(), mRecipeName);
        final DetailViewModel detailViewModel = new ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel.class);
        detailViewModel.getStepListLive().observe(getViewLifecycleOwner(), new Observer<List<Step>>() {
            @Override
            public void onChanged(List<Step> steps) {
                if (steps != null) {
                    Log.e(this.getClass().getSimpleName(), "size: " + steps.size());
                    mRecipeStepListRVAdapter.submit(steps);
                }
            }
        });

        rootview.findViewById(R.id.fragment_recipe_ingredients_button)
                .setOnClickListener( (view) -> mIngredientsCallback.onIngredientsSelected());

        return rootview;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) { currentState.putString("recipeName", mRecipeName); }

    @Override
    public void onItemClickListener(Step step) { mStepCallback.onStepSelected(step); }



    /**
     Callback interface back to the MainActivity
     **/

    public interface OnStepClickListener { void onStepSelected(Step step); }
    OnStepClickListener mStepCallback;

    public interface OnIngredientsClickListener { void onIngredientsSelected(); }
    OnIngredientsClickListener mIngredientsCallback;



    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mStepCallback = (OnStepClickListener) context;
            mIngredientsCallback = (OnIngredientsClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }
}
