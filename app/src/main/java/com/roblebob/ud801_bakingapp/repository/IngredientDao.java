package com.roblebob.ud801_bakingapp.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.roblebob.ud801_bakingapp.model.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)     void insert(Ingredient ingredient);

    @Query("SELECT * FROM Ingredient WHERE recipeName = :recipeName" )
    LiveData<List<Ingredient>> loadIngredientsLive(String recipeName);

    @Query("SELECT * FROM Ingredient WHERE recipeName = :recipeName" )
    List<Ingredient> loadIngredients(String recipeName);

}
