package com.roblebob.ud801_bakingapp.viewmodels;

import androidx.lifecycle.LiveData;

import com.roblebob.ud801_bakingapp.data.AppDatabase;
import com.roblebob.ud801_bakingapp.data.AppRepository;
import com.roblebob.ud801_bakingapp.model.Ingredient;
import com.roblebob.ud801_bakingapp.model.Recipe;
import com.roblebob.ud801_bakingapp.model.Step;

import java.util.List;

public class DetailViewModel {
    final private AppRepository mAppRepository;
    final private Recipe mRecipe;

    public DetailViewModel(AppDatabase appDatabase, Recipe recipe) {
        mAppRepository = new AppRepository(appDatabase);
        mRecipe = recipe;
    }

    public LiveData<List<Ingredient>> getIngredientListLive() {
        return mAppRepository.getIngredientListLive(mRecipe);
    }

    public LiveData<List<Step>> getStepListLive() {
        return mAppRepository.getStepListLive(mRecipe);
    }

}
