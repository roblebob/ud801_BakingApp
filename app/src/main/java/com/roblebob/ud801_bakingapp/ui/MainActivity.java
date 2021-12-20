package com.roblebob.ud801_bakingapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import android.app.PictureInPictureParams;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.view.ViewGroup;

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

            ((FragmentContainerView) findViewById(R.id.pane_1)).getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
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






    boolean isTwoPane() {
        if (findViewById(R.id.pane_2) != null) {
            return true;
        }
        return false;
    }












    // Callbacks from Fragments
    // ... from MasterListFragment
    @Override
    public void onRecipeSelected( Recipe recipe) {
        logger("onRecipeSelected(...)  " +  "before" );

        ((FragmentContainerView) findViewById(R.id.pane_1)).getLayoutParams().width = dp2pixels(400);

        mRecipeName = recipe.getName();
        mRecipeServings = recipe.getServings();
        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setRecipeName( mRecipeName);
        recipeFragment.setServings( mRecipeServings);
        mFragmentManager.beginTransaction().replace(R.id.pane_1, recipeFragment).commit();
        mFragmentBackstack.add( recipeFragment);

        logger("onRecipeSelected(...)  " +  "after" );
    }

    // ... from RecipeFragment
    @Override
    public void onStepSelected(Step step) {
        logger("onStepSelected(...)  " +  "before" );
        StepFragment stepFragment = new StepFragment();
        stepFragment.setRecipeName(mRecipeName);
        stepFragment.setStepNumber(step.getStepNumber());
        mFragmentManager.beginTransaction()
                .replace( (isTwoPane()) ? R.id.pane_2 : R.id.pane_1, stepFragment)
                .commit();

        mFragmentBackstack.add( stepFragment);
        logger("onStepSelected(...)  " +  "after" );
    }


    // ... from RecipeFragment
    @Override
    public void onIngredientsSelected() {
        logger("onIngredientsSelected()  " +  "before" );
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.setRecipeName( mRecipeName);
        mFragmentManager.beginTransaction()
                .replace( (isTwoPane())  ?  R.id.pane_2  :  R.id.pane_1,   ingredientsFragment)
                .commit();

        mFragmentBackstack.add( ingredientsFragment);
        logger("onIngredientsSelected()  " +  "after" );
    }







    @Override
    public void onBackPressed() {
        logger("onBackPressed()  " + "before");


        mFragmentBackstack.remove(mFragmentBackstack.size() - 1);

        if ( mFragmentBackstack.isEmpty()) {
            super.onBackPressed();
        } else {

            if (isTwoPane()) {

                logger("onBackPressed()  " + "intermediate");

                if (mFragmentBackstack.get(mFragmentBackstack.size() - 1).getClass().getSimpleName()  .equals( MasterListFragment.class.getSimpleName())) {

                    ((FragmentContainerView) findViewById(R.id.pane_1)).getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    mFragmentManager.beginTransaction().replace(R.id.pane_1, mFragmentBackstack.get(mFragmentBackstack.size() - 1)).commit();

                }
                if (mFragmentBackstack.get(mFragmentBackstack.size() - 1).getClass().getSimpleName()  .equals( RecipeFragment.class.getSimpleName())) {



                    mFragmentBackstack.remove(mFragmentBackstack.size() - 1);
                    ((FragmentContainerView) findViewById(R.id.pane_1)).getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    mFragmentManager.beginTransaction().replace(R.id.pane_1, mFragmentBackstack.get(mFragmentBackstack.size() - 1)).commit();

                }

            } else {
                mFragmentManager.beginTransaction().replace(R.id.pane_1, mFragmentBackstack.get(mFragmentBackstack.size() - 1)).commit();
            }
        }
        logger("onBackPressed()  " + "after");
    }






    private int dp2pixels(int dp) {
        return (int) (dp * getApplicationContext().getResources().getDisplayMetrics().density);
    }






    private void logger(String label) {
        String output = label + "...\n";

        for (Fragment element : mFragmentBackstack) {

            output +=  "\n" + element.getClass().getSimpleName() ;
        }
        Log.e(TAG, output);
    }











//    @Override
//    protected void onUserLeaveHint() {
//
//        if (mStepFragment.hasVideoPlayable()) {
//
//            enterPictureInPictureMode(
//                    new PictureInPictureParams
//                            .Builder()
//                            .setAspectRatio(new Rational(16, 9))
//                            .build()
//            );
//        } else {
//
//            super.onUserLeaveHint();
//        }
//    }







}
