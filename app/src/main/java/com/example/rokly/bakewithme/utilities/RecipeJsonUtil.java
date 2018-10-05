package com.example.rokly.bakewithme.utilities;

import com.example.rokly.bakewithme.data.Recipes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeJsonUtil {

    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing the recipe.
     *
     * @param recipeJsonStr JSON response from server
     * @return Array of Strings describing recipe link
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static List<Recipes> getRecipeListFromJson(String recipeJsonStr)
            throws JSONException {

        /* Recipe Id */
        final String RECIPE_ID = "id";

        /* Title object */
        final String RECIPE_NAME = "name";


        JSONArray jsonArray = new JSONArray(recipeJsonStr);

        List<Recipes> recipes = new ArrayList<>();

        //Iterate through all array objects and grep the movie data
        for (int i = 0; i < jsonArray.length(); i++) {
            String id;
            String name;

            /* Get the JSON object representing one  */
            JSONObject recipeObject = jsonArray.getJSONObject(i);

            /* Get the id */
            id = recipeObject.getString(RECIPE_ID);

            /* Get the title */
            name = recipeObject.getString(RECIPE_NAME);

            // Add the movie to the Movies List
            recipes.add(new Recipes(id, name));
        }
        return recipes;
    }
}