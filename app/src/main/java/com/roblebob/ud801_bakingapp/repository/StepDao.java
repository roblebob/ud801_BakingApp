package com.roblebob.ud801_bakingapp.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.roblebob.ud801_bakingapp.model.Step;

import java.util.List;

@Dao
public interface StepDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)     void insert(Step step);

    @Query("SELECT * FROM STEP WHERE recipeName = :recipeName ORDER BY stepNumber")
    LiveData<List< Step>> loadStepListLive(String recipeName);
}
