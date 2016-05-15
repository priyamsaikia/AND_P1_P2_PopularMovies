package apps.orchotech.com.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by PriyamSaikia on 15-05-2016.
 */

public class ReadDatabase {
    void readDatabase(Context context) {
        MoviesDBHelper weatherDbHelper = new MoviesDBHelper(context);
        SQLiteDatabase db = weatherDbHelper.getWritableDatabase();
        ContentValues cv = null;//initialise content values proerly
        long locationRowId = db.insert(MoviesContract.FavouritesEntry.TABLE_NAME, null, cv);
        if (locationRowId == -1)
            return; //query failed

        Cursor cursor = db.query(MoviesContract.FavouritesEntry.TABLE_NAME,
                null, //all columns
                null, //columns for the where clause
                null, //values for the where clause
                null, //columns to group by
                null, //columns to filter by row groups
                null //sort order
        );
    }
}
