package com.roblebob.ud801_bakingapp.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.roblebob.ud801_bakingapp.data.AppDatabase;

public class WidgetViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mAppDatabase;

    public WidgetViewModelFactory(AppDatabase appDatabase) {
        mAppDatabase = appDatabase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create (@NonNull Class<T> modelClass) {
        return (T) new WidgetViewModel(mAppDatabase);
    }
}
