package com.roblebob.ud801_bakingapp.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;





public class MasterRVAdapter extends RecyclerView.Adapter<MasterRVAdapter.MasterRVViewHolder> {

    List< Recipe> mRecipeList = new ArrayList<>();

    public void submit( List<Recipe> recipeList) {
        mRecipeList = new ArrayList<>(recipeList);
        notifyDataSetChanged();
    }


    public interface ItemClickListener { void onItemClickListener(Recipe recipe);}
    ItemClickListener mItemClickListener;
    MasterRVAdapter(ItemClickListener itemClickListener) { mItemClickListener = itemClickListener; }



    @NonNull
    @Override
    public MasterRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.master_list_single_item, parent, false);
        return new MasterRVViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MasterRVViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        holder.bindTo(recipe);


        holder.itemView.setOnClickListener( (view) -> {
            mItemClickListener.onItemClickListener( mRecipeList.get( position));
        } );


    }



    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }




    public class MasterRVViewHolder extends RecyclerView.ViewHolder
    {
        TextView recipeNameTv;
        TextView servingsTv;

        public MasterRVViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeNameTv = itemView.findViewById(R.id.master_list_single_item_recipe_tv);
            servingsTv = itemView.findViewById(R.id.master_list_single_item_servings_value_tv);
        }

        public void bindTo(Recipe recipe) {
            recipeNameTv.setText(recipe.getName());
            servingsTv.setText(String.valueOf(recipe.getServings()));
        }
    }
}
