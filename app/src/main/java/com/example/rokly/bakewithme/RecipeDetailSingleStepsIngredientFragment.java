package com.example.rokly.bakewithme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rokly.bakewithme.Adapter.StepsAdapter;
import com.example.rokly.bakewithme.data.Recipes;
import com.example.rokly.bakewithme.data.Steps;

public class RecipeDetailSingleStepsIngredientFragment extends Fragment{

    private Steps currentStep;
    private TextView textView;

    // Mandatory empty constructor
    public RecipeDetailSingleStepsIngredientFragment() {
    }

    // Inflates the detail view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecipeDetailSingleActivity recipeDetailSingleActivity = new RecipeDetailSingleActivity();
        currentStep = recipeDetailSingleActivity.getSteps();

        final View rootView = inflater.inflate(R.layout.fragment_recipe_single_detail, container, false);

        textView = rootView.findViewById(R.id.tv_instruction_step);
        textView.setText(currentStep.getDescription());
        // Return the root view
        return rootView;
    }


}
