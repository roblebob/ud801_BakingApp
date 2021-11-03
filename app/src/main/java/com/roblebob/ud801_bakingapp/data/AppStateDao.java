package com.roblebob.ud801_bakingapp.data;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.roblebob.ud801_bakingapp.model.AppState;

/**
 * Is only used to communicate between the two remote view services within the widget
 */


@Dao
public interface AppStateDao {

    @Query("SELECT value FROM AppState WHERE `key` = 'current_recipe_name'")
    String loadCurrentRecipeName();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AppState appState);
}
