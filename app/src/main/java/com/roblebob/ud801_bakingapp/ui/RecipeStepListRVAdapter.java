package com.roblebob.ud801_bakingapp.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepListRVAdapter extends RecyclerView.Adapter<RecipeStepListRVAdapter.StepRVViewHolder> {

    List<Step> mStepList = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void submit(List<Step> stepList) {
        mStepList = new ArrayList<>(stepList);
        notifyDataSetChanged();
    }


    public interface ItemClickListener { void onItemClickListener(Step step); }
    ItemClickListener mItemClickListener;


    RecipeStepListRVAdapter(ItemClickListener itemClickListener) { mItemClickListener = itemClickListener; }


    @NonNull
    @Override
    public StepRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_step_list, parent, false);
        return new StepRVViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull StepRVViewHolder holder, int position) {

        holder.bindTo(mStepList.get(position));

        holder.itemView.setOnClickListener( (view) -> mItemClickListener.onItemClickListener(mStepList.get(position)));
    }


    @Override
    public int getItemCount() {
        return mStepList.size();
    }



    public static class StepRVViewHolder extends RecyclerView.ViewHolder {

        TextView stepNumberTv;
        TextView shortDescriptionTv;

        public StepRVViewHolder(@NonNull View itemView) {
            super(itemView);
            stepNumberTv = itemView.findViewById(R.id.step_list_single_item_step_number);
            shortDescriptionTv = itemView.findViewById(R.id.step_list_single_item_short_description);
        }

        public void bindTo(Step step) {
            stepNumberTv.setText(String.valueOf(step.getStepNumber()));
            shortDescriptionTv.setText(step.getShortDescription());
        }
    }
}
