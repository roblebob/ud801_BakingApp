package com.roblebob.ud801_bakingapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.roblebob.ud801_bakingapp.data.AppDatabase;
import com.roblebob.ud801_bakingapp.data.AppRepository;
import com.roblebob.ud801_bakingapp.model.Recipe;

import java.util.List;


public class MasterViewModel extends ViewModel {
    private final AppRepository mAppRepository;

    public MasterViewModel(final AppDatabase appDatabase) {
        this.mAppRepository = new AppRepository(appDatabase);
    }

    public LiveData<List<Recipe>> getRecipeListLive() {
        return mAppRepository.getRecipeNameListLive();
    }

}
