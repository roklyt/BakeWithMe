package com.example.rokly.bakewithme.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.rokly.bakewithme.provider.BakeContract.BakeEntry;

public class BakeContentProvider extends ContentProvider {

    private static final String TAG = BakeContentProvider.class.getName();

    private BakeDbHelper bakeDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        bakeDbHelper = new BakeDbHelper(context);
        return true;
    }

    /***
     * Handles requests to insert a single new row of data
     *
     * @param uri
     * @param values
     * @return
     */
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = bakeDbHelper.getWritableDatabase();

        Uri returnUri;
        long id = db.insert(BakeEntry.TABLE_NAME, null, values);

        if (id > 0) {
            returnUri = ContentUris.withAppendedId(BakeEntry.CONTENT_URI, id);
        } else {
            throw new android.database.SQLException("Failed to insert row into " + uri);
        }


        // Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri;
    }

    /***
     * Handles requests for data by URI
     *
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Get access to underlying database (read-only for query)
        final SQLiteDatabase db = bakeDbHelper.getReadableDatabase();


        Cursor retCursor;


        retCursor = db.query(BakeEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);


        // Set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the desired Cursor
        return retCursor;
    }

    /***
     * Deletes a single row of data
     *
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return number of rows affected
     */
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = bakeDbHelper.getWritableDatabase();

        int bakesDeleted; // starts as 0


        bakesDeleted = db.delete(BakeEntry.TABLE_NAME, null, null);

        if (bakesDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return bakesDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
