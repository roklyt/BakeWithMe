package com.example.rokly.bakewithme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rokly.bakewithme.Adapter.StepsAdapter;
import com.example.rokly.bakewithme.data.Recipes;
import com.example.rokly.bakewithme.data.Steps;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailStepsIngredientsFragment.OnImageClickListener{

    private Recipes currentRecipe;
    private StepsAdapter StepsAdapter;
    private RecyclerView RecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent recipeIntent = getIntent();
        if (recipeIntent.hasExtra(Recipes.PARCELABLE_KEY)) {
            /* Get the current recipe data from the intent*/
            currentRecipe = recipeIntent.getParcelableExtra(Recipes.PARCELABLE_KEY);

        }

        setContentView(R.layout.activity_recipe_detail);
    }

    public Recipes getCurrentRecipe(){
        return currentRecipe;
    }

    @Override
    public void onImageSelected(Steps currentStep) {
        Toast.makeText(this, "RecipeDetailActivity " + currentStep.getShortDescription(), Toast.LENGTH_LONG).show();

        final Intent intent = new Intent(this, RecipeDetailSingleActivity.class);
        intent.putExtra(Recipes.PARCELABLE_KEY, currentStep);
        startActivity(intent);
    }
}
