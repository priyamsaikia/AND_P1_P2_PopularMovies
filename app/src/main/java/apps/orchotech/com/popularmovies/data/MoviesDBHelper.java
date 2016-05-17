package apps.orchotech.com.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoviesDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;

    static final String DATABASE_NAME = "test06.db";

    public MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_POSTERLINK_TABLE = "CREATE TABLE " + MoviesContract.MoviesEntry.TABLE_NAME + " (" +
                MoviesContract.MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MoviesContract.MoviesEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL," +
                MoviesContract.MoviesEntry.COLUMN_POSTER_LINK + " TEXT NOT NULL);";

        final String SQL_CREATE_FAVOURITES_TABLE = "CREATE TABLE " + MoviesContract.FavouritesEntry.TABLE_NAME + " (" +
                MoviesContract.FavouritesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MoviesContract.FavouritesEntry.COLUMN_MOVIE_ID + " TEXT UNIQUE NOT NULL," +
                MoviesContract.FavouritesEntry.COLUMN_MOVIE_NAME + " TEXT NOT NULL," +
                MoviesContract.FavouritesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL," +
                MoviesContract.FavouritesEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL," +
                MoviesContract.FavouritesEntry.COLUMN_VOTE_AVERAGE + " TEXT NOT NULL," +
                MoviesContract.FavouritesEntry.COLUMN_RUN_TIME + " TEXT NOT NULL," +
                MoviesContract.FavouritesEntry.COLUMN_POSTER_LINK + " TEXT ," +
                MoviesContract.FavouritesEntry.COLUMN_BANNER_LINK + " TEXT "
                + " );";
        sqLiteDatabase.execSQL(SQL_CREATE_POSTERLINK_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FAVOURITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MoviesEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.FavouritesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
