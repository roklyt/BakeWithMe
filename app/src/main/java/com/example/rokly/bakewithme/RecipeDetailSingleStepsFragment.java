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

import java.util.ArrayList;

public class RecipeDetailSingleStepsFragment extends Fragment{

    private static Steps currentStep;
    private TextView textView;

    // Mandatory empty constructor
    public RecipeDetailSingleStepsFragment() {
    }

    // Inflates the detail view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_recipe_single_detail, container, false);

        textView = rootView.findViewById(R.id.tv_instruction_step);
        textView.setText(currentStep.getDescription());
        // Return the root view
        return rootView;
    }

    public void setCurrentStep(Steps currentStep){
        this.currentStep = currentStep;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {


    }
}
