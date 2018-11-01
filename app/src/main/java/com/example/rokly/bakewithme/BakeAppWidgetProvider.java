package com.example.rokly.bakewithme;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.rokly.bakewithme.data.Recipes;
import com.example.rokly.bakewithme.provider.BakeContract;

import static com.example.rokly.bakewithme.provider.BakeContract.BASE_CONTENT_URI;
import static com.example.rokly.bakewithme.provider.BakeContract.PATH_BAKE;

/**
 * Implementation of App Widget functionality.
 */
public class BakeAppWidgetProvider extends AppWidgetProvider {

    public final static String OPENED_FROM_WIDGET = "openedFromWidget";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, Recipes currentRecipe,
                                int appWidgetId, boolean hasRecipe) {

        // Construct the RemoteViews object
        RemoteViews views;

        Intent intent;

        if (!hasRecipe) {
            views = new RemoteViews(context.getPackageName(), R.layout.bake_app_widget_provider);

            views.setTextViewText(R.id.widget_textview, "Choose a recipe");
            intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widget_textview, pendingIntent);
        } else {
            views = getBakeListRemoteView(context, currentRecipe);
        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static RemoteViews getBakeListRemoteView(Context context, Recipes currentRecipe) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bake_app_widget_list);
        // Set the ListWidgetService intent to act as the adapter for the ListView
        Intent intent = new Intent(context, ListWidgetService.class);
        views.setRemoteAdapter(R.id.list_widget_view, intent);

        // Set the RecipeDetailActivity intent to launch when clicked
        Intent appIntent;
        if (currentRecipe == null) {
            appIntent = new Intent(context, MainActivity.class);
        } else {
            appIntent = new Intent(context, RecipeDetailActivity.class);
            appIntent.putExtra(Recipes.PARCELABLE_KEY, currentRecipe);
            appIntent.putExtra(OPENED_FROM_WIDGET, true);
            appIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }


        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.list_widget_view, appPendingIntent);
        // Handle empty list
        views.setEmptyView(R.id.list_widget_view, R.id.empty_view);


        return views;
    }

    public static void updateBakeWidgets(Context context, AppWidgetManager appWidgetManager,
                                         Recipes recipes, int[] appWidgetIds, boolean hasRecipe) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipes, appWidgetId, hasRecipe);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Uri BAKE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BAKE).build();

        Cursor cursor = context.getContentResolver().query(
                BAKE_URI,
                null,
                null,
                null,
                BakeContract.BakeEntry.COLUMN_INGREDIENT
        );

        boolean hasRecipe = cursor.getCount() > 0;
        cursor.close();

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, null, appWidgetId, hasRecipe);
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
}

