package com.example.rokly.bakewithme.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class BakeContract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.rokly.bakewithme";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "plants" directory
    public static final String PATH_BAKE = "bake";

    public static final long INVALID_BAKE_ID = -1;

    public static final class BakeEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_BAKE).build();

        public static final String TABLE_NAME = "bake";
        public static final String COLUMN_INGREDIENT = "ingredient";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_MEASURE = "measure";
    }
}