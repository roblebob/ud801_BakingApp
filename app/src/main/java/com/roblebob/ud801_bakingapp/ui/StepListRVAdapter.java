package com.roblebob.ud801_bakingapp.ui;

import android.util.Log;
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

public class StepListRVAdapter extends RecyclerView.Adapter<StepListRVAdapter.StepRVViewHolder> {

    List<Step> mStepList = new ArrayList<>();

    public void submit(List<Step> stepList) {
        Log.e(this.getClass().getSimpleName(), "size: " + stepList.size());
        mStepList = new ArrayList<>(stepList);
        notifyDataSetChanged();
    }


    public interface ItemClickListener { void onItemClickListener(Step step); }
    ItemClickListener mItemClickListener;
    StepListRVAdapter(ItemClickListener itemClickListener) { mItemClickListener = itemClickListener; }






    @NonNull
    @Override
    public StepRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_list_single_item, parent, false);
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







    public class StepRVViewHolder extends RecyclerView.ViewHolder {

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
