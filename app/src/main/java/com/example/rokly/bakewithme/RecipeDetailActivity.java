package com.example.rokly.bakewithme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.rokly.bakewithme.Adapter.StepsAdapter;
import com.example.rokly.bakewithme.data.Recipes;
import com.example.rokly.bakewithme.data.Steps;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailStepsFragment.OnImageClickListener{

    private Recipes currentRecipe;
    private StepsAdapter StepsAdapter;
    private RecyclerView RecyclerView;


    private boolean twoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent recipeIntent = getIntent();
        if (recipeIntent.hasExtra(Recipes.PARCELABLE_KEY)) {
            /* Get the current recipe data from the intent*/
            currentRecipe = recipeIntent.getParcelableExtra(Recipes.PARCELABLE_KEY);
            RecipeDetailStepsFragment recipeDetailStepsFragment = new RecipeDetailStepsFragment();
            recipeDetailStepsFragment.setCurrentRecipe(currentRecipe);

        }

        RecipeDetailSingleStepsFragment recipeDetailSingleStepsFragment = new RecipeDetailSingleStepsFragment();
        recipeDetailSingleStepsFragment.setCurrentStep(currentRecipe.getSteps().get(0));

        setContentView(R.layout.activity_recipe_detail);

        if(findViewById(R.id.step_container) != null){
            twoPane = true;

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_container, recipeDetailSingleStepsFragment)
                    .commit();
        }else{
            twoPane = false;
        }


    }


    @Override
    public void onImageSelected(Steps currentStep) {
        Toast.makeText(this, "RecipeDetailActivity " + currentStep.getShortDescription(), Toast.LENGTH_LONG).show();


        if(twoPane){
            showSteps();
            RecipeDetailSingleStepsFragment recipeDetailSingleStepsFragment = new RecipeDetailSingleStepsFragment();
            recipeDetailSingleStepsFragment.setCurrentStep(currentStep);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_container, recipeDetailSingleStepsFragment)
                    .commit();

        }else{
            final Intent intent = new Intent(this, RecipeDetailSingleActivity.class);
            intent.putExtra(RecipeDetailSingleActivity.STEPS, currentStep);
            startActivity(intent);
        }
    }

    public void onClickIngredients(View view){
        Toast.makeText(this, "onImageSelected " + "CLicked", Toast.LENGTH_LONG).show();


        if(twoPane){
            showIngredients();
            RecipeDetailIngredientsFragment recipeDetailIngredientsFragment = new RecipeDetailIngredientsFragment();
            recipeDetailIngredientsFragment.setContext(this);
            recipeDetailIngredientsFragment.setCurrentRecipe(currentRecipe);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.ingredients_container, recipeDetailIngredientsFragment)
                    .commit();


        }else{
            final Intent intent = new Intent(this, RecipeDetailSingleActivity.class);
            intent.putExtra(RecipeDetailSingleActivity.INGREDIENTS, currentRecipe);
            startActivity(intent);
        }
    }

    private void showIngredients(){
        FrameLayout stepsFrameLayout = findViewById(R.id.step_container);
        stepsFrameLayout.setVisibility(View.GONE);


        FrameLayout ingredientsFrameLayout = findViewById(R.id.ingredients_container);
        ingredientsFrameLayout.setVisibility(View.VISIBLE);
    }

    private void showSteps(){
        FrameLayout ingredientsFrameLayout = findViewById(R.id.ingredients_container);
        ingredientsFrameLayout.setVisibility(View.GONE);

        FrameLayout stepsFrameLayout = findViewById(R.id.step_container);
        stepsFrameLayout.setVisibility(View.VISIBLE);
    }
}
