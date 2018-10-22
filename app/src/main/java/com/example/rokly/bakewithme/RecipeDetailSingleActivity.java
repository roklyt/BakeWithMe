package com.example.rokly.bakewithme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rokly.bakewithme.data.Recipes;
import com.example.rokly.bakewithme.data.Steps;

public class RecipeDetailSingleActivity extends AppCompatActivity {
    private static Steps steps;
    private static Recipes ingredients;
    public static final String STEPS = "steps";
    public static final String INGREDIENTS = "ingredients";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();

        if (intent.hasExtra(STEPS)){
            steps = intent.getParcelableExtra(STEPS);
            RecipeDetailSingleStepsFragment recipeDetailSingleStepsFragment = new RecipeDetailSingleStepsFragment();
            recipeDetailSingleStepsFragment.setCurrentStep(steps);
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
