package com.example.rokly.bakewithme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.rokly.bakewithme.data.Recipes;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailStepsFragment.OnImageClickListener, RecipeDetailSingleStepsFragment.OnButtonClickListener {

    private Recipes currentRecipe;
    private static boolean twoPane = false;
    private static final String SHOW_STEPS = "showIngredients";
    private static final String STEPS_POSITION = "stepsPosition";
    private static boolean showStep;
    private static boolean openedFromWidget;
    private static int currentPosition;

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

        Intent recipeIntent = getIntent();
        if (recipeIntent.hasExtra(Recipes.PARCELABLE_KEY)) {
            /* Get the current recipe data from the intent*/
            if (recipeIntent.hasExtra(BakeWidgetService.INGREDIENTS_EXTRA)) {
                currentRecipe = recipeIntent.getParcelableExtra(BakeWidgetService.INGREDIENTS_EXTRA);
            } else {
                currentRecipe = recipeIntent.getParcelableExtra(Recipes.PARCELABLE_KEY);
            }

            openedFromWidget = recipeIntent.getBooleanExtra(BakeAppWidgetProvider.OPENED_FROM_WIDGET, false);

            if (savedInstanceState != null) {
                currentPosition = savedInstanceState.getInt(STEPS_POSITION);
            } else {
                currentPosition = 1;
            }

            RecipeDetailStepsFragment recipeDetailStepsFragment = new RecipeDetailStepsFragment();
            recipeDetailStepsFragment.setCurrentRecipe(currentRecipe);
            recipeDetailStepsFragment.setCurrentPosition(currentPosition);
            getSupportActionBar().setTitle(currentRecipe.getName());
        }

        setContentView(R.layout.activity_recipe_detail);

        if (findViewById(R.id.step_container) != null) {

            if (savedInstanceState == null) {
                twoPane = true;
                showStep = true;
                RecipeDetailSingleStepsFragment recipeDetailSingleStepsFragment = new RecipeDetailSingleStepsFragment();
                recipeDetailSingleStepsFragment.setCurrentStep(currentRecipe.getSteps().get(0));
                recipeDetailSingleStepsFragment.setPhone(false);
                recipeDetailSingleStepsFragment.setSize(currentRecipe.getSteps().size());

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.step_container, recipeDetailSingleStepsFragment)
                        .commit();

                showSteps();
            } else if (!savedInstanceState.getBoolean(SHOW_STEPS)) {
                showStep = false;
/*                RecipeDetailIngredientsFragment recipeDetailIngredientsFragment = new RecipeDetailIngredientsFragment();
                recipeDetailIngredientsFragment.setContext(this);
                recipeDetailIngredientsFragment.setCurrentRecipe(currentRecipe);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.ingredients_container, recipeDetailIngredientsFragment)
                        .commit();*/
                showIngredients();
            } else {
                showStep = true;
                showSteps();
            }
        } else {
            twoPane = false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (openedFromWidget) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    onBackPressed();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SHOW_STEPS, showStep);
        outState.putInt(STEPS_POSITION, currentPosition);
    }

    @Override
    public void onImageSelected(int position, View view) {
        currentPosition = position;

        if (position < 0) {
            if (twoPane) {

                showIngredients();
                RecipeDetailIngredientsFragment recipeDetailIngredientsFragment = new RecipeDetailIngredientsFragment();
                recipeDetailIngredientsFragment.setContext(this);
                recipeDetailIngredientsFragment.setCurrentRecipe(currentRecipe);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.ingredients_container, recipeDetailIngredientsFragment)
                        .commit();


            } else {
                final Intent intent = new Intent(this, RecipeDetailSingleActivity.class);
                intent.putExtra(RecipeDetailSingleActivity.INGREDIENTS, currentRecipe);
                intent.putExtra(RecipeDetailSingleActivity.RECIPE_NAME, currentRecipe.getName());
                startActivity(intent);
            }
        } else {

            if (twoPane) {
                showSteps();
                RecipeDetailSingleStepsFragment recipeDetailSingleStepsFragment = new RecipeDetailSingleStepsFragment();
                recipeDetailSingleStepsFragment.setCurrentStep(currentRecipe.getSteps().get(position));
                recipeDetailSingleStepsFragment.setPhone(false);
                recipeDetailSingleStepsFragment.setSize(currentRecipe.getSteps().size());

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.step_container, recipeDetailSingleStepsFragment)
                        .commit();

            } else {
                final Intent intent = new Intent(this, RecipeDetailSingleActivity.class);
                intent.putExtra(RecipeDetailSingleActivity.STEPS, currentRecipe);
                intent.putExtra(RecipeDetailSingleActivity.POSITION, position);
                intent.putExtra(RecipeDetailSingleActivity.RECIPE_NAME, currentRecipe.getName());
                startActivity(intent);
            }
        }

    }

    private void showIngredients() {
        showStep = false;
        FrameLayout stepsFrameLayout = findViewById(R.id.step_container);
        stepsFrameLayout.setVisibility(View.GONE);

        FrameLayout ingredientsFrameLayout = findViewById(R.id.ingredients_container);
        ingredientsFrameLayout.setVisibility(View.VISIBLE);
    }

    private void showSteps() {
        showStep = true;
        FrameLayout ingredientsFrameLayout = findViewById(R.id.ingredients_container);
        ingredientsFrameLayout.setVisibility(View.GONE);

        FrameLayout stepsFrameLayout = findViewById(R.id.step_container);
        stepsFrameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onButtonSelected(int buttonId) {

    }
}
