package com.roblebob.ud801_bakingapp.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.roblebob.ud801_bakingapp.R;

public class IngredientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        if (savedInstanceState == null) {

            String recipeName = getIntent().getStringExtra("recipeName");

            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setRecipeName(recipeName);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.ingredients_container, ingredientsFragment).commit();
        }
    }
}
