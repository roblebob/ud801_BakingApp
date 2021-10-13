package com.roblebob.ud801_bakingapp.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.roblebob.ud801_bakingapp.data.AppDatabase;

public class DetailViewModelFactory extends ViewModelProvider .NewInstanceFactory {

    private final AppDatabase mAppDatabase;
    private final String mRecipeName;

    public DetailViewModelFactory(AppDatabase appDatabase, String recipeName) {
        mAppDatabase = appDatabase;
        mRecipeName = recipeName;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailViewModel(mAppDatabase, mRecipeName);
    }
}
