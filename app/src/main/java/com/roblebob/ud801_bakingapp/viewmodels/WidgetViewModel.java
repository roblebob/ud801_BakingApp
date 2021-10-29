package com.roblebob.ud801_bakingapp.viewmodels;

import androidx.lifecycle.ViewModel;

import com.roblebob.ud801_bakingapp.data.AppDatabase;
import com.roblebob.ud801_bakingapp.data.AppRepository;

import java.util.List;

public class WidgetViewModel extends ViewModel {

    private final AppRepository mAppRepository;

    public WidgetViewModel(final AppDatabase appDatabase) {
        mAppRepository = new AppRepository(appDatabase);
    }

    public List<String> getRecipeNameList() {
        return mAppRepository.getRecipeNameList();
    }
}
