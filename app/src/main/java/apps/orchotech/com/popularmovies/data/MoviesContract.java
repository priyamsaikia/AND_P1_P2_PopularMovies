/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package apps.orchotech.com.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Defines table and column
 * names for the weather
 * database.
 */
public class MoviesContract {

//    public static long normalizeDate(long startDate) {
//        // normalize the start date to the beginning of the (UTC) day
//        Time time = new Time();
//        time.set(startDate);
//        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
//        return time.setJulianDay(julianDay);
//    }

    /*
        Inner class that defines the table contents of the location table
        Students: This is where you will add the strings.  (Similar to what has been
        done for FavouritesEntry)
     */
    public static final class MoviesEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_POSTER_LINK = "poster_link";
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
    }
}
