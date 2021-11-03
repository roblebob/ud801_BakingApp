package com.roblebob.ud801_bakingapp.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.roblebob.ud801_bakingapp.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Recipe recipe);

    @Query("SELECT * FROM Recipe")
    LiveData<List< Recipe>> loadRecipeListLive();

    @Query("SELECT name FROM Recipe")
    List< String> loadRecipeNameList();

}
