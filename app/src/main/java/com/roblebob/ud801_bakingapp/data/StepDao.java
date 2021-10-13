package com.roblebob.ud801_bakingapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.roblebob.ud801_bakingapp.model.Step;

import java.util.List;

@Dao
public interface StepDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)     void insert(Step step);
    @Update(onConflict = OnConflictStrategy.REPLACE)    void update(Step step);
    @Delete                                             void delete(Step step);

    @Query("SELECT * FROM STEP WHERE recipeName = :recipeName ORDER BY stepNumber")
    LiveData<List< Step>> loadStepListLive(String recipeName);
}