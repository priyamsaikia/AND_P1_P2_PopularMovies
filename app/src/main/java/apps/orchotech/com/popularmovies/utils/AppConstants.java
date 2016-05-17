package apps.orchotech.com.popularmovies.utils;

/**
 * Created by PriyamSaikia on 14-05-2016.
 */
public class AppConstants {
    public static final String REQUEST_URL = "http://api.themoviedb.org/3/movie/%s?api_key=" + PrivateConstants.API_KEY;
    public static final String BASE_IMAGE_LINK = "http://image.tmdb.org/t/p/w185/";
    public static final int POSTER_REQUEST_ID = 001;
    public static final String MOVIE_ID = "movie_id";
    public static final String MOVIE_DETAIL_LINK = "https://api.themoviedb.org/3/movie/%s?api_key=" + PrivateConstants.API_KEY;
    public static final int DETAIL_REQUEST_ID = 002;
    public static final int REVIEW_REQUEST_ID = 003;
    public static final String INTENT_LIST_TYPE = "type_of_list";
    public static final String TYPE_TRAILERS = "trailers";
    public static String YOUTUBE_LINK = "http://www.youtube.com/watch?v=%s";
    public static final String TRAILER_SHARE_LINK = "Hey! Check out the trailer of %s on " + YOUTUBE_LINK;
    public static final String SETTINGS_KEY = "SortBy";
    public static final String PREF_FAVOURITES = "preference_favourites";
    public static final String TAG = "TAG";
    public static final String BASE_URL = "http://api.themoviedb.org/3/movie/%s/";
    public static final int TRAILER_REQUEST_ID = 003;
    public static String API_LINK = "?api_key=" + PrivateConstants.API_KEY;
    public static String TRAILER_LINKS = BASE_URL + "videos" + API_LINK;
    public static String REVIEW_LINKS = BASE_URL + "reviews" + API_LINK;
    public static String INTENT_MOVIE_ID = "intent_movie_id";
    public static String TYPE_REVIEWS = "reviews";
}
