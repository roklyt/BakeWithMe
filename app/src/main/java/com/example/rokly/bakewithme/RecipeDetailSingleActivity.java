package com.example.rokly.bakewithme;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rokly.bakewithme.data.Recipes;

public class RecipeDetailSingleActivity extends AppCompatActivity implements RecipeDetailSingleStepsFragment.OnButtonClickListener {
    static Recipes ingredients;
    private static Recipes currentRecipe;
    private static int position;
    public static final String POSITION = "position";
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

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        if (intent.hasExtra(RECIPE_NAME)) {
            getSupportActionBar().setTitle(intent.getStringExtra(RECIPE_NAME));
        }

        if (intent.hasExtra(STEPS)) {
            setContentView(R.layout.activity_recipe_detail_steps);
            if (savedInstanceState == null) {
                currentRecipe = intent.getParcelableExtra(STEPS);
                position = intent.getIntExtra(POSITION, 0);
                RecipeDetailSingleStepsFragment recipeDetailSingleStepsFragment = new RecipeDetailSingleStepsFragment();
                recipeDetailSingleStepsFragment.setCurrentStep(currentRecipe.getSteps().get(position));
                recipeDetailSingleStepsFragment.setPhone(true);
                recipeDetailSingleStepsFragment.setSize(currentRecipe.getSteps().size());
                recipeDetailSingleStepsFragment.setContext(this);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.step_container_single, recipeDetailSingleStepsFragment)
                        .commit();
            }
        } else if (intent.hasExtra(INGREDIENTS)) {
            if (savedInstanceState == null) {
                setContentView(R.layout.activity_recipe_detail_ingredients);
                ingredients = intent.getParcelableExtra(INGREDIENTS);
                RecipeDetailIngredientsFragment recipeDetailIngredientsFragment = new RecipeDetailIngredientsFragment();
                recipeDetailIngredientsFragment.setCurrentRecipe(ingredients);
                recipeDetailIngredientsFragment.setContext(this);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.ingredients_container_single, recipeDetailIngredientsFragment)
                        .commit();
            }else{
                setContentView(R.layout.activity_recipe_detail_ingredients);
                ingredients = intent.getParcelableExtra(INGREDIENTS);
                RecipeDetailIngredientsFragment recipeDetailIngredientsFragment = new RecipeDetailIngredientsFragment();
                recipeDetailIngredientsFragment.setCurrentRecipe(ingredients);
                recipeDetailIngredientsFragment.setContext(this);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ingredients_container_single, recipeDetailIngredientsFragment)
                        .commit();
            }
        }
    }


    @Override
    public void onButtonSelected(int buttonId) {
        calculateNewPosition(buttonId);
        RecipeDetailSingleStepsFragment recipeDetailSingleStepsFragment = new RecipeDetailSingleStepsFragment();
        switch (buttonId) {
            case R.id.back_button:
                recipeDetailSingleStepsFragment.setCurrentStep(currentRecipe.getSteps().get(position));
                recipeDetailSingleStepsFragment.setPhone(true);
                recipeDetailSingleStepsFragment.setSize(currentRecipe.getSteps().size());
                recipeDetailSingleStepsFragment.setContext(this);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_container_single, recipeDetailSingleStepsFragment)
                        .commit();
                break;
            case R.id.forward_button:
                recipeDetailSingleStepsFragment.setCurrentStep(currentRecipe.getSteps().get(position));
                recipeDetailSingleStepsFragment.setPhone(true);
                recipeDetailSingleStepsFragment.setSize(currentRecipe.getSteps().size());
                recipeDetailSingleStepsFragment.setContext(this);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_container_single, recipeDetailSingleStepsFragment)
                        .commit();
                break;
            default:
                break;
        }
    }

    private void calculateNewPosition(int buttonId) {
        int stepsSize = currentRecipe.getSteps().size();
        switch (buttonId) {
            case R.id.back_button:
                if (position == 0) {
                    position = stepsSize - 1;
                } else {
                    position = position - 1;
                }
                break;
            case R.id.forward_button:
                if (position == stepsSize - 1) {
                    position = 0;
                } else {
                    position = position + 1;
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt(POSITION, position);
    }

}
