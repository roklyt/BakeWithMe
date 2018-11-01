package com.example.rokly.bakewithme;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.rokly.bakewithme.Adapter.IngredientsAdapter;
import com.example.rokly.bakewithme.provider.BakeContract;

import static com.example.rokly.bakewithme.provider.BakeContract.BASE_CONTENT_URI;
import static com.example.rokly.bakewithme.provider.BakeContract.PATH_BAKE;

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {


        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Cursor cursor;

    public ListRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
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
        if (cursor == null) return 0;
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

        if (cursor.getString(measureIndex).equals("")) {
            views.setTextViewText(R.id.widget_text_ingredient, cursor.getString(ingredientsIndex));
            views.setTextColor(R.id.widget_text_ingredient, context.getResources().getColor(R.color.colorTextIcons));
            views.setInt(R.id.list_row_main, "setBackgroundResource", R.color.colorPrimaryDark);
            views.setImageViewResource(R.id.widget_image_measure, IngredientsAdapter.getMeasureImage(cursor.getString(measureIndex)));
            views.setViewVisibility(R.id.widget_image_measure, View.GONE);
            views.setTextViewText(R.id.widget_text_quantity, cursor.getString(quantityIndex));
            views.setViewVisibility(R.id.widget_text_quantity, View.GONE);
        } else {
            views.setTextColor(R.id.widget_text_ingredient, context.getResources().getColor(R.color.colorPrimaryText));
            views.setInt(R.id.list_row_main, "setBackgroundResource", R.color.colorTextIcons);
            views.setViewVisibility(R.id.widget_image_measure, View.VISIBLE);
            views.setViewVisibility(R.id.widget_text_quantity, View.VISIBLE);
            views.setTextViewText(R.id.widget_text_ingredient, cursor.getString(ingredientsIndex));
            views.setImageViewResource(R.id.widget_image_measure, IngredientsAdapter.getMeasureImage(cursor.getString(measureIndex)));
            views.setTextViewText(R.id.widget_text_quantity, cursor.getString(quantityIndex));
        }

        Intent fillInIntent = new Intent();
        views.setOnClickFillInIntent(R.id.list_row_main, fillInIntent);

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

}
