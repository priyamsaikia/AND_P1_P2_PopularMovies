package apps.orchotech.com.popularmovies.utils;

/**
 * Created by PriyamSaikia on 14-05-2016.
 */
public class AppConstants {
    public static final String REQUEST_URL = "http://api.themoviedb.org/3/movie/%s?api_key=" + PrivateConstants.API_KEY;
    public static final String BASE_IMAGE_LINK = "http://image.tmdb.org/t/p/w185/";
    public static final int POSTER_REQUEST_ID = 001;
    public static final String INTENT_MOVIE_DETAIL = "movie_id";
    public static final String MOVIE_DETAIL_LINK = "https://api.themoviedb.org/3/movie/%s?api_key=" + PrivateConstants.API_KEY;
    public static final int DETAIL_REQUEST_ID = 002;

    public static final String SETTINGS_KEY = "SortBy";
}
