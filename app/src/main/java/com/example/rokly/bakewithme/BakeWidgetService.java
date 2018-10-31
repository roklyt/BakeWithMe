package com.example.rokly.bakewithme;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.example.rokly.bakewithme.data.Recipes;

public class BakeWidgetService extends IntentService {

    public static final String ACTION_UPDATE_BAKE_WIDGETS = "com.example.android.mygarden.action.update_plant_widgets";
    public static final String INGREDIENTS_EXTRA = "ingredients_recipe";

    public BakeWidgetService() {
        super("BakeWidgetService");
    }



    /**
     * Starts this service to perform UpdatePlantWidgets action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdateBakeWidget(Context context, Recipes currentRecipe) {

        Intent intent = new Intent(context, BakeWidgetService.class);
        intent.setAction(ACTION_UPDATE_BAKE_WIDGETS);
        intent.putExtra(INGREDIENTS_EXTRA, currentRecipe);
        context.startService(intent);
    }

    /**
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_BAKE_WIDGETS.equals(action)) {
                Recipes currentRecipe = intent.getParcelableExtra(INGREDIENTS_EXTRA);
                handleActionUpdatePlantWidgets(currentRecipe);
            }
        }
    }




    /**
     * Handle action UpdatePlantWidgets in the provided background thread
     */
    private void handleActionUpdatePlantWidgets(Recipes currentRecipe) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakeAppWidgetProvider.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_widget_view);
        //Now update all widgets
        BakeAppWidgetProvider.updateBakeWidgets(this, appWidgetManager, currentRecipe,appWidgetIds);


    }
}
