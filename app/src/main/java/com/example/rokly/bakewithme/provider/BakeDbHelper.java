package com.example.rokly.bakewithme.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rokly.bakewithme.provider.BakeContract.BakeEntry;

public class BakeDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bakewithme.db";

    private static final int DATABASE_VERSION = 1;

    // Constructor
    public BakeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold the bake data
        final String SQL_CREATE_BAKE_TABLE = "CREATE TABLE " + BakeEntry.TABLE_NAME + " (" +
                BakeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                BakeEntry.COLUMN_INGREDIENT + " STRING NOT NULL, " +
                BakeEntry.COLUMN_QUANTITY + " STRING NOT NULL, " +
                BakeEntry.COLUMN_MEASURE + " STRING NOT NULL)";

        sqLiteDatabase.execSQL(SQL_CREATE_BAKE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BakeEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
