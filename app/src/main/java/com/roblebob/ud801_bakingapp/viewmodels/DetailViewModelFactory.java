package com.roblebob.ud801_bakingapp.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class DetailViewModelFactory extends ViewModelProvider .NewInstanceFactory {

    private final Context mContext;
    private final String mRecipeName;

    public DetailViewModelFactory(Context context, String recipeName) {
        mContext = context;
        mRecipeName = recipeName;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailViewModel(mContext, mRecipeName);
    }
}
