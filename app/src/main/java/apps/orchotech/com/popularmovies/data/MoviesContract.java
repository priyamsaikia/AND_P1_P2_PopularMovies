package apps.orchotech.com.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column
 * names for the tables of the
 * database.
 */
public class MoviesContract {
    public static final String AUTHORITY = "apps.orchotech.com.popularmovies";
    public static Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final class MoviesEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_POSTER_LINK = "poster_link";

        public static Uri CONTENT_URI_MOVIES_ENTRY = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
        public static String CONTENT_MOVIES_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + TABLE_NAME;
        public static String CONTENT_MOVIES_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + TABLE_NAME;

        // for building URIs on insertion
        public static Uri buildMovieListUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI_MOVIES_ENTRY, id);
        }
    }

    /* Inner class that defines the table contents of the weather table */
    public static final class FavouritesEntry implements BaseColumns {
        public static final String TABLE_NAME = "favourites";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_RUN_TIME = "duration";
        public static final String COLUMN_MOVIE_NAME = "movie_name";
        public static final String COLUMN_OVERVIEW = "movie_overview";
        public static final String COLUMN_VOTE_AVERAGE = "movie_vote_average";
        public static final String COLUMN_RELEASE_DATE = "movie_release_date";
        public static final String COLUMN_POSTER_LINK = "movie_poster_link";
        public static final String COLUMN_BANNER_LINK = "movie_banner_link";

        public static Uri CONTENT_URI_FAVOURITES_ENTRY = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
        public static String CONTENT_FAVOURITES_DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + TABLE_NAME;
        public static String CONTENT_FAVOURITES_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + TABLE_NAME;

        // for building URIs on insertion
        public static Uri buildFavouritesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI_FAVOURITES_ENTRY, id);
        }

    }
}
