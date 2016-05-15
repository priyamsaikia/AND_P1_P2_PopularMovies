package apps.orchotech.com.popularmovies.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import apps.orchotech.com.popularmovies.R;
import apps.orchotech.com.popularmovies.network.ImageLoader;
import apps.orchotech.com.popularmovies.network.MyConnection;
import apps.orchotech.com.popularmovies.network.Parser;
import apps.orchotech.com.popularmovies.network.PopularMoviesBean;
import apps.orchotech.com.popularmovies.network.VolleyRequest;
import apps.orchotech.com.popularmovies.utils.AppConstants;

/**
 * Created by PriyamSaikia on 15-05-2016.
 */
public class DetailFragment extends Fragment implements MyConnection.IMyConnection {
    private static final String TAG = DetailFragment.class.getSimpleName();
    private TextView tv_overview, tv_movie_name, tv_release_date, tv_duration, tv_rating;
    ImageView imv_poster;
    private String mMovieId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null)
            mMovieId = getArguments().getString(AppConstants.MOVIE_ID);
        else mMovieId="";
        View view = inflater.inflate(R.layout.detail_layout, container, false);
        tv_movie_name = (TextView) view.findViewById(R.id.tv_movie_name);
        tv_release_date = (TextView) view.findViewById(R.id.tv_release_date);
        tv_duration = (TextView) view.findViewById(R.id.tv_duration);
        tv_rating = (TextView) view.findViewById(R.id.tv_rating);
        tv_overview = (TextView) view.findViewById(R.id.tv_overview);
        imv_poster = (ImageView) view.findViewById(R.id.imv_detail);
        getMovieDetails(mMovieId);
        return view;
    }

    private void getMovieDetails(String movieId) {
        String url = String.format(AppConstants.MOVIE_DETAIL_LINK, movieId);
        VolleyRequest.sendRequest(getActivity(), url, this, AppConstants.DETAIL_REQUEST_ID);
    }

    @Override
    public void onSuccess(String response, int requestId) {
        Parser parser = new Parser();
        PopularMoviesBean details = parser.parseMovieDetails(response);
        String[] formattedDate = details.getRelease_date().split("-");
        tv_duration.setText(details.getRuntime() + " mins");
        tv_movie_name.setText(details.getTitle());
        tv_rating.setText(details.getVote_average() + "/10");
        tv_overview.setText(details.getOverview());
        tv_release_date.setText(formattedDate[0]);
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.loadImage(getActivity(), details.getPoster_path(), imv_poster);
    }

    @Override
    public void onFailure(String error, int requestId) {

    }
}
