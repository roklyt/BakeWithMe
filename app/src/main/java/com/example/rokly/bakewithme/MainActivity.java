package com.example.rokly.bakewithme;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rokly.bakewithme.Adapter.RecipeAdapter;
import com.example.rokly.bakewithme.data.Ingredients;
import com.example.rokly.bakewithme.data.Recipes;
import com.example.rokly.bakewithme.provider.BakeContract;
import com.example.rokly.bakewithme.utilities.NetworkUtils;
import com.example.rokly.bakewithme.utilities.RecipeJsonUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements com.example.rokly.bakewithme.Adapter.RecipeAdapter.RecipeAdapterOnClickHandler {

    /* RecycleView adapter*/
    private RecipeAdapter RecipeAdapter;
    /* recyclerView to populate all recipes*/
    private RecyclerView RecyclerView;
    /* List of all recipes*/
    private static ArrayList<Recipes> RecipesList = new ArrayList<>();
    /* Error text view*/
    private TextView ErrorMessageDisplay;
    /* Progress bar as indicator */
    private ProgressBar LoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* find all views */
        ErrorMessageDisplay = findViewById(R.id.error_message);


        LoadingIndicator = findViewById(R.id.pb_loading_indicator);
        if ((RecyclerView = findViewById(R.id.recyclerview_main_linear)) != null) {
            RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            RecyclerView = findViewById(R.id.recyclerview_main_grid);
            RecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }


        if (savedInstanceState != null && savedInstanceState.containsKey(Recipes.PARCELABLE_KEY)) {
            List<Recipes> recipes = savedInstanceState.getParcelableArrayList(Recipes.PARCELABLE_KEY);
            RecipeAdapter = new RecipeAdapter(this, recipes);
            RecyclerView.setAdapter(RecipeAdapter);
            LoadingIndicator.setVisibility(View.GONE);
        } else {
            RecipeAdapter = new RecipeAdapter(this, RecipesList);
            RecyclerView.setAdapter(RecipeAdapter);

            /* If network is available proceed else show error message */
            if (checkNetwork()) {
                loadRecipes();
            } else {
                Toast.makeText(this, getString(R.string.error_no_network), Toast.LENGTH_LONG).show();
                showErrorMessage();
            }
        }

    }

    /* Check network */
    private boolean checkNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();

    }

    private void showRecipeDataView() {
        /* First, make sure the error is invisible */
        ErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the bake data is visible */
        RecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        RecyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        ErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void loadRecipes() {
        new FetchRecipesTask().execute();
    }

    private void writeIngredientsToDatabase(Recipes currentRecipe) {
        getContentResolver().delete(BakeContract.BakeEntry.CONTENT_URI, null, null);

        ContentValues contentValues = new ContentValues();
        contentValues.put(BakeContract.BakeEntry.COLUMN_INGREDIENT, currentRecipe.getName());
        contentValues.put(BakeContract.BakeEntry.COLUMN_MEASURE, "");
        contentValues.put(BakeContract.BakeEntry.COLUMN_QUANTITY, "");
        getContentResolver().insert(BakeContract.BakeEntry.CONTENT_URI, contentValues);


        for (Ingredients ingredients : currentRecipe.getIngredients()) {
            contentValues = new ContentValues();
            contentValues.put(BakeContract.BakeEntry.COLUMN_INGREDIENT, ingredients.getIngredient());
            contentValues.put(BakeContract.BakeEntry.COLUMN_MEASURE, ingredients.getMeasure());
            contentValues.put(BakeContract.BakeEntry.COLUMN_QUANTITY, ingredients.getQuantity());
            getContentResolver().insert(BakeContract.BakeEntry.CONTENT_URI, contentValues);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<Recipes> recipesContents = RecipesList;
        outState.putParcelableArrayList(Recipes.PARCELABLE_KEY, recipesContents);
    }

    @Override
    public void onClick(Recipes currentRecipe) {
        writeIngredientsToDatabase(currentRecipe);

        BakeWidgetService.startActionUpdateBakeWidget(this, currentRecipe);

        Intent intentToStartDetailActivity = new Intent(this, RecipeDetailActivity.class);
        intentToStartDetailActivity.putExtra(Recipes.PARCELABLE_KEY, currentRecipe);
        intentToStartDetailActivity.putExtra(BakeAppWidgetProvider.OPENED_FROM_WIDGET, false);
        startActivity(intentToStartDetailActivity);
    }

    /* Async Task to make an url request against the given link to get the recipe list*/
    public class FetchRecipesTask extends AsyncTask<String, Void, List<Recipes>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Recipes> doInBackground(String... params) {

            /* build the url */
            URL recipeRequestUrl = NetworkUtils.buildUrl();

            try {
                String jsonRecipeResponse = NetworkUtils
                        .getResponseFromHttpUrl(recipeRequestUrl);

                return RecipeJsonUtil
                        .getRecipeListFromJson(jsonRecipeResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Recipes> recipeData) {
            /* Hide the loading bar */
            LoadingIndicator.setVisibility(View.INVISIBLE);
            if (recipeData != null) {
                RecipesList = new ArrayList<>(recipeData);
                showRecipeDataView();
                /* set the new data to the adapter */
                RecipeAdapter.setRecipeData(recipeData);
            } else {
                showErrorMessage();
            }
        }
    }
}
