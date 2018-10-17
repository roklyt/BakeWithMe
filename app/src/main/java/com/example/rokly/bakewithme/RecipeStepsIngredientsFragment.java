package com.example.rokly.bakewithme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecipeStepsIngredientsFragment extends Fragment {

    // Mandatory empty constructor
    public RecipeStepsIngredientsFragment() {
    }

    // Inflates the detail view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);


        // Return the root view
        return rootView;
    }

}
