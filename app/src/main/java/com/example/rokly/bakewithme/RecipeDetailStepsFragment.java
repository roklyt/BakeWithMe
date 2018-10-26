package com.example.rokly.bakewithme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.util.List;

public class RecipeDetailStepsFragment extends Fragment implements com.example.rokly.bakewithme.Adapter.StepsAdapter.StepsAdapterOnClickHandler{

    private static Recipes currentRecipe;
    private RecyclerView recyclerView;
    private StepsAdapter stepsAdapter;
    private TextView ingredientsTextView;

    private static final String CURRENT_RECIPE = "currentRecipe";

    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnImageClickListener mCallback;
    OnIngredientsClickListener iCallback;


    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnImageClickListener {
        void onImageSelected(int position, View view);
    }

    public interface OnIngredientsClickListener{
        void onIngredientsSelected(View view);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            iCallback = (OnIngredientsClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnIngredientsClickListener");
        }


        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnImageClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    // Mandatory empty constructor
    public RecipeDetailStepsFragment() {
    }

    // Inflates the detail view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState != null){
            currentRecipe = savedInstanceState.getParcelable(CURRENT_RECIPE);
        }

        RecipeDetailActivity recipeDetailActivity = (RecipeDetailActivity) getActivity();

        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        recyclerView = rootView.findViewById(R.id.rv_detail_steps);
        recyclerView.setLayoutManager(new LinearLayoutManager(recipeDetailActivity.getApplicationContext()));
        stepsAdapter = new StepsAdapter(this, currentRecipe.getSteps());
        recyclerView.setAdapter(stepsAdapter);

        ingredientsTextView = rootView.findViewById(R.id.tv_detail_ingredient);
        ingredientsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackground(-1);
                iCallback.onIngredientsSelected(v);
            }
        });

        // Return the root view
        return rootView;
    }

    @Override
    public void onClick(int position, View view) {
        setBackground(position);

        mCallback.onImageSelected(position, view);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_RECIPE, currentRecipe);
    }

    public void setCurrentRecipe(Recipes currentRecipe){
      this.currentRecipe = currentRecipe;
    }

    private void setBackground(int position){
        if(position == -1){
            for(int i = 0; i < recyclerView.getLayoutManager().getChildCount(); i++){
                recyclerView.getLayoutManager().getChildAt(i).findViewById(R.id.recipe_text_recyclerview).setBackgroundColor(getResources().getColor(R.color.colorTextIcons));
            }
        }else{
            //Remove the blue background from all recyclerview items and the igredients item
            ingredientsTextView.setBackgroundColor(getResources().getColor(R.color.colorTextIcons));

            for(int i = 0; i < recyclerView.getLayoutManager().getChildCount(); i++){
                recyclerView.getLayoutManager().getChildAt(i).findViewById(R.id.recipe_text_recyclerview).setBackgroundColor(getResources().getColor(R.color.colorTextIcons));
            }
        }
    }
}
