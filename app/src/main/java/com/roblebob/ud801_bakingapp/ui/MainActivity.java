package com.roblebob.ud801_bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.model.Recipe;
import com.roblebob.ud801_bakingapp.model.Step;

public class MainActivity extends AppCompatActivity
        implements MasterListFragment.OnRecipeClickListener,
                    RecipeFragment.OnStepClickListener,
                    RecipeFragment.OnIngredientsClickListener

{
    public static final String TAG = MainActivity.class.getSimpleName();


    NavController mNavController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        mNavController = navHostFragment.getNavController();

    }







    // Callbacks from Fragments
    // ... from MasterListFragment
    @Override
    public void onRecipeSelected( Recipe recipe) {

//        Bundle bundle = new Bundle();
//        bundle.putString("recipeName", recipe.getName());
//        bundle.putInt("servings", recipe.getServings());
//        mNavController.navigate(R.id.action_masterListFragment_to_recipeFragment, bundle);
    }

    // ... from RecipeFragment
    @Override
    public void onStepSelected(Step step) {

        mNavController.navigate(R.id.action_recipeFragment_to_stepFragment);
    }


    // ... from RecipeFragment
    @Override
    public void onIngredientsSelected() {

        mNavController.navigate(R.id.action_recipeFragment_to_ingredientsFragment );
    }








}
