package com.example.rokly.bakewithme;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.rokly.bakewithme.Adapter.IngredientsAdapter;
import com.example.rokly.bakewithme.data.Ingredients;
import com.example.rokly.bakewithme.data.Recipes;
import com.example.rokly.bakewithme.provider.BakeContract;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import static com.example.rokly.bakewithme.provider.BakeContract.BASE_CONTENT_URI;
import static com.example.rokly.bakewithme.provider.BakeContract.PATH_BAKE;

public class ListWidgetService extends RemoteViewsService {
    public static final String INGREDIENT_ARRAY_LIST = "ingredientsArrayList";
    public static final String QUANTITY_ARRAY_LIST = "measureArrayList";
    public static final String MEASUERE_ARRAY_LIST = "measureIdArrayList";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        ArrayList<String> ingredients = intent.getStringArrayListExtra(INGREDIENT_ARRAY_LIST);
        ArrayList<String> measure = intent.getStringArrayListExtra(MEASUERE_ARRAY_LIST);
        ArrayList<String> quantity = intent.getStringArrayListExtra(QUANTITY_ARRAY_LIST);

        return new ListRemoteViewsFactory(this.getApplicationContext(), ingredients, measure, quantity);
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    Cursor cursor;
    public static ArrayList<String>  ingredients;
    public static ArrayList<String>  measure;
    public static ArrayList<String>  quantity;

    public ListRemoteViewsFactory(Context context,ArrayList<String> ingredients, ArrayList<String> measure, ArrayList<String> quantity){
        this.context = context;
        this.ingredients = ingredients;
        this.measure = measure;
        this.quantity = quantity;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        // Get all plant info ordered by creation time
        Uri BAKE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BAKE).build();
        if (cursor != null) cursor.close();
        cursor = context.getContentResolver().query(
                BAKE_URI,
                null,
                null,
                null,
                BakeContract.BakeEntry.COLUMN_INGREDIENT
        );

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(cursor == null) return 0;
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (cursor == null || cursor.getCount() == 0) return null;
        cursor.moveToPosition(i);
        int ingredientsIndex = cursor.getColumnIndex(BakeContract.BakeEntry.COLUMN_INGREDIENT);
        int measureIndex = cursor.getColumnIndex(BakeContract.BakeEntry.COLUMN_MEASURE);
        int quantityIndex = cursor.getColumnIndex(BakeContract.BakeEntry.COLUMN_QUANTITY);



        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_row);
        //views.setTextViewText(R.id.tv_ingredients_quantity, );
        //views.setImageViewResource(R.id.iv_ingredients_measure, IngredientsAdapter.getMeasureImage(currenRecipe.getIngredients().get(i).getMeasure()));
        Log.e("getViewAt", ingredients.get(i));
        if(cursor.getString(measureIndex).equals("")){
            views.setTextViewText(R.id.widget_text_ingredient, cursor.getString(ingredientsIndex));
            views.setTextColor(R.id.widget_text_ingredient, context.getResources().getColor(R.color.colorTextIcons));
            views.setInt(R.id.list_row_main,"setBackgroundResource", R.color.colorPrimaryDark);
            views.setImageViewResource(R.id.widget_image_measure, IngredientsAdapter.getMeasureImage(cursor.getString(measureIndex)));
            views.setViewVisibility(R.id.widget_image_measure, View.GONE);
            views.setTextViewText(R.id.widget_text_quantity, cursor.getString(quantityIndex));
            views.setViewVisibility(R.id.widget_text_quantity, View.GONE);
        }else{
            views.setTextColor(R.id.widget_text_ingredient, context.getResources().getColor(R.color.colorPrimaryText));
            views.setInt(R.id.list_row_main,"setBackgroundResource", R.color.colorTextIcons);
            views.setViewVisibility(R.id.widget_image_measure, View.VISIBLE);
            views.setViewVisibility(R.id.widget_text_quantity, View.VISIBLE);
            views.setTextViewText(R.id.widget_text_ingredient, cursor.getString(ingredientsIndex));
            views.setImageViewResource(R.id.widget_image_measure, IngredientsAdapter.getMeasureImage(cursor.getString(measureIndex)));
            views.setTextViewText(R.id.widget_text_quantity, cursor.getString(quantityIndex));
        }

        // Fill in the onClick PendingIntent Template using the specific plant Id for each item individually

        Intent fillInIntent = new Intent(context, MainActivity.class);
        fillInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        views.setOnClickFillInIntent(R.id.tv_ingredients_ingredient, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public static void setIngredients(ArrayList<String> ingredients) {
        ListRemoteViewsFactory.ingredients = ingredients;
    }
}
