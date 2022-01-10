package com.roblebob.ud801_bakingapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.repository.AppConnectivity;
import com.roblebob.ud801_bakingapp.model.Recipe;
import com.roblebob.ud801_bakingapp.viewmodels.MasterViewModel;
import com.roblebob.ud801_bakingapp.viewmodels.MasterViewModelFactory;


public class MasterListFragment extends Fragment implements MasterRVAdapter.ItemClickListener{

    // Mandatory empty constructor
    public MasterListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.fragment_master_list, container,false);

        RecyclerView masterRV = rootview.findViewById(R.id.master_RV);
        RecyclerView.LayoutManager masterRVLayoutManager = new GridLayoutManager(this.getContext(),
                (rootview.findViewById(R.id.master_multi_pane_marker) != null)? 3 : 1,
                RecyclerView.VERTICAL, false);
        masterRV.setLayoutManager(masterRVLayoutManager);
        MasterRVAdapter masterRVAdapter = new MasterRVAdapter(this);
        masterRV.setAdapter(masterRVAdapter);

        MasterViewModelFactory masterViewModelFactory = new MasterViewModelFactory(this.getContext());
        final MasterViewModel masterViewModel = new ViewModelProvider(this, masterViewModelFactory).get(MasterViewModel.class);

        new AppConnectivity( this.getContext()) .observe( getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                masterViewModel.start();
            }
        });

        masterViewModel.getRecipeListLive().observe( getViewLifecycleOwner(), recipeList -> {
            if (recipeList.size() != 0) {
                ((RecyclerView) rootview.findViewById(R.id.master_RV)).setVisibility(View.VISIBLE);
                ((TextView) rootview.findViewById(R.id.fragment_master_list_empty_tv)).setVisibility(View.GONE);
                masterRVAdapter.submit(recipeList);
            }
        });

        return rootview;
    }



    // Callback from the MasterRVAdapter
    @Override
    public void onItemClickListener(Recipe recipe) {

        MasterListFragmentDirections.ActionMasterListFragmentToRecipeFragment action = MasterListFragmentDirections.actionMasterListFragmentToRecipeFragment();
        action.setRecipeName(recipe.getName());
        action.setServings(recipe.getServings());
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(action);
    }
}
