package com.example.rokly.bakewithme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rokly.bakewithme.data.Recipes;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);


        Intent recipeIntent = getIntent();
        if (recipeIntent.hasExtra(Recipes.PARCELABLE_KEY)) {
            /* Get the current recipe data from the intent*/
            final Recipes currentRecipe = recipeIntent.getParcelableExtra(Recipes.PARCELABLE_KEY);
        }

        



    }
}
