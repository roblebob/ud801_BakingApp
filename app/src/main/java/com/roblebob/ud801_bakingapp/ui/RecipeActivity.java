package com.roblebob.ud801_bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.model.Step;

public class RecipeActivity extends AppCompatActivity implements RecipeFragment.OnStepClickListener{

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if (savedInstanceState == null) {

            String recipeName = getIntent().getStringExtra("recipeName");

            Log.e(this.getClass().getSimpleName(), recipeName);

            RecipeFragment recipeFragment = new RecipeFragment();
            recipeFragment.setRecipeName(recipeName);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.recipe_container, recipeFragment).commit();
        }
    }

    @Override
    public void onStepSelected(Step step) {
        final Intent intent = new Intent(this, StepActivity.class);
        intent.putExtra("recipeName", step.getRecipeName());
        intent.putExtra("stepNumber", step.getStepNumber());
        startActivity(intent);
    }
}
