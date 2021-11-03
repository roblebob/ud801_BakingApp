package com.roblebob.ud801_bakingapp.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.roblebob.ud801_bakingapp.repository.AppRepository;
import com.roblebob.ud801_bakingapp.model.Ingredient;
import com.roblebob.ud801_bakingapp.model.Step;

import java.util.List;

public class DetailViewModel extends ViewModel {

    final private AppRepository mAppRepository;
    final private String mRecipeName;

    public DetailViewModel(Context context, String recipeName) {
        mAppRepository = new AppRepository(context);
        mRecipeName = recipeName;
    }

    public LiveData<List<Ingredient>> getIngredientListLive() {
        return mAppRepository.getIngredientListLive(mRecipeName);
    }

    public LiveData<List<Step>> getStepListLive() {
        return mAppRepository.getStepListLive(mRecipeName);
    }

}
