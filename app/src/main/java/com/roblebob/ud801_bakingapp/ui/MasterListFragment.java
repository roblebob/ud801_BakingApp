package com.roblebob.ud801_bakingapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.data.AppDatabase;
import com.roblebob.ud801_bakingapp.model.Recipe;
import com.roblebob.ud801_bakingapp.viewmodels.MasterViewModel;
import com.roblebob.ud801_bakingapp.viewmodels.MasterViewModelFactory;

import java.util.List;

public class MasterListFragment extends Fragment implements MasterRVAdapter.ItemClickListener{


    MasterRVAdapter mMasterRVAdapter;

    // Mandatory empty constructor
    public MasterListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.fragment_master_list, container,false);

        RecyclerView masterRV = rootview.findViewById(R.id.master_RV);
        RecyclerView.LayoutManager masterRVLayoutManager = new GridLayoutManager(this.getContext(), 1, RecyclerView.VERTICAL, false);
        masterRV.setLayoutManager(masterRVLayoutManager);
        mMasterRVAdapter = new MasterRVAdapter(this);
        masterRV.setAdapter(mMasterRVAdapter);


        AppDatabase appDatabase = AppDatabase.getInstance(this.getContext());
        MasterViewModelFactory masterViewModelFactory = new MasterViewModelFactory(appDatabase);
        final MasterViewModel masterViewModel = new ViewModelProvider(this, masterViewModelFactory).get(MasterViewModel.class);

        masterViewModel.getRecipeListLive().observe( getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipeList) {
                if (recipeList != null) {
                    mMasterRVAdapter.submit(recipeList);
                }
            }
        });


        masterViewModel.start();


        return rootview;
    }







    @Override
    public void onItemClickListener(Recipe recipe) {
        Toast.makeText(getContext(), recipe.getName(), Toast.LENGTH_LONG).show();
    }
}
