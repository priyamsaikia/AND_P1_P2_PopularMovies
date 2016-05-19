package apps.orchotech.com.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by PriyamSaikia on 19-05-2016.
 * <p/>
 * This file is created with reference and help from
 * Udacity's Content Provider Project on GitHub
 */
public class AppContentProvider extends ContentProvider {
    // Codes for the UriMatcher //////
    private static final int MOVIES_ENTRY = 100;
    private static final int MOVIES_ENTRY_WITH_ID = 200;
    // Codes for the UriMatcher //////
    private static final int FAVORITES_ENTRY = 300;
    private static final int FAVORITES_ENTRY_WITH_ID = 400;
    private MoviesDBHelper mMovieDBHelper;

    public static UriMatcher buildAppUriMatcher() {
        // Build a UriMatcher by adding a specific code to return based on a match
        // It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MoviesContract.AUTHORITY;

        // add a code for each type of URI you want
        matcher.addURI(authority, MoviesContract.MoviesEntry.TABLE_NAME, MOVIES_ENTRY);
        matcher.addURI(authority, MoviesContract.MoviesEntry.TABLE_NAME + "/#", MOVIES_ENTRY_WITH_ID);
        matcher.addURI(authority, MoviesContract.FavouritesEntry.TABLE_NAME, FAVORITES_ENTRY);
        matcher.addURI(authority, MoviesContract.FavouritesEntry.TABLE_NAME + "/#", FAVORITES_ENTRY_WITH_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mMovieDBHelper = new MoviesDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (buildAppUriMatcher().match(uri)) {
            // All Flavors selected
            case MOVIES_ENTRY: {
                retCursor = mMovieDBHelper.getReadableDatabase().query(
                        MoviesContract.MoviesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            // Individual flavor based on Id selected
            case MOVIES_ENTRY_WITH_ID: {
                retCursor = mMovieDBHelper.getReadableDatabase().query(
                        MoviesContract.MoviesEntry.TABLE_NAME,
                        projection,
                        MoviesContract.MoviesEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            case FAVORITES_ENTRY: {
                retCursor = mMovieDBHelper.getReadableDatabase().query(
                        MoviesContract.FavouritesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            // Individual flavor based on Id selected
            case FAVORITES_ENTRY_WITH_ID: {
                retCursor = mMovieDBHelper.getReadableDatabase().query(
                        MoviesContract.FavouritesEntry.TABLE_NAME,
                        projection,
                        MoviesContract.FavouritesEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                retCursor.setNotificationUri(getContext().getContentResolver(),uri);
                return retCursor;
            }
            default: {
                // By default, we assume a bad URI
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = AppContentProvider.buildAppUriMatcher().match(uri);

        switch (match) {
            case MOVIES_ENTRY: {
                return MoviesContract.MoviesEntry.CONTENT_MOVIES_DIR_TYPE;
            }
            case MOVIES_ENTRY_WITH_ID: {
                return MoviesContract.MoviesEntry.CONTENT_MOVIES_ITEM_TYPE;
            }
            case FAVORITES_ENTRY: {
                return MoviesContract.FavouritesEntry.CONTENT_FAVOURITES_DIR_TYPE;
            }
            case FAVORITES_ENTRY_WITH_ID: {
                return MoviesContract.FavouritesEntry.CONTENT_FAVOURITES_ITEM_TYPE;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mMovieDBHelper.getWritableDatabase();
        Uri returnUri;
        switch (buildAppUriMatcher().match(uri)) {
            case MOVIES_ENTRY: {
                long _id = db.insert(MoviesContract.MoviesEntry.TABLE_NAME, null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    returnUri = MoviesContract.MoviesEntry.buildMovieListUri(_id);
                } else {
//                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                    return null;
                }
            }
            break;
            case FAVORITES_ENTRY: {
                long _id = db.insert(MoviesContract.FavouritesEntry.TABLE_NAME, null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    returnUri = MoviesContract.FavouritesEntry.buildFavouritesUri(_id);
                } else {
//                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                    return null;
                }
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
