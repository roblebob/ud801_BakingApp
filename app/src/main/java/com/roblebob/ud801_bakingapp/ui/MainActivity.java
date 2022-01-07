package com.roblebob.ud801_bakingapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;
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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();


    }












    // Callbacks from Fragments
    // ... from MasterListFragment
    @Override
    public void onRecipeSelected( Recipe recipe) {

    }

    // ... from RecipeFragment
    @Override
    public void onStepSelected(Step step) {

    }


    // ... from RecipeFragment
    @Override
    public void onIngredientsSelected() {

    }








}
