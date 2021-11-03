package com.roblebob.ud801_bakingapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.viewmodels.DetailViewModel;
import com.roblebob.ud801_bakingapp.viewmodels.DetailViewModelFactory;


public class IngredientsFragment extends Fragment {

    IngredientsRVAdapter mIngredientsRVAdapter;

    public IngredientsFragment(){}

    String mRecipeName;
    void setRecipeName(String recipeName) { mRecipeName = recipeName; }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.fragment_ingredients, container, false);

        if (savedInstanceState != null) { mRecipeName = savedInstanceState.getString("recipeName"); }

        RecyclerView ingredientsRV = rootview.findViewById(R.id.ingredients_RV);
        RecyclerView.LayoutManager ingredientsRVLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false);
        ingredientsRV.setLayoutManager(ingredientsRVLayoutManager);
        mIngredientsRVAdapter = new IngredientsRVAdapter();
        ingredientsRV.setAdapter(mIngredientsRVAdapter);

        if (mRecipeName != null) {

            DetailViewModelFactory detailViewModelFactory = new DetailViewModelFactory(this.getContext(), mRecipeName);
            final DetailViewModel detailViewModel = new ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel.class);
            detailViewModel.getIngredientListLive().observe(getViewLifecycleOwner(), ingredients -> mIngredientsRVAdapter.submit(ingredients));
        }

        return rootview;
    }
}