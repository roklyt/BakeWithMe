package com.example.rokly.bakewithme.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rokly.bakewithme.R;
import com.example.rokly.bakewithme.data.Recipes;
import com.example.rokly.bakewithme.data.Steps;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder> {

    private final StepsAdapter.StepsAdapterOnClickHandler ClickHandler;
    /* List for all steps*/
    private List<Steps> StepsList;


    public StepsAdapter(StepsAdapter.StepsAdapterOnClickHandler clickHandler, List<Steps> stepsList) {
        ClickHandler = clickHandler;
        StepsList = stepsList;
    }

    @Override
    public StepsAdapter.StepsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_recycle_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new StepsAdapter.StepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsAdapter.StepsAdapterViewHolder forecastAdapterViewHolder, int position) {

        if(position == 0){
            forecastAdapterViewHolder.StepsTextView.setText("Ingredients");
        }else{
            /*Get the current movie to find the correct poster path */
            Steps steps = StepsList.get(position -1);

            /* Set the text from the recipe to the textview */
            forecastAdapterViewHolder.StepsTextView.setText(steps.getShortDescription());
        }

    }

    @Override
    public int getItemCount() {
        return StepsList.size() + 1;
    }

    /* Set the new Steps list to the adapter */
    public void setStepsData(List<Steps> stepsData) {
        StepsList = stepsData;
        notifyDataSetChanged();
    }

    /* Interface for the on click handler */
    public interface StepsAdapterOnClickHandler {
        void onClick(int position, View view);
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView StepsTextView;

        public StepsAdapterViewHolder(View view) {
            super(view);
            StepsTextView = view.findViewById(R.id.recipe_text_recyclerview);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ClickHandler.onClick(getAdapterPosition() -1, v);
        }
    }
}