package com.roblebob.ud801_bakingapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsRVAdapter extends RecyclerView.Adapter<IngredientsRVAdapter.IngredientsRVViewHolder>{

    List<Ingredient> mIngredientList = new ArrayList<>();

    public void submit( List<Ingredient> ingredientList) {
        mIngredientList = new ArrayList<>(ingredientList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientsRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_single_item, parent, false);
        return new IngredientsRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsRVViewHolder holder, int position) {
        holder.bindTo(mIngredientList.get(position));
    }

    @Override
    public int getItemCount() { return mIngredientList.size(); }




    public class IngredientsRVViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientsNameTv;
        TextView ingredientsQuantityTv;
        TextView ingredientsMeasureTv;

        public IngredientsRVViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientsNameTv = itemView.findViewById(R.id.ingredient_list_single_item_name_tv);
            ingredientsQuantityTv = itemView.findViewById(R.id.ingredient_list_single_item_quantity_tv);
            ingredientsMeasureTv = itemView.findViewById(R.id.ingredient_list_single_item_measure_tv);
        }

        public void bindTo(Ingredient ingredient) {
            ingredientsNameTv.setText(ingredient.getName());
            ingredientsQuantityTv.setText(String.valueOf(ingredient.getQuantity()));
            ingredientsMeasureTv.setText(ingredient.getMeasure());
        }
    }


}
