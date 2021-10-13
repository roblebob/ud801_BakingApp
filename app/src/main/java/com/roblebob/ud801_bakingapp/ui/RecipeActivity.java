package com.roblebob.ud801_bakingapp.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.roblebob.ud801_bakingapp.R;

public class RecipeActivity extends AppCompatActivity {

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
}
