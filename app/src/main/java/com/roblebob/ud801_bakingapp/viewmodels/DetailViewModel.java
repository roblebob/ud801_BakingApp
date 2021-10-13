package com.roblebob.ud801_bakingapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.roblebob.ud801_bakingapp.data.AppDatabase;
import com.roblebob.ud801_bakingapp.data.AppRepository;
import com.roblebob.ud801_bakingapp.model.Ingredient;
import com.roblebob.ud801_bakingapp.model.Recipe;
import com.roblebob.ud801_bakingapp.model.Step;

import java.util.List;

public class DetailViewModel extends ViewModel {

    final private AppRepository mAppRepository;
    final private String mRecipeName;

    public DetailViewModel(AppDatabase appDatabase, String recipeName) {
        mAppRepository = new AppRepository(appDatabase);
        mRecipeName = recipeName;
    }

    public LiveData<List<Ingredient>> getIngredientListLive() {
        return mAppRepository.getIngredientListLive(mRecipeName);
    }

    public LiveData<List<Step>> getStepListLive() {
        return mAppRepository.getStepListLive(mRecipeName);
    }

}
