package com.example.rokly.bakewithme.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rokly.bakewithme.R;
import com.example.rokly.bakewithme.data.Ingredients;

import org.w3c.dom.Text;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder> {

    /* List for all ingredients*/
    private List<Ingredients> IngredientsList;


    public IngredientsAdapter(List<Ingredients> ingredientsList) {
        IngredientsList = ingredientsList;
    }

    @Override
    public IngredientsAdapter.IngredientsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_ingredients_recycle_item;
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
        forecastAdapterViewHolder.QuantityTextView.setText(ingredients.getQuantity() + ":");
        forecastAdapterViewHolder.MeasueTextView.setText(ingredients.getMeasure());
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

    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView IngredientsTextView;
        TextView QuantityTextView;
        TextView MeasueTextView;

        public IngredientsAdapterViewHolder(View view) {
            super(view);
            IngredientsTextView = view.findViewById(R.id.tv_ingredients_ingredient);
            QuantityTextView = view.findViewById(R.id.tv_ingredients_quantity);
            MeasueTextView = view.findViewById(R.id.tv_ingredients_measure);
        }
    }
}