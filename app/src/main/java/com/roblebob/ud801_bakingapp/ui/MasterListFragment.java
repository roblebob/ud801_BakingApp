package com.roblebob.ud801_bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.model.Recipe;
import com.roblebob.ud801_bakingapp.viewmodels.MasterViewModel;
import com.roblebob.ud801_bakingapp.viewmodels.MasterViewModelFactory;

import java.util.ArrayList;
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
        RecyclerView.LayoutManager masterRVLayoutManager = new GridLayoutManager(this.getContext(),
                (rootview.findViewById(R.id.master_multi_pane_marker) != null)? 3 : 1,
                RecyclerView.VERTICAL, false);
        masterRV.setLayoutManager(masterRVLayoutManager);
        mMasterRVAdapter = new MasterRVAdapter(this);
        masterRV.setAdapter(mMasterRVAdapter);

        MasterViewModelFactory masterViewModelFactory = new MasterViewModelFactory(this.getContext());
        final MasterViewModel masterViewModel = new ViewModelProvider(this, masterViewModelFactory).get(MasterViewModel.class);

        masterViewModel.getRecipeListLive().observe( getViewLifecycleOwner(), recipeList -> {
            Log.e(this.getClass().getSimpleName(), "recieving  " + recipeList.toString() );
            if (recipeList.size() != 0) {
                ((RecyclerView) rootview.findViewById(R.id.master_RV)).setVisibility(View.VISIBLE);
                ((TextView) rootview.findViewById(R.id.fragment_master_list_empty_tv)).setVisibility(View.GONE);
                mMasterRVAdapter.submit(recipeList);
            } else {
                mMasterRVAdapter.submit(TEST_LIST);
            }
        });


        masterViewModel.start();
        return rootview;
    }






    // Callback from the MasterRVAdapter
    @Override
    public void onItemClickListener(Recipe recipe) {
        Log.e(this.getClass().getSimpleName(), recipe.getName() + " was clicked");
        Toast.makeText(getContext(), recipe.getName(), Toast.LENGTH_LONG).show();

        mCallback.onRecipeSelected(recipe);
    }





    /**
        Callback interface back to the MainActivity
    **/

    public interface OnRecipeClickListener {
        void onRecipeSelected(Recipe recipe);
    }

    OnRecipeClickListener mCallback;


    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeClickListener");
        }
    }





    final static List< Recipe> TEST_LIST =  new ArrayList<Recipe>() {
        {
            add(new Recipe(0, "Recipe Test 0", 10, ""));
            add(new Recipe(0, "Recipe Test 1", 10, ""));
            add(new Recipe(0, "Recipe Test 2", 10, ""));
        }
    };
}
