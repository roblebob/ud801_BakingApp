package com.roblebob.ud801_bakingapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.roblebob.ud801_bakingapp.model.Ingredient;
import com.roblebob.ud801_bakingapp.model.Recipe;

import java.util.List;

@Dao
public interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)     void insert(Ingredient ingredient);
    @Update(onConflict = OnConflictStrategy.REPLACE)    void update(Ingredient ingredient);
    @Delete                                             void delete(Ingredient ingredient);


    @Query("SELECT * FROM Ingredient WHERE recipeName = :recipeName" )
    LiveData<List<Ingredient>> loadIngredientsLive(String recipeName);
}
