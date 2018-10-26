package com.example.rokly.bakewithme.utilities;

import com.example.rokly.bakewithme.data.Ingredients;
import com.example.rokly.bakewithme.data.Recipes;
import com.example.rokly.bakewithme.data.Steps;

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

        /* Name object */
        final String RECIPE_NAME = "name";

        /* Ingredient object */
        final String RECIPE_INGREDIENTS = "ingredients";
        final String INGREDIENT_QUANTITY= "quantity";
        final String INGREDIENT_MEASURE= "measure";
        final String INGREDIENT_INGREDIENT = "ingredient";

        /* Steps object*/
        final String RECIPE_STEPS = "steps";
        final String STEP_ID = "id";
        final String STEP_SHORT_DESCRIPTION = "shortDescription";
        final String STEP_DESCRIPTION = "description";
        final String STEP_VIDEO_URL = "videoURL";

        JSONArray jsonArray = new JSONArray(recipeJsonStr);

        List<Recipes> recipes = new ArrayList<>();

        //Iterate through all array objects and grep the movie data
        for (int i = 0; i < jsonArray.length(); i++) {
            List<Ingredients> ingredients = new ArrayList<>();
            List<Steps> steps = new ArrayList<>();

            /* Get the JSON object representing one  */
            JSONObject recipeObject = jsonArray.getJSONObject(i);

            /* Get the id */
            String id = recipeObject.getString(RECIPE_ID);

            /* Get the title */
            String name = recipeObject.getString(RECIPE_NAME);

            /* Get the list of ingredients*/
            JSONArray ingredientArray = recipeObject.getJSONArray(RECIPE_INGREDIENTS);
            for(int x = 0; x < ingredientArray.length(); x++){
                JSONObject ingredientObject = ingredientArray.getJSONObject(x);

                String quantity = getQuantity(ingredientObject.getDouble(INGREDIENT_QUANTITY));
                String measure = ingredientObject.getString(INGREDIENT_MEASURE);
                String ingredient = ingredientObject.getString(INGREDIENT_INGREDIENT);

                ingredients.add(new Ingredients(quantity, measure, ingredient));
            }

            /* Get the list of steps*/
            JSONArray stepsArray = recipeObject.getJSONArray(RECIPE_STEPS);
            for(int x = 0; x < stepsArray.length(); x++){
                JSONObject stepObject = stepsArray.getJSONObject(x);

                int stepid = stepObject.getInt(STEP_ID);
                String shortDescription = stepObject.getString(STEP_SHORT_DESCRIPTION);
                String description = stepObject.getString(STEP_DESCRIPTION);
                String videoUrl = stepObject.getString(STEP_VIDEO_URL);

                steps.add(new Steps(stepid, shortDescription, description, videoUrl));
            }

            // Add the recipe to the Recipes List
            recipes.add(new Recipes(id, name, ingredients, steps));
        }
        return recipes;
    }

    private static String getQuantity(double num){
        long iPart;
        double fPart;
        String returnString;

        iPart = (long) num;
        fPart = num - iPart;

        if(fPart == 0){
            returnString = String.valueOf(iPart);
        }else{
            returnString = String.valueOf(num);
        }

        return returnString;
    }
}