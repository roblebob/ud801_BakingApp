package com.roblebob.ud801_bakingapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.roblebob.ud801_bakingapp.model.AppState;

@Dao
public interface AppStateDao {

    @Query("SELECT value FROM AppState WHERE `key` = :key")
    LiveData< String> loadStateLive(String key);


    @Query("SELECT value FROM AppState WHERE `key` = 'current_recipe_name'")
    LiveData< String> loadCurrentRecipeNameLive();


    @Query("SELECT value FROM AppState WHERE `key` = 'current_recipe_name'")
    String loadCurrentRecipeName();





    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AppState appState);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(AppState appState);

    @Delete
    void delete(AppState appState);
}
