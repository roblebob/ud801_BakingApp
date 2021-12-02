package com.roblebob.ud801_bakingapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.model.Recipe;
import com.roblebob.ud801_bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MasterListFragment.OnRecipeClickListener,
                    RecipeFragment.OnStepClickListener,
                    RecipeFragment.OnIngredientsClickListener

{
    public static final String TAG = MainActivity.class.getSimpleName();

    FragmentManager mFragmentManager;
    List<Fragment> mFragmentBackstack;


    String mRecipeName;
    int mRecipeServings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // (re-) building the fragment backstack

        mFragmentManager = getSupportFragmentManager();
        mFragmentBackstack = new ArrayList<>();
        MasterListFragment masterListFragment = new MasterListFragment();
        mFragmentBackstack.add( masterListFragment);

        if (savedInstanceState == null) {

            mFragmentManager.beginTransaction().add(R.id.pane_1, masterListFragment).commit();

        } else {

            String currentFragment = savedInstanceState.getString("current_fragment");
            mRecipeName = savedInstanceState.getString("recipe_name");
            mRecipeServings = savedInstanceState.getInt("recipe_Servings");

            if ( currentFragment.equals( RecipeFragment.class.getSimpleName())) {
                RecipeFragment recipeFragment = new RecipeFragment();
                recipeFragment.setRecipeName( mRecipeName);
                recipeFragment.setServings( mRecipeServings);
                mFragmentBackstack.add( recipeFragment);

            } else if ( currentFragment.equals( IngredientsFragment.class.getSimpleName())) {

                RecipeFragment recipeFragment = new RecipeFragment();
                recipeFragment.setRecipeName( mRecipeName);
                recipeFragment.setServings( mRecipeServings);
                mFragmentBackstack.add( recipeFragment);

                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setRecipeName( mRecipeName);
                mFragmentBackstack.add( ingredientsFragment);

                // TODO: WEIRD !!!!  that this is necessary
                mFragmentManager.beginTransaction().add(R.id.pane_1, ingredientsFragment).commit();

            } else if ( currentFragment.equals( StepFragment.class.getSimpleName())) {

                RecipeFragment recipeFragment = new RecipeFragment();
                recipeFragment.setRecipeName( mRecipeName);
                recipeFragment.setServings( mRecipeServings);
                mFragmentBackstack.add( recipeFragment);

                StepFragment stepFragment = new StepFragment();
                stepFragment.setRecipeName( mRecipeName);
                mFragmentBackstack.add( stepFragment);
            }
        }



    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.e(TAG, mFragmentBackstack.get( mFragmentBackstack.size() - 1).getClass().getSimpleName());

        outState.putString("current_fragment", mFragmentBackstack.get( mFragmentBackstack.size() - 1).getClass().getSimpleName());
        outState.putString("recipe_name", mRecipeName);
        outState.putInt("recipe_servings", mRecipeServings);

        super.onSaveInstanceState(outState);
    }



















    // Callbacks from Fragments
    // ... from MasterListFragment
    @Override
    public void onRecipeSelected( Recipe recipe) {
        mRecipeName = recipe.getName();
        mRecipeServings = recipe.getServings();
        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setRecipeName( mRecipeName);
        recipeFragment.setServings( mRecipeServings);
        mFragmentManager.beginTransaction().replace(R.id.pane_1, recipeFragment).commit();
        mFragmentBackstack.add( recipeFragment);
    }

    // ... from RecipeFragment
    @Override
    public void onStepSelected(Step step) {
        StepFragment stepFragment = new StepFragment();
        stepFragment.setRecipeName(mRecipeName);
        stepFragment.setStepNumber(step.getStepNumber());
        mFragmentManager.beginTransaction().replace(R.id.pane_1, stepFragment).commit();
        mFragmentBackstack.add( stepFragment);
    }

    // ... from RecipeFragment
    @Override
    public void onIngredientsSelected() {
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.setRecipeName( mRecipeName);
        mFragmentManager.beginTransaction().replace(R.id.pane_1, ingredientsFragment).commit();
        mFragmentBackstack.add( ingredientsFragment);
    }








    @Override
    public void onBackPressed() {
        mFragmentBackstack.remove(mFragmentBackstack.size() - 1);

        if ( mFragmentBackstack.isEmpty()) {
            super.onBackPressed();
        } else {
            mFragmentManager.beginTransaction().replace(R.id.pane_1, mFragmentBackstack.get( mFragmentBackstack.size() - 1)).commit();
        }
    }
}
