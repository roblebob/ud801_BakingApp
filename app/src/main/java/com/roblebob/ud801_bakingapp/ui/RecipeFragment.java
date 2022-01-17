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
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.model.Step;
import com.roblebob.ud801_bakingapp.viewmodels.DetailViewModel;
import com.roblebob.ud801_bakingapp.viewmodels.DetailViewModelFactory;

import java.util.List;

public class RecipeFragment extends Fragment implements RecipeStepListRVAdapter.ItemClickListener{

    public static final String TAG = RecipeFragment.class.getSimpleName();

    String mRecipeName;
    int mServings;

    boolean mIsTwoPane = false;

    public RecipeFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.fragment_recipe, container, false);

        FragmentContainerView fragmentContainerView = rootview.findViewById(R.id.fragment_recipe_detail_pane);
        mIsTwoPane = fragmentContainerView != null;
        Log.e(TAG, "mIsTwoPane= " + mIsTwoPane);

        if (savedInstanceState == null) {
            mRecipeName = RecipeFragmentArgs.fromBundle(getArguments()).getRecipeName();
            mServings = RecipeFragmentArgs.fromBundle(getArguments()).getServings();
        } else {
            mRecipeName = savedInstanceState.getString("recipeName");
            mServings = savedInstanceState.getInt("servings");
        }

        ((TextView) rootview.findViewById(R.id.fragment_recipe_heading_tv)).setText(mRecipeName);
        ((TextView) rootview.findViewById(R.id.fragment_recipe_servings_value_tv)).setText(String.valueOf(mServings));

        RecyclerView stepListRV = rootview.findViewById(R.id.fragment_recipe_steps_rv);
        RecyclerView.LayoutManager stepListRVLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        stepListRV.setLayoutManager(stepListRVLayoutManager);
        RecipeStepListRVAdapter recipeStepListRVAdapter = new RecipeStepListRVAdapter(this);
        stepListRV.setAdapter(recipeStepListRVAdapter);

        DetailViewModelFactory detailViewModelFactory = new DetailViewModelFactory(this.getContext(), mRecipeName);
        final DetailViewModel detailViewModel = new ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel.class);
        detailViewModel.getStepListLive().observe(getViewLifecycleOwner(), new Observer<List<Step>>() {
            @Override
            public void onChanged(List<Step> steps) {
                if (steps != null) {
                    Log.e(this.getClass().getSimpleName(), "size: " + steps.size());
                    recipeStepListRVAdapter.submit(steps);
                }
            }
        });

        rootview.findViewById(R.id.fragment_recipe_ingredients_button)
                .setOnClickListener( (view) -> {
                    RecipeFragmentDirections.ActionRecipeFragmentToIngredientsFragment action = RecipeFragmentDirections.actionRecipeFragmentToIngredientsFragment();
                    action.setRecipeName(mRecipeName);
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(action);
                });

        return rootview;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putString("recipeName", mRecipeName);
        currentState.putInt("servings", mServings);
    }

    // Callback from the RecipeStepListRVAdapter
    @Override
    public void onItemClickListener(Step step) {
        RecipeFragmentDirections.ActionRecipeFragmentToStepFragment action = RecipeFragmentDirections.actionRecipeFragmentToStepFragment();
        action.setRecipeName(mRecipeName);
        action.setStepNumber(step.getStepNumber());
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(action);
    }
}
