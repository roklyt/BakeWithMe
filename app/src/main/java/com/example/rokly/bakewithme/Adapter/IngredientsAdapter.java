package com.example.rokly.bakewithme.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rokly.bakewithme.R;
import com.example.rokly.bakewithme.data.Ingredients;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder> {

    private final IngredientsAdapter.IngredientsAdapterOnClickHandler ClickHandler;
    /* List for all ingredients*/
    private List<Ingredients> IngredientsList;


    public IngredientsAdapter(IngredientsAdapter.IngredientsAdapterOnClickHandler clickHandler, List<Ingredients> ingredientsList) {
        ClickHandler = clickHandler;
        IngredientsList = ingredientsList;
    }

    @Override
    public IngredientsAdapter.IngredientsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_recycle_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new IngredientsAdapter.IngredientsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.IngredientsAdapterViewHolder forecastAdapterViewHolder, int position) {
        Ingredients ingredients = IngredientsList.get(position);

        /* Set the text from the steps to the textview */
        forecastAdapterViewHolder.IngredientsTextView.setText(ingredients.getIngredient());
    }

    @Override
    public int getItemCount() {
        return IngredientsList.size();
    }

    /* Set the new Ingredients list to the adapter */
    public void setIngredientsData(List<Ingredients> ingredientsData) {
        IngredientsList = ingredientsData;
        notifyDataSetChanged();
    }

    /* Interface for the on click handler */
    public interface IngredientsAdapterOnClickHandler {
        void onClick(Ingredients currentStep);
    }

    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView IngredientsTextView;

        public IngredientsAdapterViewHolder(View view) {
            super(view);
            IngredientsTextView = view.findViewById(R.id.recipe_text_recyclerview);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Ingredients currentStep = IngredientsList.get(getAdapterPosition());
            ClickHandler.onClick(currentStep);
        }
    }
}