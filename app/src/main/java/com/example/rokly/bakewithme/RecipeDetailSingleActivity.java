package com.example.rokly.bakewithme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.rokly.bakewithme.data.Recipes;
import com.example.rokly.bakewithme.data.Steps;

public class RecipeDetailSingleActivity extends AppCompatActivity {
    private static Steps steps;
    private static Recipes ingredients;
    private static Recipes currentRecipe;
    private static int position;
    public static final String POSITION = "position" ;
    public static final String STEPS = "steps";
    public static final String INGREDIENTS = "ingredients";
    public static final String RECIPE_NAME = "recipeName";

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        if(intent.hasExtra(RECIPE_NAME)){
            getSupportActionBar().setTitle(intent.getStringExtra(RECIPE_NAME));
        }

        if (intent.hasExtra(STEPS)){
            currentRecipe = intent.getParcelableExtra(STEPS);
            position = intent.getIntExtra(POSITION, 0);
            RecipeDetailSingleStepsFragment recipeDetailSingleStepsFragment = new RecipeDetailSingleStepsFragment();
            recipeDetailSingleStepsFragment.setCurrentStep(currentRecipe.getSteps().get(position));
            recipeDetailSingleStepsFragment.setContext(this);
            setContentView(R.layout.activity_recipe_single_detail);
        }else if(intent.hasExtra(INGREDIENTS)){
            ingredients = intent.getParcelableExtra(INGREDIENTS);
            RecipeDetailIngredientsFragment recipeDetailIngredientsFragment = new RecipeDetailIngredientsFragment();
            recipeDetailIngredientsFragment.setCurrentRecipe(ingredients);
            recipeDetailIngredientsFragment.setContext(this);
            setContentView(R.layout.activity_recipe_detail_ingredients);
        }
    }
}
