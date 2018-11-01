package com.example.rokly.bakewithme.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rokly.bakewithme.R;
import com.example.rokly.bakewithme.data.Recipes;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private final RecipeAdapterOnClickHandler ClickHandler;
    /* List for all recipes*/
    private List<Recipes> RecipesList;


    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler, List<Recipes> recipesList) {
        ClickHandler = clickHandler;
        RecipesList = recipesList;
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.card_view_main;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder forecastAdapterViewHolder, final int position) {
        Recipes recipes = RecipesList.get(position);

        /* Set the text from the recipe to the textview */
        forecastAdapterViewHolder.RecipeTextView.setText(recipes.getName());

    }

    @Override
    public int getItemCount() {
        return RecipesList.size();
    }

    /* Set the new Recipes list to the adapter */
    public void setRecipeData(List<Recipes> recipeData) {
        RecipesList = recipeData;
        notifyDataSetChanged();
    }

    /* Interface for the on click handler */
    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipes currentRecipe);
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView RecipeTextView;

        public RecipeAdapterViewHolder(View view) {
            super(view);
            RecipeTextView = view.findViewById(R.id.recipe_text_recyclerview);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Recipes currentRecips = RecipesList.get(getAdapterPosition());
            ClickHandler.onClick(currentRecips);
        }
    }
}