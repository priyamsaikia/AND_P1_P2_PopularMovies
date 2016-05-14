package apps.orchotech.com.popularmovies.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by PriyamSaikia on 15-05-2016.
 */
public class Parser {
    public PopularMoviesBean parseMovieDetails(String response) {
        PopularMoviesBean bean = new PopularMoviesBean();
        try {
            JSONObject obj = new JSONObject(response);

            String poster_path = obj.getString("poster_path");
            String overview = obj.getString("overview");
            String release_date = obj.getString("release_date");
            String id = obj.getString("id");
            String original_title = obj.getString("title");
            String backdrop_path = obj.getString("backdrop_path");
            String vote_average = obj.getString("vote_average");
            String duration = obj.getString("runtime");

            bean.setPoster_path(poster_path);
            bean.setOverview(overview);
            bean.setRelease_date(release_date);
            bean.setId(id);
            bean.setTitle(original_title);
            bean.setBackdrop_path(backdrop_path);
            bean.setVote_average(vote_average);
            bean.setRuntime(duration);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public ArrayList<AllMoviesBean> parseAllMovies(String response) {
        ArrayList<AllMoviesBean> allMoviesBeen = null;

        try {
            JSONObject obj = new JSONObject(response);
            JSONArray array = obj.getJSONArray("results");
            allMoviesBeen = new ArrayList<AllMoviesBean>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String id = object.getString("id");
                String poster_path = object.getString("poster_path");

                AllMoviesBean bean = new AllMoviesBean();
                bean.setId(id);
                bean.setPoster_link(poster_path);

                allMoviesBeen.add(bean);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allMoviesBeen;
    }
}
