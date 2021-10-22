package com.roblebob.ud801_bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.model.Step;

public class RecipeActivity extends AppCompatActivity
        implements RecipeFragment.OnStepClickListener,
                    RecipeFragment.OnIngredientsClickListener
{

    String mRecipeName;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if (savedInstanceState == null) {

            mRecipeName = getIntent().getStringExtra("recipeName");

            Log.e(this.getClass().getSimpleName(), mRecipeName);

            RecipeFragment recipeFragment = new RecipeFragment();
            recipeFragment.setRecipeName(mRecipeName);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.recipe_container, recipeFragment).commit();
        } else {
            mRecipeName = savedInstanceState.getString("recipeName");
        }


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("recipeName", mRecipeName);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStepSelected(Step step) {
        final Intent intent = new Intent(this, StepActivity.class);
        intent.putExtra("recipeName", step.getRecipeName());
        intent.putExtra("stepNumber", step.getStepNumber());
        startActivity(intent);
    }


    @Override
    public void onIngredientsSelected() {
        final Intent intent = new Intent(this, IngredientsActivity.class);
        intent.putExtra("recipeName", mRecipeName);
        startActivity(intent);
    }
}
