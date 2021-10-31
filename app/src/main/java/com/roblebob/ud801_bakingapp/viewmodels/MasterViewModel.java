package com.roblebob.ud801_bakingapp.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.roblebob.ud801_bakingapp.data.AppRepository;
import com.roblebob.ud801_bakingapp.model.Recipe;

import java.util.List;


public class MasterViewModel extends ViewModel {
    private final AppRepository mAppRepository;

    public MasterViewModel(final Context context) {
        this.mAppRepository = new AppRepository(context);
    }

    public LiveData<List<Recipe>> getRecipeListLive() {
        return mAppRepository.getRecipeListLive();
    }

    public void start() {
        mAppRepository.integrate();
    }
}
