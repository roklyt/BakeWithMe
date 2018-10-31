package com.example.rokly.bakewithme;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.rokly.bakewithme.data.Ingredients;
import com.example.rokly.bakewithme.data.Recipes;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakeAppWidgetProvider extends AppWidgetProvider {
    private static Intent intent;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, Recipes currentRecipe,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views;

        Intent intent;


        if(currentRecipe == null){
            views = new RemoteViews(context.getPackageName(), R.layout.bake_app_widget_provider);

            views.setTextViewText(R.id.widget_textview, "Bakw with me");
            intent  = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setPendingIntentTemplate(R.id.list_widget_view, pendingIntent);
        }else{
            views = getBakeListRemoteView(context, currentRecipe);
        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static RemoteViews getBakeListRemoteView(Context context, Recipes currentRecipe) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bake_app_widget_list);
        // Set the ListWidgetService intent to act as the adapter for the ListView
        intent = new Intent(context, ListWidgetService.class);

        ArrayList<String> ingredientsArrayList = new ArrayList<String>();
        ingredientsArrayList.add(currentRecipe.getName());
        ArrayList<String> quantityArrayList = new ArrayList<String>();
        quantityArrayList.add("");
        ArrayList<String> measureIdArrayList = new ArrayList<String>();
        measureIdArrayList.add("");

        for(Ingredients ingredient:currentRecipe.getIngredients()){
            ingredientsArrayList.add(ingredient.getIngredient());
            quantityArrayList.add(ingredient.getQuantity());
            measureIdArrayList.add(ingredient.getMeasure());
        }

        intent.putStringArrayListExtra(ListWidgetService.INGREDIENT_ARRAY_LIST,ingredientsArrayList);
        intent.putStringArrayListExtra(ListWidgetService.QUANTITY_ARRAY_LIST,quantityArrayList);
        intent.putStringArrayListExtra(ListWidgetService.MEASUERE_ARRAY_LIST,measureIdArrayList);
        views.removeAllViews(R.id.list_widget_view);
        views.setRemoteAdapter(R.id.list_widget_view, intent);

        // Set the RecipeDetailActivity intent to launch when clicked
        Intent appIntent = new Intent(context, RecipeDetailActivity.class);
        appIntent.putExtra(BakeWidgetService.INGREDIENTS_EXTRA, currentRecipe);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.list_widget_view, appPendingIntent);
        // Handle empty list
        views.setEmptyView(R.id.list_widget_view, R.id.empty_view);


        return views;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, null, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void updateBakeWidgets(Context context, AppWidgetManager appWidgetManager,
                                         Recipes recipes, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipes, appWidgetId);
        }
    }
}

