package com.example.rokly.bakewithme;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rokly.bakewithme.Adapter.IngredientsAdapter;
import com.example.rokly.bakewithme.Adapter.StepsAdapter;
import com.example.rokly.bakewithme.data.Ingredients;
import com.example.rokly.bakewithme.data.Recipes;
import com.example.rokly.bakewithme.data.Steps;

import java.util.List;

public class RecipeDetailIngredientsFragment extends Fragment {

    private static Recipes currentRecipe;
    private static List<Ingredients> ingredients;
    private RecyclerView recyclerView;
    private IngredientsAdapter ingredientsAdapter;
    private Context context;

    // Mandatory empty constructor
    public RecipeDetailIngredientsFragment() {
    }

    // Inflates the detail view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);

        recyclerView = rootView.findViewById(R.id.rv_detail_ingredients);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ingredientsAdapter = new IngredientsAdapter(currentRecipe.getIngredients());
        recyclerView.setAdapter(ingredientsAdapter);


        // Return the root view
        return rootView;
    }

    public void setCurrentRecipe(Recipes currentRecipe){
        this.currentRecipe = currentRecipe;
    }

    public void setContext(Context context){
        this.context = context;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {


    }
}
