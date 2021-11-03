package com.roblebob.ud801_bakingapp.repository;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;

import com.roblebob.ud801_bakingapp.model.AppState;
import com.roblebob.ud801_bakingapp.model.Ingredient;
import com.roblebob.ud801_bakingapp.model.Recipe;
import com.roblebob.ud801_bakingapp.model.Step;


@Database( entities = { Recipe.class, Ingredient.class,  Step.class, AppState.class},   version = 1,   exportSchema = false)
public abstract class AppDatabase extends RoomDatabase { /*singleton-pattern*/
    private static final String DATABASE_NAME  = "BakingAppDatabase";
    private static AppDatabase  sInstance;
    private static final Object LOCK  = new Object();
    public static AppDatabase   getInstance( Context context) {
        if ( sInstance == null) {  synchronized (LOCK) { sInstance = Room
                .databaseBuilder(  context.getApplicationContext(),  AppDatabase.class,  AppDatabase.DATABASE_NAME)
                //.allowMainThreadQueries()
                .build();
        }}
        return sInstance;
    }
    public abstract RecipeDao recipeDao();
    public abstract IngredientDao ingredientDao();
    public abstract StepDao stepDao();
    public abstract AppStateDao appStateDao();
}
