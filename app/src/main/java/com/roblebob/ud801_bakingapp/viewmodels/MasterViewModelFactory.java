package com.roblebob.ud801_bakingapp.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class MasterViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Context mContext;

    public MasterViewModelFactory(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create (@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MasterViewModel(mContext);
    }
}
