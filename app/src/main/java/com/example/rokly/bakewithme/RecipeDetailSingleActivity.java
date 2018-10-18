package com.example.rokly.bakewithme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.rokly.bakewithme.data.Recipes;
import com.example.rokly.bakewithme.data.Steps;

public class RecipeDetailSingleActivity extends AppCompatActivity {
    private Steps steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();

        if (intent.hasExtra(Recipes.PARCELABLE_KEY)){
            steps = intent.getParcelableExtra(Recipes.PARCELABLE_KEY);
        }

        setContentView(R.layout.activity_recipe_single_detail);

    }

    public Steps getSteps(){
        return steps;
    }
}
